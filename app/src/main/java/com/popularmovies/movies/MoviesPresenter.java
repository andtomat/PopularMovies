package com.popularmovies.movies;

import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.popularmovies.data.model.Movie;
import com.popularmovies.data.model.Results;
import com.popularmovies.data.source.LoaderProvider;
import com.popularmovies.data.source.MoviesRepository;
import com.popularmovies.detailmovie.DetailMovieContract;
import com.popularmovies.detailmovie.DetailMoviePresenter;
import com.popularmovies.util.Device;
import com.popularmovies.util.Sort;
import com.popularmovies.util.schedulers.BaseSchedulerProvider;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static com.google.common.base.Preconditions.checkNotNull;


public class MoviesPresenter implements MoviesContract.Presenter, LoaderManager.LoaderCallbacks<Cursor> {

    @NonNull
    private MoviesRepository mRepository;

    @NonNull
    private MoviesContract.View mView;

    @NonNull
    private BaseSchedulerProvider mSchedulerProvider;

    @NonNull
    private CompositeSubscription mSubscriptions;

    @NonNull
    private LoaderManager mLoaderManager;

    @NonNull
    private LoaderProvider mLoaderProvider;

    private DetailMoviePresenter mDetailMoviePresenter;

    private DetailMovieContract.View mDetailView;

    public final static int MOVIES_LOADER = 1;

    private Device typeDevice = Device.PHONE;

    @Inject
    MoviesPresenter(@NonNull MoviesRepository repository,
                    @NonNull MoviesContract.View view,
                    @NonNull BaseSchedulerProvider schedulerProvider,
                    @NonNull LoaderManager loaderManager,
                    @NonNull LoaderProvider loaderProvider) {

        mRepository = checkNotNull(repository, "cannot be null!");
        mView = checkNotNull(view, "cannot be null!");
        mSchedulerProvider = checkNotNull(schedulerProvider, "cannot be null!");
        mLoaderManager = checkNotNull(loaderManager, "cannot be null!");
        mLoaderProvider = checkNotNull(loaderProvider, "cannot be null!");

        mSubscriptions = new CompositeSubscription();
        mView.setPresenter(this);
    }

    @Inject
    void setupListeners() {
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    @Override
    public void getMovies(Sort sort, int page) {

        Observable<Results<Movie>> mObsRepo;
        if(sort == Sort.POPULAR){
            mObsRepo =  mRepository.getPopularMovies(page);
        }
        else{
            mObsRepo =  mRepository.getTopRatedPopularMovies(page);
        }

        Subscription subscription = mObsRepo
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(data -> {
                    mView.hideProgress();
                    if(page == 1) {
                        mView.showData(data.getResults());
                        if(typeDevice == Device.TABLET){
                            mDetailMoviePresenter.setMovieId(String.valueOf(data.getResults().get(0).id));
                            mDetailView.showLayout(data.getResults().get(0));
                        }
                    }
                    else{
                        mView.addData(data.getResults());
                    }
                }, error -> {
                    mView.hideProgress();
                    if(page == 1) {
                        mView.showMessage("No Internet Connection");
                    }
                    else{
                        mView.showMessageOnScroll("No Internet Connection", page);
                    }
                });

        mSubscriptions.add(subscription);
    }

    @Override
    public void setTabletSetting(Device type, DetailMoviePresenter presenter, DetailMovieContract.View view) {
        mDetailMoviePresenter = presenter;
        mDetailView = view;
        typeDevice = type;
    }

    @Override
    public void getFavoriteMovies() {
        initLoader();
    }

    @Override
    public void refreshPopularMovies() {
        mRepository.refreshPopularMovies();
    }

    @Override
    public void refreshTopRatedMovies() {
        mRepository.refreshTopRatedMovies();
    }

    @Override
    public void openDetailUi(Movie movie, ImageView img) {
        mView.navigateToDetailUi(movie, img);
    }

    @Override
    public void initLoader() {
        if (mLoaderManager.getLoader(MOVIES_LOADER) == null) {
            mLoaderManager.initLoader(MOVIES_LOADER, null, this);
        } else {
            mLoaderManager.restartLoader(MOVIES_LOADER, null, this);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return mLoaderProvider.createMoviesLoader();
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        ArrayList<Movie> mList = new ArrayList<>();
        while (cursor.moveToNext()){
            Movie movie = Movie.from(cursor);
            mList.add(movie);
        }
        mView.hideProgress();
        mView.showData(mList);
        if(typeDevice == Device.TABLET){
            if(mList.size() > 0){
                mDetailMoviePresenter.setMovieId(String.valueOf(mList.get(0).id));
                mDetailView.showLayout(mList.get(0));
            }
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mView.showData(null);
    }
}

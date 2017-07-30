
package com.popularmovies;

import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.popularmovies.data.model.Movie;
import com.popularmovies.detailmovie.DetailMovieContract;
import com.popularmovies.detailmovie.DetailMoviePresenter;
import com.popularmovies.movies.MoviesContract;
import com.popularmovies.movies.MoviesPresenter;
import com.popularmovies.util.Device;
import com.popularmovies.util.Sort;

import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;

public class MoviesTabletPresenter implements MoviesContract.Presenter, DetailMovieContract.Presenter {

    private MoviesPresenter mMoviesPresenter;

    private DetailMoviePresenter mDetailMoviePresenter;

    private MoviesContract.View mMoviewView;

    private DetailMovieContract.View mDetailView;

    private Toolbar mToolbar;

    private TextView mTvToolbar;

    @Inject
    public MoviesTabletPresenter(@NonNull MoviesPresenter moviesPresenter,
                                 @NonNull DetailMoviePresenter detailMoviePresenter,
                                 @NonNull MoviesContract.View movieView,
                                 @NonNull DetailMovieContract.View detailView,
                                 @NonNull Toolbar toolbar,
                                 @NonNull TextView tvToolbar) {
        mMoviesPresenter = checkNotNull(moviesPresenter);
        mDetailMoviePresenter = checkNotNull(detailMoviePresenter);
        mMoviewView = checkNotNull(movieView);
        mDetailView = checkNotNull(detailView);
        mToolbar = checkNotNull(toolbar);
        mTvToolbar = checkNotNull(tvToolbar);

    }

    @Inject
    void setupListeners() {
        mMoviewView.setPresenter(this);
        mDetailView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        setTabletSetting(Device.TABLET, mDetailMoviePresenter, mDetailView);
        mMoviewView.setToolbarTablet(mToolbar, mTvToolbar);
    }

    @Override
    public void unsubscribe() {
    }

    @Override
    public void getMovies(Sort sort, int page) {
        mMoviesPresenter.getMovies(sort, page);
    }

    @Override
    public void setTabletSetting(Device type, DetailMoviePresenter presenter, DetailMovieContract.View view) {
        mMoviesPresenter.setTabletSetting(type, presenter, view);
    }


    @Override
    public void getFavoriteMovies() {
        mMoviesPresenter.getFavoriteMovies();
    }

    @Override
    public void initLoader() {
        mMoviesPresenter.initLoader();
    }

    @Override
    public void refreshPopularMovies() {
        mMoviesPresenter.refreshPopularMovies();
    }

    @Override
    public void refreshTopRatedMovies() {
        mMoviesPresenter.refreshTopRatedMovies();
    }

    @Override
    public void openDetailUi(Movie movie, ImageView img) {
        setMovieId(String.valueOf(movie.id));
        mDetailView.showLayout(movie);
    }


    @Override
    public void isFavorited(Movie movie) {
        mDetailMoviePresenter.isFavorited(movie);
    }

    @Override
    public void setFavorited(Movie movie, boolean favorite) {
        mDetailMoviePresenter.setFavorited(movie, favorite);
    }

    @Override
    public void getVideosandReviews(int page) {
        mDetailMoviePresenter.getVideosandReviews(page);
    }

    @Override
    public void getReviews(int page) {
        mDetailMoviePresenter.getReviews(page);
    }

    @Override
    public void setMovieId(String id) {
        mDetailMoviePresenter.setMovieId(id);
    }

}

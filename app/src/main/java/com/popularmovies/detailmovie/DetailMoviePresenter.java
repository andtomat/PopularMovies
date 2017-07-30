/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.popularmovies.detailmovie;

import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.popularmovies.data.model.Movie;
import com.popularmovies.data.model.Results;
import com.popularmovies.data.model.Review;
import com.popularmovies.data.model.Video;
import com.popularmovies.data.source.LoaderProvider;
import com.popularmovies.data.source.MoviesRepository;
import com.popularmovies.movies.MoviesContract;
import com.popularmovies.util.Sort;
import com.popularmovies.util.schedulers.BaseSchedulerProvider;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

import static com.google.common.base.Preconditions.checkNotNull;


public class DetailMoviePresenter implements DetailMovieContract.Presenter, LoaderManager.LoaderCallbacks<Cursor>  {

    @NonNull
    private final MoviesRepository mRepository;

    @NonNull
    private final DetailMovieContract.View mView;

    @NonNull
    private final BaseSchedulerProvider mSchedulerProvider;

    @NonNull
    private CompositeSubscription mSubscriptions;

    @NonNull
    private LoaderManager mLoaderManager;

    @NonNull
    private LoaderProvider mLoaderProvider;

    public final static int MOVIES_LOADER = 1;

    private String mId;

    @Inject
    DetailMoviePresenter(@NonNull String id,
                         @NonNull MoviesRepository repository,
                         @NonNull DetailMovieContract.View view,
                         @NonNull BaseSchedulerProvider schedulerProvider,
                         @NonNull LoaderManager loaderManager,
                         @NonNull LoaderProvider loaderProvider) {

        mId =  checkNotNull(id, "cannot be null!");
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
    public void isFavorited(Movie movie) {
        if (mLoaderManager.getLoader(MOVIES_LOADER) == null) {
            mLoaderManager.initLoader(MOVIES_LOADER, null, this);
        } else {
            mLoaderManager.restartLoader(MOVIES_LOADER, null, this);
        }
    }

    @Override
    public void setFavorited(Movie movie, boolean favorite) {
        if(favorite){
            mRepository.saveMovies(movie);
        }
        else{
            mRepository.deleteMovie(String.valueOf(movie.id));
        }
    }

    @Override
    public void getVideosandReviews(int page) {

        Subscription subscription = mRepository.getVideos(Integer.valueOf(mId))
                .doOnNext(videoResults -> mView.showTrailer(videoResults.getResults()))
                .flatMap(videoResults -> mRepository.getReviews(Integer.valueOf(mId), page).
                        subscribeOn(mSchedulerProvider.computation())
                        .observeOn(mSchedulerProvider.ui()))
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(reviewResults -> {
                    mView.showReview(reviewResults.getResults());
                }, error -> {
                    error.printStackTrace();
                    mView.showMessage("Trailer and reviews not available in offline mode");
                });

        mSubscriptions.add(subscription);
    }

    @Override
    public void getReviews(int page) {
        Subscription subscription = mRepository.getReviews(Integer.valueOf(mId), page)
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(reviewResults -> {
                    mView.addReview(reviewResults.getResults());
                }, error -> {
                    error.printStackTrace();
                });

        mSubscriptions.add(subscription);
    }

    @Override
    public void setMovieId(String id) {
        mId = id;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return mLoaderProvider.createMovieLoader(mId);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        ArrayList<Movie> mList = new ArrayList<>();
        int i = 0;
        while (cursor.moveToNext()){
            Movie movie = Movie.from(cursor);
            mList.add(movie);
            i++;
        }
        if(i > 0){
            mView.setLikeButton(true);
        }
        else{
            mView.setLikeButton(false);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}

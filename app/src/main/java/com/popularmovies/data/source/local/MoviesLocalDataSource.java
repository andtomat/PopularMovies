
package com.popularmovies.data.source.local;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.popularmovies.data.model.Review;
import com.popularmovies.data.model.Video;
import com.popularmovies.data.source.MovieValues;
import com.popularmovies.data.model.Movie;
import com.popularmovies.data.model.Results;
import com.popularmovies.data.source.MoviesDataSource;

import rx.Observable;

import static com.google.common.base.Preconditions.checkNotNull;

public class MoviesLocalDataSource implements MoviesDataSource, LoaderManager.LoaderCallbacks<Cursor>  {

    private ContentResolver mContentResolver;

    public MoviesLocalDataSource(@NonNull ContentResolver contentResolver) {
        checkNotNull(contentResolver);
        mContentResolver = contentResolver;
    }

    @Override
    public Observable<Results<Movie>> getPopularMovies(int page) {
        // no-op since the data is loader via Cursor Loader
        return null;
    }

    @Override
    public Observable<Results<Movie>> getTopRatedPopularMovies(int page) {
        // no-op since the data is loader via Cursor Loader
        return null;
    }

    @Override
    public Observable<Results<Movie>> getFavoriteMovies() {
        // no-op since the data is loader via Cursor Loader
        return null;
    }

    @Override
    public Observable<Movie> getMovie(@NonNull String movieId) {
        // no-op since the data is loader via Cursor Loader
        return null;
    }

    @Override
    public Observable<Results<Video>> getVideos(int id) {
        //only remote
        return null;
    }

    @Override
    public Observable<Results<Review>> getReviews(int id, int page) {
        //only remote
        return null;
    }

    @Override
    public void saveMovies(@NonNull Movie movie) {
        checkNotNull(movie);

        ContentValues values = MovieValues.from(movie);
        mContentResolver.insert(MoviesPersistenceContract.MovieEntry.buildMoviesUri(), values);
    }

    public void updateMovie(@NonNull String id) {
        ContentValues values = new ContentValues();
        values.put(MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_LOVE, false);

        String selection = MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_ID + " LIKE ?";
        String[] selectionArgs = {id};

        mContentResolver.update(MoviesPersistenceContract.MovieEntry.buildMoviesUri(), values, selection, selectionArgs);
    }

    @Override
    public void deleteMovie(@NonNull String id) {

        String selection = MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_ID + " LIKE ?";
        String[] selectionArgs = {id};

        mContentResolver.delete(MoviesPersistenceContract.MovieEntry.buildMoviesUri(), selection, selectionArgs);
    }

    @Override
    public void refreshPopularMovies() {
        //refresh has handle by repo
    }

    @Override
    public void refreshTopRatedMovies() {
        //refresh has handle by repo
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}

package com.popularmovies.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import com.popularmovies.data.model.Movie;
import com.popularmovies.data.model.Results;
import com.popularmovies.data.model.Review;
import com.popularmovies.data.model.Video;
import com.popularmovies.data.source.local.Local;
import com.popularmovies.data.source.remote.Remote;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Func1;

import static com.google.common.base.Preconditions.checkNotNull;

@Singleton
public class MoviesRepository implements MoviesDataSource{

    private final MoviesDataSource mMoviesRemoteDataSource;

    private final MoviesDataSource mMoviesLocalDataSource;

    @VisibleForTesting
    @Nullable
    Map<String, Movie> mCachedPopularMovies;

    @VisibleForTesting
    @Nullable
    Map<String, Movie> mCachedTopRatedMovies;

    @VisibleForTesting
    boolean mPopularCacheIsDirty = false;

    @VisibleForTesting
    boolean mTopRatedCacheIsDirty = false;

    @Inject
    MoviesRepository(@Local MoviesDataSource moviesLocalDataSource, @Remote MoviesDataSource moviesRemoteDataSource) {
        mMoviesRemoteDataSource = moviesRemoteDataSource;
        mMoviesLocalDataSource =  moviesLocalDataSource;
    }

    @Override
    public Observable<Results<Movie>> getPopularMovies(int page) {

        if(page == 1){
            if (mCachedPopularMovies != null && !mPopularCacheIsDirty) {
                return Observable.just(convertArrayToResult(convertHashToArraylist(mCachedPopularMovies.values())));

            } else if (mCachedPopularMovies == null) {
                mCachedPopularMovies = new LinkedHashMap<>();
            }
        }

        return mMoviesRemoteDataSource.getPopularMovies(page)
                .flatMap(movieResults -> {
                    if(page == 1) {
                        Observable.from(movieResults.getResults())
                                .doOnNext(movie -> {
                                    mCachedPopularMovies.put(String.valueOf(movie.id), movie);
                                }).subscribe();
                    }
                    return Observable.just(movieResults);
                })
                .doOnCompleted(() -> mPopularCacheIsDirty = false);

    }

    public ArrayList<Movie> convertHashToArraylist(Collection<Movie> collection){
        ArrayList<Movie> movieArrayList = new ArrayList<>();
        movieArrayList.addAll(collection);
        return movieArrayList;
    }

    public Results<Movie> convertArrayToResult(ArrayList<Movie> arrayList){
        Results<Movie> mCachedList = new Results<>();
        mCachedList.setResults(arrayList);
        return mCachedList;
    }

    @Override
    public Observable<Results<Movie>> getTopRatedPopularMovies(int page) {
        if(page == 1){
            if (mCachedTopRatedMovies != null && !mTopRatedCacheIsDirty) {
                return Observable.just(convertArrayToResult(convertHashToArraylist(mCachedTopRatedMovies.values())));

            } else if (mCachedTopRatedMovies == null) {
                mCachedTopRatedMovies = new LinkedHashMap<>();
            }
        }

        return mMoviesRemoteDataSource.getTopRatedPopularMovies(page)
                .flatMap(movieResults -> {
                    if(page == 1) {
                        Observable.from(movieResults.getResults())
                                .doOnNext(movie -> {
                                    mCachedPopularMovies.put(String.valueOf(movie.id), movie);
                                }).subscribe();
                    }
                    return Observable.just(movieResults);
                })
                .doOnCompleted(() -> mTopRatedCacheIsDirty = false);
    }

    @Override
    public Observable<Results<Movie>> getFavoriteMovies() {
        return mMoviesLocalDataSource.getFavoriteMovies();
    }

    @Override
    public Observable<Movie> getMovie(@NonNull String movieId) {
        return null;
    }

    @Override
    public Observable<Results<Video>> getVideos(int id) {
        return mMoviesRemoteDataSource.getVideos(id);
    }

    @Override
    public Observable<Results<Review>> getReviews(int id, int page) {
        return mMoviesRemoteDataSource.getReviews(id, page);
    }

    @Override
    public void saveMovies(@NonNull Movie movie) {
        checkNotNull(movie);
        mMoviesLocalDataSource.saveMovies(movie);
    }

    @Override
    public void deleteMovie(@NonNull String id) {
        mMoviesLocalDataSource.deleteMovie(checkNotNull(id));
    }

    @Override
    public void refreshPopularMovies() {
        mPopularCacheIsDirty = true;
    }

    @Override
    public void refreshTopRatedMovies() {
        mTopRatedCacheIsDirty = true;
    }
}

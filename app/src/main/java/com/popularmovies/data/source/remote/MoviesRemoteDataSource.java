package com.popularmovies.data.source.remote;

import android.support.annotation.NonNull;

import com.popularmovies.BuildConfig;
import com.popularmovies.PopularMoviesApplication;
import com.popularmovies.data.model.Movie;
import com.popularmovies.data.model.Results;
import com.popularmovies.data.model.Review;
import com.popularmovies.data.model.Video;
import com.popularmovies.data.source.MoviesDataSource;

import javax.inject.Inject;

import rx.Observable;

public class MoviesRemoteDataSource implements MoviesDataSource {

    @Inject
    MovieService mService;

    public MoviesRemoteDataSource(){
        PopularMoviesApplication.getMovieRepositoryComponent().inject(this);
    }


    @Override
    public Observable<Results<Movie>> getPopularMovies(int page) {
        return mService.getPopularMovies(BuildConfig.MOVIE_DB_API_KEY, page);
    }

    @Override
    public Observable<Results<Movie>> getTopRatedPopularMovies(int page) {
        return mService.getTopRatedMovies(BuildConfig.MOVIE_DB_API_KEY, page);
    }

    @Override
    public Observable<Results<Movie>> getFavoriteMovies() {
        //only local
        return null;
    }

    @Override
    public Observable<Movie> getMovie(@NonNull String movieId) {
        return null;
    }

    @Override
    public Observable<Results<Video>> getVideos(int id) {
        return mService.getVideos(id, BuildConfig.MOVIE_DB_API_KEY);
    }

    @Override
    public Observable<Results<Review>> getReviews(int id, int page) {
        return mService.getReviews(id, BuildConfig.MOVIE_DB_API_KEY, page);
    }


    @Override
    public void saveMovies(@NonNull Movie movie) {
        //only local
    }

    @Override
    public void deleteMovie(@NonNull String id) {
        //only local
    }

    @Override
    public void refreshPopularMovies() {
        //refresh has handle by repo
    }

    @Override
    public void refreshTopRatedMovies() {
        //refresh has handle by repo
    }
}

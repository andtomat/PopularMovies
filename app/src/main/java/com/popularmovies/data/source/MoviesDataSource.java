
package com.popularmovies.data.source;

import android.support.annotation.NonNull;

import com.popularmovies.data.model.Movie;
import com.popularmovies.data.model.Results;
import com.popularmovies.data.model.Review;
import com.popularmovies.data.model.Video;

import rx.Observable;

public interface MoviesDataSource {

    Observable<Results<Movie>> getPopularMovies(int page);

    Observable<Results<Movie>> getTopRatedPopularMovies(int page);

    Observable<Results<Movie>> getFavoriteMovies();

    Observable<Movie> getMovie(@NonNull String movieId);

    Observable<Results<Video>> getVideos(int id);

    Observable<Results<Review>> getReviews(int id, int page);

    void saveMovies(@NonNull Movie movie);

    void deleteMovie(@NonNull String id);

    void refreshPopularMovies();

    void refreshTopRatedMovies();
}

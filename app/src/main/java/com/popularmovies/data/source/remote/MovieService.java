package com.popularmovies.data.source.remote;

import com.popularmovies.data.model.Movie;
import com.popularmovies.data.model.Results;
import com.popularmovies.data.model.Review;
import com.popularmovies.data.model.Video;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;


public interface MovieService {

    @GET("3/movie/popular")
    Observable<Results<Movie>> getPopularMovies(@Query("api_key")String api_key, @Query("page")int page);

    @GET("3/movie/top_rated")
    Observable<Results<Movie>> getTopRatedMovies(@Query("api_key")String api_key, @Query("page")int page);

    @GET("3/movie/{id}/videos")
    Observable<Results<Video>> getVideos(@Path("id") int id, @Query("api_key")String api_key);

    @GET("3/movie/{id}/reviews")
    Observable<Results<Review>> getReviews(@Path("id") int id, @Query("api_key")String api_key, @Query("page")int page);

}

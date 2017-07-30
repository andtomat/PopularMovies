
package com.popularmovies.data.source;

import android.content.ContentValues;

import com.popularmovies.data.model.Movie;
import com.popularmovies.data.source.local.MoviesPersistenceContract;

public class MovieValues {

    public static ContentValues from(Movie movie) {
        ContentValues values = new ContentValues();
        values.put(MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_ID, movie.id);
        values.put(MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_TITLE, movie.title);
        values.put(MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_POSTER_PATH, movie.poster_path);
        values.put(MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_ORIGINAL_LANGUAGE, movie.original_language);
        values.put(MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_ORIGINAL_TITLE, movie.original_title);
        values.put(MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_BACKDROP_PATH, movie.backdrop_path);
        values.put(MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_OVERVIEW, movie.overview);
        values.put(MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_RELEASE_DATE, movie.release_date);
        values.put(MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_VOTE_COUNT, movie.vote_count);
        values.put(MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_VOTE_AVERAGE, movie.vote_average);
        values.put(MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_POPULARITY, movie.popularity);
        values.put(MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_ADULT, movie.adult ? 1 : 0);
        values.put(MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_VIDEO, movie.video ? 1 : 0);
        values.put(MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_LOVE, movie.video ? 1 : 0);
        return values;
    }

}

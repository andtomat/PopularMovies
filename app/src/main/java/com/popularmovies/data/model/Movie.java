package com.popularmovies.data.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.popularmovies.data.source.local.MoviesPersistenceContract;

import java.util.ArrayList;

/**
 * Immutable model class for a Movies.
 */
public final class Movie implements Parcelable {

    public int id;
    public String title;
    public String poster_path;
    public String original_language;
    public String original_title;
    public String backdrop_path;
    public String overview;
    public String release_date;
    public int vote_count;
    public double vote_average;
    public double popularity;
    public boolean adult;
    public boolean video;
    public ArrayList<Integer> genre_ids;

    Movie(Parcel in) {
        this.vote_count = in.readInt();
        this.id = in.readInt();
        this.video = in.readInt() != 0;
        this.vote_average = in.readDouble();
        this.title = in.readString();
        this.popularity = in.readDouble();
        this.poster_path = in.readString();
        this.original_language = in.readString();
        this.original_title = in.readString();
        this.genre_ids = in.readArrayList(null);
        this.backdrop_path = in.readString();
        this.adult = in.readInt() != 0;
        this.overview = in.readString();
        this.release_date = in.readString();
    }

    Movie(@Nullable int id,
          @Nullable String title,
          @Nullable String poster_path,
          @Nullable String original_language,
          @Nullable String original_title,
          @Nullable String backdrop_path,
          @Nullable String overview,
          @Nullable String release_date,
          @Nullable int vote_count,
          @Nullable double vote_average,
          @Nullable double popularity,
          @Nullable boolean adult,
          @Nullable boolean video) {

        this.id = id;
        this.title = title;
        this.poster_path = poster_path;
        this.original_language = original_language;
        this.original_title = original_title;
        this.backdrop_path = backdrop_path;
        this.overview = overview;
        this.release_date = release_date;
        this.vote_count = vote_count;
        this.vote_average = vote_average;
        this.popularity = popularity;
        this.adult = adult;
        this.video = video;
    }

    public static Movie from(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndexOrThrow(
                MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_ID));
        String title = cursor.getString(cursor.getColumnIndexOrThrow(
                MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_TITLE));
        String poster_path = cursor.getString(cursor.getColumnIndexOrThrow(
                MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_POSTER_PATH));
        String original_language = cursor.getString(cursor.getColumnIndexOrThrow(
                MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_ORIGINAL_LANGUAGE));
        String original_title = cursor.getString(cursor.getColumnIndexOrThrow(
                MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_ORIGINAL_TITLE));
        String backdrop_path = cursor.getString(cursor.getColumnIndexOrThrow(
                MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_BACKDROP_PATH));
        String overview = cursor.getString(cursor.getColumnIndexOrThrow(
                MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_OVERVIEW));
        String release_date = cursor.getString(cursor.getColumnIndexOrThrow(
                MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_RELEASE_DATE));
        int vote_count = cursor.getInt(cursor.getColumnIndexOrThrow(
                MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_VOTE_COUNT));
        double vote_average = cursor.getDouble(cursor.getColumnIndexOrThrow(
                MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_VOTE_AVERAGE));
        double popularity = cursor.getDouble(cursor.getColumnIndexOrThrow(
                MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_POPULARITY));
        boolean adult = cursor.getInt(cursor.getColumnIndexOrThrow(
                MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_ADULT)) == 1;
        boolean video = cursor.getInt(cursor.getColumnIndexOrThrow(
                MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_VIDEO)) == 1;

        return new Movie(id, title, poster_path, original_language, original_title, backdrop_path, overview, release_date, vote_count, vote_average, popularity, adult, video);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(vote_count);
        parcel.writeInt(id);
        parcel.writeInt(video ? 1 : 0);
        parcel.writeDouble(vote_average);
        parcel.writeString(title);
        parcel.writeDouble(popularity);
        parcel.writeString(poster_path);
        parcel.writeString(original_language);
        parcel.writeString(original_title);
            parcel.writeList(genre_ids);
        parcel.writeString(backdrop_path);
        parcel.writeInt(adult ? 1 : 0);
        parcel.writeString(overview);
        parcel.writeString(release_date);
    }

    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}

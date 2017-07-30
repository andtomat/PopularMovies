
package com.popularmovies.data.source.local;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import com.popularmovies.BuildConfig;


public final class MoviesPersistenceContract {

    public static final String CONTENT_AUTHORITY = BuildConfig.APPLICATION_ID;
    private static final String CONTENT_SCHEME = "content://";
    public static final Uri BASE_CONTENT_URI = Uri.parse(CONTENT_SCHEME + CONTENT_AUTHORITY);
    private static final String SEPARATOR = "/";

    public static final String CONTENT_MOVIE_TYPE = "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + MovieEntry.TABLE_NAME;
    public static final String CONTENT_MOVIE_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + MovieEntry.TABLE_NAME;
    public static final String VND_ANDROID_CURSOR_ITEM_VND = "vnd.android.cursor.item/vnd." + CONTENT_AUTHORITY + ".";
    private static final String VND_ANDROID_CURSOR_DIR_VND = "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + ".";


    private MoviesPersistenceContract() {}

    public static abstract class MovieEntry implements BaseColumns {

        public static final String TABLE_NAME = "movies";

        public static final String COLUMN_NAME_MOVIE_ID = "id";
        public static final String COLUMN_NAME_MOVIE_TITLE = "title";
        public static final String COLUMN_NAME_MOVIE_POSTER_PATH = "poster_path";
        public static final String COLUMN_NAME_MOVIE_ORIGINAL_LANGUAGE = "original_language";
        public static final String COLUMN_NAME_MOVIE_ORIGINAL_TITLE = "original_title";
        public static final String COLUMN_NAME_MOVIE_BACKDROP_PATH = "backdrop_path";
        public static final String COLUMN_NAME_MOVIE_OVERVIEW = "overview";
        public static final String COLUMN_NAME_MOVIE_RELEASE_DATE = "release_date";
        public static final String COLUMN_NAME_MOVIE_VOTE_COUNT = "vote_count";
        public static final String COLUMN_NAME_MOVIE_VOTE_AVERAGE = "vote_average";
        public static final String COLUMN_NAME_MOVIE_POPULARITY = "popularity";
        public static final String COLUMN_NAME_MOVIE_ADULT = "adult";
        public static final String COLUMN_NAME_MOVIE_VIDEO = "video";
        public static final String COLUMN_NAME_MOVIE_LOVE = "love";


        public static final Uri CONTENT_MOVIE_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static String[] MOVIES_COLUMNS = new String[]{
                MoviesPersistenceContract.MovieEntry._ID,
                MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_ID,
                MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_TITLE,
                MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_POSTER_PATH,
                MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_ORIGINAL_LANGUAGE,
                MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_ORIGINAL_TITLE,
                MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_BACKDROP_PATH,
                MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_OVERVIEW,
                MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_RELEASE_DATE,
                MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_VOTE_COUNT,
                MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_VOTE_AVERAGE,
                MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_POPULARITY,
                MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_ADULT,
                MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_VIDEO,
                MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_LOVE};

        public static Uri buildMoviesUriWith(long id) {
            return ContentUris.withAppendedId(CONTENT_MOVIE_URI, id);
        }

        public static Uri buildMoviesUriWith(String id) {
            Uri uri = CONTENT_MOVIE_URI.buildUpon().appendPath(id).build();
            return uri;
        }

        public static Uri buildMoviesUri() {
            return CONTENT_MOVIE_URI.buildUpon().build();
        }

    }

}

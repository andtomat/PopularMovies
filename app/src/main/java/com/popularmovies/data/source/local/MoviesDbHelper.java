package com.popularmovies.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MoviesDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 3;

    public static final String DATABASE_NAME = "Movies6.db";

    private static final String TEXT_TYPE = " TEXT";

    private static final String INTEGER_TYPE = " INTEGER";

    private static final String BOOLEAN_TYPE = " INTEGER";

    private static final String DOUBLE_TYPE = " DOUBLE";

    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + MoviesPersistenceContract.MovieEntry.TABLE_NAME + " (" +
                    MoviesPersistenceContract.MovieEntry._ID + INTEGER_TYPE + " PRIMARY KEY," +
                    MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_ID + TEXT_TYPE + COMMA_SEP +
                    MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_TITLE + TEXT_TYPE + COMMA_SEP +
                    MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_POSTER_PATH + TEXT_TYPE + COMMA_SEP +
                    MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_ORIGINAL_LANGUAGE + TEXT_TYPE + COMMA_SEP +
                    MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_ORIGINAL_TITLE + TEXT_TYPE + COMMA_SEP +
                    MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_BACKDROP_PATH + TEXT_TYPE + COMMA_SEP +
                    MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_OVERVIEW + TEXT_TYPE + COMMA_SEP +
                    MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_RELEASE_DATE + TEXT_TYPE + COMMA_SEP +
                    MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_VOTE_COUNT + INTEGER_TYPE + COMMA_SEP +
                    MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_VOTE_AVERAGE + DOUBLE_TYPE + COMMA_SEP +
                    MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_POPULARITY + DOUBLE_TYPE + COMMA_SEP +
                    MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_ADULT + BOOLEAN_TYPE + COMMA_SEP +
                    MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_VIDEO + BOOLEAN_TYPE + COMMA_SEP +
                    MoviesPersistenceContract.MovieEntry.COLUMN_NAME_MOVIE_LOVE + BOOLEAN_TYPE +
            " )";

    public MoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + MoviesPersistenceContract.MovieEntry.TABLE_NAME);
        onCreate(db);
    }
}

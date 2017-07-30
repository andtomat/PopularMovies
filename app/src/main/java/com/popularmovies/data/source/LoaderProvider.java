
package com.popularmovies.data.source;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.popularmovies.data.source.local.MoviesPersistenceContract;

import static com.google.common.base.Preconditions.checkNotNull;

public class LoaderProvider {

    @NonNull
    private final Context mContext;

    public LoaderProvider(@NonNull Context context) {
        mContext = checkNotNull(context, "context cannot be null");
    }

    public Loader<Cursor> createMoviesLoader() {
        return new CursorLoader(
                mContext,
                MoviesPersistenceContract.MovieEntry.buildMoviesUri(),
                null,
                null,
                null,
                null
        );
    }

    public Loader<Cursor> createMovieLoader(String taskId) {
        return new CursorLoader(mContext, MoviesPersistenceContract.MovieEntry.buildMoviesUriWith(taskId),
                                null,
                                null,
                                new String[]{String.valueOf(taskId)}, null
        );
    }
}

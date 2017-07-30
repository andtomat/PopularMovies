package com.popularmovies.data.source;

import android.content.Context;

import com.popularmovies.data.source.local.Local;
import com.popularmovies.data.source.local.MoviesLocalDataSource;
import com.popularmovies.data.source.remote.MoviesRemoteDataSource;
import com.popularmovies.data.source.remote.Remote;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MoviesRepositoryModule {

    @Singleton
    @Provides
    @Local
    MoviesDataSource provideMoviesLocalDataSource(Context context) {
        return new MoviesLocalDataSource(context.getContentResolver());
    }

    @Singleton
    @Provides
    @Remote
    MoviesDataSource provideMoviesRemoteDataSource() {
        return new MoviesRemoteDataSource();
    }

}

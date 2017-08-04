package com.popularmovies;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.popularmovies.data.source.DaggerMoviesRepositoryComponent;
import com.popularmovies.data.source.MoviesRepositoryComponent;

public class PopularMoviesApplication extends Application {

    private static MoviesRepositoryComponent mRepositoryComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mRepositoryComponent = DaggerMoviesRepositoryComponent.builder()
                .applicationModule(new ApplicationModule(getApplicationContext()))
                .build();

        Stetho.initializeWithDefaults(this);

    }

    public static MoviesRepositoryComponent getMovieRepositoryComponent() {
        return mRepositoryComponent;
    }
}

package com.popularmovies.movies;

import android.app.LoaderManager;
import android.content.Loader;

import dagger.Module;
import dagger.Provides;


@Module
public class MoviesPresenterModule {

    private final MoviesContract.View mView;

    private final LoaderManager mLoaderManager;

    public MoviesPresenterModule(MoviesContract.View view, LoaderManager loaderManager) {
        mLoaderManager = loaderManager;
        mView = view;
    }

    @Provides
    LoaderManager provideLoaderManager() {
        return mLoaderManager;
    }

    @Provides
    MoviesContract.View provideMoviesContractView() {
        return mView;
    }

}

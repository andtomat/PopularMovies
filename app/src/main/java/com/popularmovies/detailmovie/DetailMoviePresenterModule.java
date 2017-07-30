package com.popularmovies.detailmovie;

import android.app.LoaderManager;

import com.popularmovies.movies.MoviesContract;

import dagger.Module;
import dagger.Provides;


@Module
public class DetailMoviePresenterModule {

    private final DetailMovieContract.View mView;

    private String mId;

    private LoaderManager mLoaderManager;

    public DetailMoviePresenterModule(DetailMovieContract.View view, String id, LoaderManager loaderManager) {
        mView = view;
        mId = id;
        mLoaderManager = loaderManager;
    }

    @Provides
    DetailMovieContract.View provideMoviesContractView() {
        return mView;
    }

    @Provides
    String provideMovieId() {
        return mId;
    }

    @Provides
    LoaderManager provideLoaderManager() {
        return mLoaderManager;
    }

}

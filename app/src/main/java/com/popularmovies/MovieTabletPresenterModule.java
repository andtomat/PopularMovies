package com.popularmovies;

import android.app.LoaderManager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.popularmovies.detailmovie.DetailMovieContract;
import com.popularmovies.detailmovie.DetailMoviePresenter;
import com.popularmovies.movies.MoviesContract;
import com.popularmovies.movies.MoviesPresenter;

import dagger.Module;
import dagger.Provides;


@Module
public class MovieTabletPresenterModule {

    private final DetailMovieContract.View mView;

    private final MoviesContract.View mMoviewView;

    private final LoaderManager mLoaderManager;

    private final String mId;

    private final Toolbar mToolbar;

    private final TextView mTvToolbar;

    public MovieTabletPresenterModule(String id,
                                      MoviesContract.View movieView,
                                      DetailMovieContract.View view,
                                      LoaderManager loaderManager,
                                      Toolbar toolbar,
                                      TextView tvToolbar) {
        mId = id;
        mView = view;
        mMoviewView = movieView;
        mLoaderManager = loaderManager;
        mToolbar = toolbar;
        mTvToolbar = tvToolbar;
    }


    @Provides
    Toolbar provideToolbar() {
        return mToolbar;
    }

    @Provides
    TextView provideTvToolbar() {
        return mTvToolbar;
    }

    @Provides
    String provideMovieId() {
        return mId;
    }

    @Provides
    MoviesContract.View provideMoviesContractView() {
        return mMoviewView;
    }

    @Provides
    DetailMovieContract.View provideDetailMovieContractView() {
        return mView;
    }

    @Provides
    LoaderManager provideLoaderManager() {
        return mLoaderManager;
    }
}

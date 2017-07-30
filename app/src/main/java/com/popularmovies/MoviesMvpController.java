
package com.popularmovies;

import android.app.LoaderManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.popularmovies.data.model.Movie;
import com.popularmovies.data.source.MoviesRepositoryComponent;
import com.popularmovies.detailmovie.DetailMovieFragment;
import com.popularmovies.movies.DaggerMoviesComponent;
import com.popularmovies.movies.MoviesActivity;
import com.popularmovies.movies.MoviesFragment;
import com.popularmovies.movies.MoviesPresenterModule;
import com.popularmovies.util.ActivityUtils;

import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.popularmovies.util.ActivityUtils.isTablet;

public class MoviesMvpController {

    private final MoviesActivity mMoviesActivity;

    private final MoviesRepositoryComponent mMoviesRepositoryComponent;

    private final LoaderManager mLoaderManager;

    private final Toolbar mToolbar;

    private final TextView mTvToolbar;
    @Inject
    MoviesTabletPresenter mTasksTabletPresenter;

    Movie mMovie;

    private MoviesMvpController(
            @NonNull MoviesActivity moviesActivity,
            @Nullable Movie movie,
            @Nullable MoviesRepositoryComponent moviesRepositoryComponent,
            @Nullable LoaderManager loaderManager,
            @Nullable Toolbar toolbar,
            @Nullable TextView tvToolbar) {
        mMoviesActivity = moviesActivity;
        mMovie = movie;
        mMoviesRepositoryComponent = moviesRepositoryComponent;
        mLoaderManager = loaderManager;
        mToolbar = toolbar;
        mTvToolbar = tvToolbar;
    }

    @Inject
    MoviesTabletPresenter mMoviesTabletPresenter;

    public static MoviesMvpController createMoviesView(
            @NonNull MoviesActivity moviesActivity,
            @Nullable Movie movie,
            @Nullable MoviesRepositoryComponent moviesRepositoryComponent,
            @Nullable LoaderManager loaderManager,
            @Nullable Toolbar toolbar,
            @Nullable TextView tvToolbar) {
        checkNotNull(moviesActivity);

        MoviesMvpController tasksMvpController =
                new MoviesMvpController(moviesActivity, movie, moviesRepositoryComponent, loaderManager, toolbar, tvToolbar);

        tasksMvpController.initTasksView();
        return tasksMvpController;
    }

    private void initTasksView() {
        if (isTablet(mMoviesActivity)) {
            createTabletElements();
            Log.e("laod", "tablet");
        } else {
            createPhoneElements();
            Log.e("laod", "phone");
        }
    }

    private void createPhoneElements() {

        MoviesFragment moviesFragment =
                (MoviesFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (moviesFragment == null) {
            moviesFragment = MoviesFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), moviesFragment, R.id.contentFrame);
        }

        DaggerMoviesComponent.builder()
                .moviesRepositoryComponent(mMoviesRepositoryComponent)
                .moviesPresenterModule(new MoviesPresenterModule(moviesFragment, mLoaderManager)).build()
                .inject(mMoviesActivity);
    }


    private void createTabletElements() {

        MoviesFragment moviesFragment =
                (MoviesFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (moviesFragment == null) {
            moviesFragment = MoviesFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), moviesFragment, R.id.contentFrameList);
        }

        DetailMovieFragment detailMovieFragment =
                (DetailMovieFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (detailMovieFragment == null) {
            detailMovieFragment = DetailMovieFragment.newInstance(mMovie);
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), detailMovieFragment, R.id.contentFrameDetail);
        }


        DaggerMoviesTabletComponent.builder()
                .moviesRepositoryComponent(mMoviesRepositoryComponent)
                .movieTabletPresenterModule(new MovieTabletPresenterModule("",
                        moviesFragment,
                        detailMovieFragment,
                        mLoaderManager,
                        mToolbar,
                        mTvToolbar)).build()
                .inject(this);

    }

    private FragmentManager getSupportFragmentManager() {
        return mMoviesActivity.getSupportFragmentManager();
    }
}

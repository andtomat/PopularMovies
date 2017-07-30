package com.popularmovies.data.source;

import android.content.Context;

import com.popularmovies.ApplicationModule;
import com.popularmovies.data.source.remote.MovieService;
import com.popularmovies.data.source.remote.MoviesRemoteDataSource;
import com.popularmovies.util.schedulers.BaseSchedulerProvider;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {MoviesRepositoryModule.class, ApplicationModule.class})
public interface MoviesRepositoryComponent {

    MoviesRepository getMoviesRepository();

    BaseSchedulerProvider getBaseSchedulerProvider();

    LoaderProvider getLoaderProvider();

    Context getContext();

    void inject(MoviesRemoteDataSource moviesRemoteDataSource);
}

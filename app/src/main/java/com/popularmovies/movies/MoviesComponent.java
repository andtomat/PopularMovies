package com.popularmovies.movies;

import com.popularmovies.MoviesMvpController;
import com.popularmovies.data.source.MoviesRepositoryComponent;
import com.popularmovies.util.FragmentScoped;

import dagger.Component;

@FragmentScoped
@Component(dependencies = MoviesRepositoryComponent.class, modules = MoviesPresenterModule.class)
public interface MoviesComponent {
	
    void inject(MoviesActivity activity);

}

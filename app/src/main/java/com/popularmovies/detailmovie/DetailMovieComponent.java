package com.popularmovies.detailmovie;

import com.popularmovies.data.source.MoviesRepositoryComponent;
import com.popularmovies.util.FragmentScoped;

import dagger.Component;

@FragmentScoped
@Component(dependencies = MoviesRepositoryComponent.class, modules = DetailMoviePresenterModule.class)
public interface DetailMovieComponent {
	
    void inject(DetailMovieActivity activity);

}

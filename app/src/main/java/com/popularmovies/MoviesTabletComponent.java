package com.popularmovies;

import com.popularmovies.data.source.MoviesRepositoryComponent;
import com.popularmovies.util.FragmentScoped;

import dagger.Component;

@FragmentScoped
@Component(dependencies = MoviesRepositoryComponent.class, modules = MovieTabletPresenterModule.class)
public interface MoviesTabletComponent {

    void inject(MoviesMvpController controller);

}

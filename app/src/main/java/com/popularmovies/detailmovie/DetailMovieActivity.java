package com.popularmovies.detailmovie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.popularmovies.PopularMoviesApplication;
import com.popularmovies.R;
import com.popularmovies.data.model.Movie;
import com.popularmovies.util.ActivityUtils;

import javax.inject.Inject;

public class DetailMovieActivity extends AppCompatActivity {

    @Inject
    DetailMoviePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        Movie movie = getIntent().getExtras().getParcelable("movie");

        DetailMovieFragment moviesFragment =
                (DetailMovieFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (moviesFragment == null) {
            moviesFragment = DetailMovieFragment.newInstance(movie);
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), moviesFragment, R.id.contentFrame);
        }

        DaggerDetailMovieComponent.builder()
                .moviesRepositoryComponent(((PopularMoviesApplication) getApplication()).getMovieRepositoryComponent())
                .detailMoviePresenterModule(new DetailMoviePresenterModule(moviesFragment, String.valueOf(movie.id), getLoaderManager())).build()
                .inject(this);

    }
}

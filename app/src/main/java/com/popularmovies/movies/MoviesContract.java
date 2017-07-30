package com.popularmovies.movies;

import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.popularmovies.BasePresenter;
import com.popularmovies.BaseView;
import com.popularmovies.data.model.Movie;
import com.popularmovies.detailmovie.DetailMovieContract;
import com.popularmovies.detailmovie.DetailMovieFragment;
import com.popularmovies.detailmovie.DetailMoviePresenter;
import com.popularmovies.util.Device;
import com.popularmovies.util.Sort;

import java.util.ArrayList;

public interface MoviesContract {

    interface View extends BaseView<Presenter> {

        void showProgress();

        void hideProgress();

        void showMessage(String message);

        void showMessageOnScroll(String message, int page);

        void showData(ArrayList<Movie> list);

        void addData(ArrayList<Movie> list);

        void navigateToDetailUi(Movie movie, ImageView img);

        void setToolbarTablet(Toolbar toolbar, TextView tvToolbar);

    }

    interface Presenter extends BasePresenter {

        void getMovies(Sort sort, int page);

        void setTabletSetting(Device type, DetailMoviePresenter presenter, DetailMovieContract.View view);

        void getFavoriteMovies();

        void initLoader();

        void refreshPopularMovies();

        void refreshTopRatedMovies();

        void openDetailUi(Movie movie, ImageView img);

    }
}

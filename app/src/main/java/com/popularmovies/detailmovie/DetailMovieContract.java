package com.popularmovies.detailmovie;

import com.popularmovies.BasePresenter;
import com.popularmovies.BaseView;
import com.popularmovies.data.model.Movie;
import com.popularmovies.data.model.Review;
import com.popularmovies.data.model.Video;
import com.popularmovies.util.Sort;

import java.util.ArrayList;

public interface DetailMovieContract {

    interface View extends BaseView<Presenter> {

        void setLikeButton(boolean status);

        void showTrailer(ArrayList<Video> list);

        void showReview(ArrayList<Review> list);

        void addReview(ArrayList<Review> list);

        void showMessage(String message);

        void showLayout(Movie movie);

    }

    interface Presenter extends BasePresenter {


        void isFavorited(Movie movie);

        void setFavorited(Movie movie, boolean favorite);

        void getVideosandReviews(int page);

        void getReviews(int page);

        void setMovieId(String id);

    }
}

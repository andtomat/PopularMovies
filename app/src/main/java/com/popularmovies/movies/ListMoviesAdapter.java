package com.popularmovies.movies;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.popularmovies.PopularMoviesApplication;
import com.popularmovies.R;
import com.popularmovies.data.model.Movie;
import com.popularmovies.detailmovie.DetailMovieActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ListMoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Movie> mList;
    private Activity mActivity;
    private MoviesFragment.MovieItemListener mMovieItemListener;

    public class ProgressViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.pbLoading)
        ProgressBar pbLoading;

        public ProgressViewHolder(View v) {
            super(v);

            ButterKnife.bind(this, v);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvOverview)
        TextView tvOverview;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.ivPoster)
        ImageView ivPoster;

        Movie currentMovie;

        public ViewHolder(View v) {
            super(v);

            ButterKnife.bind(this, v);

            v.setOnClickListener(view -> {
                mMovieItemListener.onMovieClick(currentMovie, ivPoster);
            });
        }
    }

    public ListMoviesAdapter(ArrayList<Movie> list, Activity activity, MoviesFragment.MovieItemListener movieItemListener) {
        mList = list;
        mActivity = activity;
        mMovieItemListener = movieItemListener;
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position) != null ? 1 : 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh;

        if (viewType == 1) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
            vh = new ViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            vh = new ProgressViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).tvTitle.setText(mList.get(position).title);
            ((ViewHolder) holder).tvOverview.setText(mList.get(position).overview);
            Picasso.with(PopularMoviesApplication.getMovieRepositoryComponent().getContext())
                    .load("http://image.tmdb.org/t/p/w342/"+mList.get(position).poster_path)
                    .placeholder(R.drawable.reload)
                    .into(((ViewHolder) holder).ivPoster);

            ((ViewHolder) holder).currentMovie = mList.get(position);
        }
        else{
            ((ProgressViewHolder) holder).pbLoading.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void replaceData(ArrayList<Movie> list) {
        mList = list;
        notifyDataSetChanged();
    }

}
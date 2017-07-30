package com.popularmovies.detailmovie;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.popularmovies.PopularMoviesApplication;
import com.popularmovies.R;
import com.popularmovies.data.model.Review;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ListReviewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Review> mList;

    public class ProgressViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.pbLoading)
        ProgressBar pbLoading;

        public ProgressViewHolder(View v) {
            super(v);

            ButterKnife.bind(this, v);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvAuthor)
        TextView tvAuthor;
        @BindView(R.id.tvReview)
        TextView tvReview;

        Review currentReview;

        public ViewHolder(View v) {
            super(v);

            ButterKnife.bind(this, v);

            v.setOnClickListener(view -> {
                PopularMoviesApplication.getMovieRepositoryComponent().getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(currentReview.url)));
            });
        }
    }

    public ListReviewsAdapter(ArrayList<Review> list) {
        mList = list;
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position) != null ? 1 : 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh;

        if (viewType == 1) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
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
            ((ViewHolder) holder).tvAuthor.setText("Author : " + mList.get(position).author);
            ((ViewHolder) holder).tvReview.setText(mList.get(position).content);

            ((ViewHolder) holder).currentReview = mList.get(position);
        }
        else{
            ((ProgressViewHolder) holder).pbLoading.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void replaceData(ArrayList<Review> list) {
        mList = list;
        notifyDataSetChanged();
    }

}
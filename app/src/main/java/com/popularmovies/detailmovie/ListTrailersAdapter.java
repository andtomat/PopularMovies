package com.popularmovies.detailmovie;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.popularmovies.PopularMoviesApplication;
import com.popularmovies.R;
import com.popularmovies.data.model.Video;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ListTrailersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Video> mList;

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imgThumbnail)
        ImageView imgThumbnail;

        Video currentVideo;

        public ViewHolder(View v) {
            super(v);

            ButterKnife.bind(this, v);

            v.setOnClickListener(view -> {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + currentVideo.key));
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                PopularMoviesApplication.getMovieRepositoryComponent().getContext().startActivity(i);
            });
        }
    }

    public ListTrailersAdapter(ArrayList<Video> list) {
        mList = list;
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position) != null ? 1 : 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh;

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trailer, parent, false);
        vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((ViewHolder) holder).currentVideo = mList.get(position);

        String thumbnailUrl = "http://img.youtube.com/vi/" + mList.get(position).key + "/0.jpg";

        Picasso.with(PopularMoviesApplication.getMovieRepositoryComponent().getContext())
                .load(thumbnailUrl)
                .into(((ViewHolder) holder).imgThumbnail);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void replaceData(ArrayList<Video> list) {
        mList = list;
        notifyDataSetChanged();
    }

}
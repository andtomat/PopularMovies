package com.popularmovies.detailmovie;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.like.IconType;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.popularmovies.R;
import com.popularmovies.data.model.Movie;
import com.popularmovies.data.model.Review;
import com.popularmovies.data.model.Video;
import com.popularmovies.util.EndlessScrollListener;
import com.popularmovies.util.TimeFormater;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailMovieFragment extends Fragment implements DetailMovieContract.View {

    private DetailMovieContract.Presenter mPresenter;

    Toolbar mToolbar;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    @BindView(R.id.ivBackdrop)
    ImageView ivBackdrop;

    @BindView(R.id.ivPoster)
    ImageView ivPoster;

    @BindView(R.id.ivPlay)
    ImageView ivPlay;

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.tvRelease)
    TextView tvRelease;

    @BindView(R.id.tvVote)
    TextView tvVote;

    @BindView(R.id.tvOverview)
    TextView tvOverview;

    @BindView(R.id.btnFavorite)
    LikeButton btnFavorite;

    @BindView(R.id.rvTrailer)
    RecyclerView rvTrailer;

    @BindView(R.id.rvReview)
    RecyclerView rvReview;

    @BindView(R.id.llTrailer)
    LinearLayout llTrailer;

    @BindView(R.id.llReview)
    LinearLayout llReview;

    ProgressBar pbLoading;

    CoordinatorLayout clContainer;

    private ListTrailersAdapter mTrailerAdapter;
    private ListReviewsAdapter mReviewAdapter;

    private EndlessScrollListener mEndlessScrollListener;

    private ArrayList<Review> mReviewList = new ArrayList<>();

    private int flagload = 0;

    private View root;

    public DetailMovieFragment() {
    }

    public static DetailMovieFragment newInstance(@Nullable Movie movie) {
        DetailMovieFragment fragment = new DetailMovieFragment();
        Bundle arguments = new Bundle();
        if(movie!=null){
            arguments.putParcelable("movie", movie);
        }
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_detail_movie, container, false);
        ButterKnife.bind(this, root);

        if(getArguments().containsKey("movie")){
            showLayout(this.getArguments().getParcelable("movie"));
        }

        return root;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().supportFinishAfterTransition();
                break;
        }
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }

    @Override
    public void setPresenter(DetailMovieContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setLikeButton(boolean status) {
        btnFavorite.setLiked(status);
    }

    @Override
    public void showTrailer(ArrayList<Video> list) {
        if(list.size()>0) {
            ivPlay.post(() -> ivPlay.setVisibility(View.VISIBLE));
            ivBackdrop.setOnClickListener(view -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + list.get(0).key))));

            llTrailer.post(() -> llTrailer.setVisibility(View.VISIBLE));

            rvTrailer.post(() -> {
                rvTrailer.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                mTrailerAdapter = new ListTrailersAdapter(new ArrayList<>());
                rvTrailer.setAdapter(mTrailerAdapter);
                rvTrailer.setNestedScrollingEnabled(false);
                mTrailerAdapter.replaceData(list);
            });
        }
    }

    @Override
    public void showReview(ArrayList<Review> list) {
        if(list.size()>0) {
            llReview.post(() -> {
                llReview.setVisibility(View.VISIBLE);
                mReviewAdapter = new ListReviewsAdapter(new ArrayList<>());
                rvReview.setAdapter(mReviewAdapter);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                rvReview.setLayoutManager(mLayoutManager);
                mEndlessScrollListener = new EndlessScrollListener(mLayoutManager) {
                    @Override
                    public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                        mPresenter.getReviews(page);
                        if(flagload == 0){
                            mReviewList.add(null);
                            rvReview.post(() -> mReviewAdapter.notifyDataSetChanged());
                            flagload = 1;
                        }
                    }
                };
                rvReview.addOnScrollListener(mEndlessScrollListener);

                mReviewList = list;
                mEndlessScrollListener.resetState();
                mReviewAdapter.replaceData(mReviewList);
            });
        }
    }

    @Override
    public void addReview(ArrayList<Review> list) {
        flagload = 0;
        mReviewList.remove(null);
        mReviewList.addAll(list);
        rvReview.post(() -> mReviewAdapter.notifyDataSetChanged());
    }

    @Override
    public void showMessage(String message) {
        Snackbar snackbar = Snackbar
                .make(mToolbar, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void showLayout(Movie movie) {

        if(root.findViewById(R.id.toolbar)!=null){
            mToolbar = (Toolbar) root.findViewById(R.id.toolbar);
            ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(movie.title);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        else {
            clContainer = (CoordinatorLayout) root.findViewById(R.id.clContainer);
            clContainer.setVisibility(View.VISIBLE);
            pbLoading = (ProgressBar) root.findViewById(R.id.pbLoading);
            pbLoading.setVisibility(View.GONE);
        }


        mCollapsingToolbarLayout.setTitle(movie.title);
        mCollapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(getActivity(),android.R.color.transparent));

        tvTitle.setText(movie.title);
        tvRelease.setText(new TimeFormater().formattedDateFromString("yyyy-MM-dd", "dd MMMM yyyy", movie.release_date));
        tvVote.setText("Average Vote : " + movie.vote_average);
        tvOverview.setText(movie.overview);

        Picasso.with(getActivity())
                .load("http://image.tmdb.org/t/p/w342/"+movie.poster_path)
                .placeholder(R.drawable.reload)
                .into(ivPoster);

        Picasso.with(getActivity())
                .load("http://image.tmdb.org/t/p/w780/"+movie.backdrop_path)
                .into(ivBackdrop);


        btnFavorite.setIcon(IconType.Star);
        btnFavorite.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                likeButton.setLiked(true);
                mPresenter.setFavorited(movie, true);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                likeButton.setLiked(false);
                mPresenter.setFavorited(movie, false);
            }
        });

        mPresenter.isFavorited(movie);

        mPresenter.getVideosandReviews(1);

        llTrailer.setVisibility(View.GONE);
        llReview.setVisibility(View.GONE);

        setHasOptionsMenu(true);
    }
}

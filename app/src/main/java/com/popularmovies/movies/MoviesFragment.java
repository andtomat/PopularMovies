package com.popularmovies.movies;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.popularmovies.R;
import com.popularmovies.data.model.Movie;
import com.popularmovies.detailmovie.DetailMovieActivity;
import com.popularmovies.util.Device;
import com.popularmovies.util.EndlessScrollListener;
import com.popularmovies.util.ScrollListener;
import com.popularmovies.util.Sort;

import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesFragment extends Fragment implements MoviesContract.View {

    private MoviesContract.Presenter mPresenter;

    Toolbar mToolbar;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.pbLoading)
    ProgressBar pbLoading;

    @BindView(R.id.srlRefresh)
    SwipeRefreshLayout srlRefresh;

    private ListMoviesAdapter recyclerAdapter;
    private TextView tvToolbar;
    private Sort mCurrentSort = Sort.POPULAR;

    private ArrayList<Movie> mList = new ArrayList<>();
    private EndlessScrollListener mEndlessScrollListener;

    private int flagload = 0;

    private boolean isAddedEndlessScroll = true;

    private static Bundle mBundleRecyclerViewState;

    private GridLayoutManager mLayoutManager;

    public MoviesFragment() {

    }

    public static MoviesFragment newInstance() {
        return new MoviesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //subscribe for tablet settings
        mPresenter.subscribe();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_movies, container, false);
        ButterKnife.bind(this, root);

        if(root.findViewById(R.id.toolbar)!=null){
            mToolbar = (Toolbar) root.findViewById(R.id.toolbar);
            tvToolbar = (TextView) mToolbar.findViewById(R.id.tvToolbar);
            ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
            tvToolbar.setText(Html.fromHtml("Most Popular <font color='#F44336'>Movies</font>"));
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");
        }


        recyclerAdapter = new ListMoviesAdapter(new ArrayList<>(), getActivity(), mItemListener);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(recyclerAdapter);

        mLayoutManager = new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.movies_columns));
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override public int getSpanSize(int position) {
                int spanCount = mLayoutManager.getSpanCount();
                return mList.get(position) != null ? 1 : spanCount;
            }
        });
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.removeOnScrollListener(mEndlessScrollListener);
        mEndlessScrollListener = new EndlessScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                mPresenter.getMovies(mCurrentSort, page);
                if(flagload == 0){
                    mList.add(null);

                    mRecyclerView.post(() -> recyclerAdapter.notifyDataSetChanged());

                    flagload = 1;
                }
            }
        };
        if(mCurrentSort!=Sort.FAVORITE){
            mRecyclerView.addOnScrollListener(mEndlessScrollListener);
        }

        mRecyclerView.addOnScrollListener(new ScrollListener(getActivity()) {
            @Override
            public void onMoved(int distance) {
                if(root.findViewById(R.id.toolbar)!=null){
                    mToolbar.setTranslationY(-distance);
                }
            }
        });

        srlRefresh.setColorSchemeResources(
                R.color.colorAccent,
                R.color.colorAccent,
                R.color.colorAccent);

        srlRefresh.setOnRefreshListener( () -> {
            if(mCurrentSort == Sort.POPULAR){
                mPresenter.refreshPopularMovies();
                mPresenter.getMovies(mCurrentSort, 1);
            }
            else if(mCurrentSort == Sort.TOP){
                mPresenter.refreshTopRatedMovies();
                mPresenter.getMovies(mCurrentSort, 1);
            }
            else{
                mPresenter.getFavoriteMovies();
            }
            showProgress();
        });

        setHasOptionsMenu(true);

        mPresenter.getMovies(Sort.POPULAR, 1);

        return root;
    }

    private void initGrid(){
        mLayoutManager.setSpanCount(getResources().getInteger(R.integer.movies_columns));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.top:
                if(mCurrentSort!=Sort.TOP){
                    mCurrentSort = Sort.TOP;
                    showProgress();
                    if(!isAddedEndlessScroll){
                        mRecyclerView.addOnScrollListener(mEndlessScrollListener);
                        isAddedEndlessScroll = true;
                    }
                    recyclerAdapter.replaceData(new ArrayList<>());
                    mPresenter.getMovies(mCurrentSort, 1);
                    tvToolbar.setText(Html.fromHtml("Highest Rated <font color='#F44336'>Movies</font>"));
                }
                break;

            case R.id.popular:
                if(mCurrentSort!=Sort.POPULAR){
                    mCurrentSort = Sort.POPULAR;
                    showProgress();
                    if(!isAddedEndlessScroll){
                        mRecyclerView.addOnScrollListener(mEndlessScrollListener);
                        isAddedEndlessScroll = true;
                    }
                    recyclerAdapter.replaceData(new ArrayList<>());
                    mPresenter.getMovies(mCurrentSort, 1);
                    tvToolbar.setText(Html.fromHtml("Most Popular <font color='#F44336'>Movies</font>"));
                }
                break;
            case R.id.favorite:
                if(mCurrentSort!=Sort.FAVORITE){
                    mCurrentSort = Sort.FAVORITE;
                    showProgress();
                    if(isAddedEndlessScroll){
                        mRecyclerView.removeOnScrollListener(mEndlessScrollListener);
                        isAddedEndlessScroll = false;
                    }
                    mPresenter.getFavoriteMovies();
                    tvToolbar.setText(Html.fromHtml("Favorite <font color='#F44336'>Movies</font>"));
                }
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
    public void setPresenter(MoviesContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showProgress() {
        pbLoading.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        srlRefresh.setRefreshing(false);
    }

    @Override
    public void hideProgress() {
        pbLoading.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMessage(String message) {
        Snackbar snackbar = Snackbar
                .make(mToolbar, message, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", view -> {
                    showProgress();
                    mPresenter.getMovies(mCurrentSort, 1);
                });
        snackbar.show();
    }

    @Override
    public void showMessageOnScroll(String message, int page) {
        Snackbar snackbar = Snackbar
                .make(mToolbar, message, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", view -> mPresenter.getMovies(mCurrentSort, page));
        snackbar.show();
    }

    @Override
    public void showData(ArrayList<Movie> list) {
        mList = list;
        mEndlessScrollListener.resetState();
        recyclerAdapter.replaceData(mList);
        mRecyclerView.scrollToPosition(0);
    }

    @Override
    public void addData(ArrayList<Movie> list) {
        flagload = 0;
        mList.remove(null);
        mList.addAll(list);
        recyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void navigateToDetailUi(Movie movie, ImageView img) {
        Intent i = new Intent(getActivity(), DetailMovieActivity.class);
        i.putExtra("movie", movie);
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(getActivity(), img, "poster");
        getActivity().startActivity(i, options.toBundle());
    }

    @Override
    public void setToolbarTablet(Toolbar toolbar, TextView tvToolbar) {
        mToolbar = toolbar;
        this.tvToolbar = tvToolbar;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_movies, menu);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        initGrid();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    MovieItemListener mItemListener = new MovieItemListener() {
        @Override
        public void onMovieClick(Movie movie, ImageView img) {
            mPresenter.openDetailUi(movie, img);
        }
    };

    public interface MovieItemListener {
        void onMovieClick(Movie movie, ImageView img);
    }
}

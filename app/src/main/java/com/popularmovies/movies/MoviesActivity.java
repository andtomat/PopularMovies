package com.popularmovies.movies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.widget.TextView;

import com.popularmovies.MoviesMvpController;
import com.popularmovies.PopularMoviesApplication;
import com.popularmovies.R;
import com.popularmovies.util.ActivityUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesActivity extends AppCompatActivity {

    @Inject
    MoviesPresenter mPresenter;

    private Toolbar mToolbar;

    private TextView tvToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        if(findViewById(R.id.toolbar)!=null){
            mToolbar = (Toolbar) findViewById(R.id.toolbar);
            tvToolbar = (TextView) mToolbar.findViewById(R.id.tvToolbar);
            setSupportActionBar(mToolbar);
            tvToolbar.setText(Html.fromHtml("Most Popular <font color='#F44336'>Movies</font>"));
            getSupportActionBar().setTitle("");
        }

        MoviesMvpController.createMoviesView(this, null, ((PopularMoviesApplication) getApplication()).getMovieRepositoryComponent(), getLoaderManager(), mToolbar, tvToolbar);

    }
}

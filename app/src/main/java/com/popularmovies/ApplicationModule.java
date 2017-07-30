package com.popularmovies;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.popularmovies.data.source.LoaderProvider;
import com.popularmovies.data.source.remote.MovieService;
import com.popularmovies.util.schedulers.BaseSchedulerProvider;
import com.popularmovies.util.schedulers.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public final class ApplicationModule {

    private Context mContext;

    ApplicationModule(Context context) {
        mContext = context;
    }

    @Provides
    Context provideContext() {
        return mContext;
    }

    @Provides
    LoaderProvider provideLoaderProvider() {
        return new LoaderProvider(mContext);
    }

    @Provides
    BaseSchedulerProvider provideSchedulerProvider() {
        return SchedulerProvider.getInstance();
    }

    @Provides
    SharedPreferences provideSharedPreferences(){
        return mContext.getSharedPreferences("gis_apps", Context.MODE_PRIVATE);
    }

    @Provides
    Retrofit provideRetrofit(Gson gson){
        return new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    Gson provideGson() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
    }

    @Provides
    @Singleton
    MovieService provideMovieService(Retrofit retrofit){
        return retrofit.create(MovieService.class);
    }
}
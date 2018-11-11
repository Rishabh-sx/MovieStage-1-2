package com.rishabh.moviestage.data.database.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.rishabh.moviestage.data.database.AppDatabase;
import com.rishabh.moviestage.pojo.Result;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    // Constant for logging
    private static final String TAG = MainViewModel.class.getSimpleName();
    private final AppDatabase database;

    public MainViewModel(Application application) {
        super(application);
        database = AppDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the tasks from the DataBase");
    }

    public LiveData<List<Result>> getMovies() {
        return database.moviesDao().loadAllMovies();
    }


    public LiveData<List<Result>> getFavMovies() {
        return database.moviesDao().loadFavMovies();
    }

    public LiveData<List<com.rishabh.moviestage.pojo.trailers.Result>> getTrailers(Integer movieId) {
        return database.moviesDao().loadTrailers(movieId);
    }

    public LiveData<List<com.rishabh.moviestage.pojo.reviews.Result>> getReviews(Integer movieId) {
        return database.moviesDao().loadReviews(movieId);
    }

}
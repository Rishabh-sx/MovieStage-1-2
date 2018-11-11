package com.rishabh.moviestage.data.database.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.rishabh.moviestage.data.database.AppDatabase;
import com.rishabh.moviestage.pojo.Result;


public class AddMoviesViewModel extends ViewModel {

    private LiveData<Result> task;

    public AddMoviesViewModel(AppDatabase database, int movieId) {
        task = database.moviesDao().loadMovieById(movieId);
    }

    public LiveData<Result> getMovies() {
        return task;
    }
}
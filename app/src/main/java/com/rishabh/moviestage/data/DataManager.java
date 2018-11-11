package com.rishabh.moviestage.data;


import android.app.Application;
import android.content.Context;

import com.rishabh.moviestage.data.api.ApiManager;
import com.rishabh.moviestage.data.database.AppDatabase;
import com.rishabh.moviestage.pojo.MovieResponse;
import com.rishabh.moviestage.pojo.reviews.Reviews;
import com.rishabh.moviestage.pojo.trailers.Trailers;

import retrofit2.Call;

/**
 * Created by appinventiv on 27/3/18.
 */

public class DataManager implements IDataManager {

    private final AppDatabase mDb;
    private ApiManager apiManager;
    private static DataManager instance;


    public static void init(Context applicationContext) {
        if (instance == null)
            instance = new DataManager(applicationContext);
    }

    public static DataManager getInstance() {
        return instance;
    }

    private DataManager(Context applicationContext) {
        apiManager = ApiManager.getInstance();
        mDb = AppDatabase.getInstance(applicationContext);
    }

    /*public static DataManager getInstance() {
        if (instance == null) {
            synchronized (DataManager.class){
                if (instance == null)
                    instance = new DataManager(applicationContext);
            }
        }
        return instance;
    }*/


    @Override
    public Call<MovieResponse> getMovies(String sortOrder, String apiKey) {
        return apiManager.getMovies(sortOrder, apiKey);
    }

    @Override
    public Call<Trailers> getTrailers(String movieId, String apiKey) {
        return apiManager.getTrailers(movieId, apiKey);
    }

    @Override
    public Call<Reviews> getReviews(String movieId, String apiKey) {
        return apiManager.getReviews(movieId, apiKey);
    }

    public AppDatabase getDatabase() {
        return mDb;
    }
}

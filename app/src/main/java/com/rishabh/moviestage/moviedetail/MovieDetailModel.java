package com.rishabh.moviestage.moviedetail;

import android.util.Log;

import com.rishabh.moviestage.base.BaseModel;
import com.rishabh.moviestage.data.AppExecutors;
import com.rishabh.moviestage.network.NetworkResponse;
import com.rishabh.moviestage.pojo.FailureResponse;
import com.rishabh.moviestage.pojo.MovieResponse;
import com.rishabh.moviestage.pojo.reviews.Reviews;
import com.rishabh.moviestage.pojo.trailers.Result;
import com.rishabh.moviestage.pojo.trailers.Trailers;
import com.rishabh.moviestage.utils.AppContants;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by appinventiv on 27/3/18.
 */

class MovieDetailModel extends BaseModel<MovieDetailModelListener> {

    private static final String TAG = "MovieListModel";

    public MovieDetailModel(MovieDetailModelListener listener) {
        super(listener);
    }

    @Override
    public void init() {

    }


    public void getTrailers(String movieId) {
        getDataManager().getTrailers(movieId, AppContants.API_KEY).enqueue
                (new NetworkResponse<Trailers>(this) {
                    @Override
                    public void onSuccess(final Trailers trailers) {
                        if (getListener() != null) {
                            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                @Override
                                public void run() {
                                    for (Result result : trailers.getResults()) {
                                        result.setMovie_id(trailers.getId());
                                        getDataManager().getDatabase().moviesDao().insertTrailer(result);
                                    }
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(int code, FailureResponse failureResponse) {
                        Log.e(TAG, "onFailure: " + failureResponse.getMsg());
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e(TAG, "onError: " + t.getMessage());
                    }
                });
    }

    public void getReviews(String movieId) {
        getDataManager().getReviews(movieId, AppContants.API_KEY).enqueue(new NetworkResponse<Reviews>(this) {
            @Override
            public void onSuccess(final Reviews reviews) {
                if (getListener() != null) {
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            for (com.rishabh.moviestage.pojo.reviews.Result result : reviews.getResults()) {
                                result.setMovie_id(reviews.getId());
                                getDataManager().getDatabase().moviesDao().insertReview(result);
                            }
                        }
                    });
                }

            }

            @Override
            public void onFailure(int code, FailureResponse failureResponse) {
                Log.e(TAG, "onFailure: " + failureResponse.getMsg());
            }

            @Override
            public void onError(Throwable t) {
                Log.e(TAG, "onError: " + t.getMessage());
            }
        });
        ;

    }

    public void setUserMoviePreference(final com.rishabh.moviestage.pojo.Result movie) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                getDataManager().getDatabase().moviesDao().updateMovie(movie);

            }
        });
    }
}

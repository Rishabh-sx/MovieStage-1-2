package com.rishabh.moviestage.movielist;

import android.util.Log;

import com.rishabh.moviestage.base.BaseModel;
import com.rishabh.moviestage.data.AppExecutors;
import com.rishabh.moviestage.network.NetworkResponse;
import com.rishabh.moviestage.pojo.FailureResponse;
import com.rishabh.moviestage.pojo.MovieResponse;



class MovieListModel extends BaseModel<MovieListModelListener> {

    private static final String TAG = "MovieListModel";

    public MovieListModel(MovieListModelListener listener) {
        super(listener);
    }

    @Override
    public void init() {

    }

    protected void getMovies(String filterBy) {
        getDataManager().getMovies(
                filterBy, "be9e7aae79d1f82bc8a8376025e1a182").enqueue(new NetworkResponse<MovieResponse>(this) {
            @Override
            public void onSuccess(final MovieResponse body) {
                getListener().hideLoader();
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {

                        getDataManager().getDatabase().moviesDao().deleteAll();
                        for (int i = 0; i < body.getResults().size(); i++)
                            getDataManager().getDatabase().moviesDao().insertMovie(body.getResults().get(i));
                    }
                });
            }

            @Override
            public void onFailure(int code, FailureResponse failureResponse) {
                if (getListener() != null)
                    getListener().onErrorOccurred(failureResponse);
                Log.e(TAG, "onFailure: " + failureResponse.getMsg());
            }

            @Override
            public void onError(Throwable t) {
                if (getListener() != null)
                    getListener().noNetworkError();

                Log.e(TAG, "onError: ", t);
            }
        });
    }
}

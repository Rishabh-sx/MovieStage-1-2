package com.rishabh.moviestage.data.api;


import com.rishabh.moviestage.pojo.MovieResponse;
import com.rishabh.moviestage.pojo.reviews.Reviews;
import com.rishabh.moviestage.pojo.trailers.Trailers;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by appinventiv on 27/3/18.
 */

public class ApiManager {

    private static final ApiManager instance = new ApiManager();
    private final ApiClient apiClient;

    private ApiManager() {
        apiClient = getRetrofitService();
    }

    public static ApiManager getInstance() {
        return instance;
    }

    private static ApiClient getRetrofitService() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiClient.TMDB_BASE_URL)
                .build();

        return retrofit.create(ApiClient.class);
    }


    public Call<MovieResponse> getMovies(String sortOrder, String apiKey) {
        return apiClient.getMovies(sortOrder,apiKey);
    }

    public Call<Trailers> getTrailers(String movieId, String apiKey) {
        return apiClient.getTrailers(movieId,apiKey);
    }

    public Call<Reviews> getReviews(String movieId, String apiKey) {
        return apiClient.getReviews(movieId,apiKey);
    }
}

package com.rishabh.moviestage.data.api;



import com.rishabh.moviestage.pojo.MovieResponse;
import com.rishabh.moviestage.pojo.reviews.Reviews;
import com.rishabh.moviestage.pojo.trailers.Trailers;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiClient {


    final String TMDB_BASE_URL = "https://api.themoviedb.org/3/";
    final String SORT_BY_PARAM = "sort_by";
    final String ID = "id";
    final String API_KEY_PARAM = "api_key";
    final String BASE_URL_ORIGINAL_IMAGE = "https://image.tmdb.org/t/p/original";
    final String BASE_URL_w500_IMAGE = "https://image.tmdb.org/t/p/w500";



    @GET("movie/{sort_by}")
    Call<MovieResponse> getMovies(@Path(SORT_BY_PARAM) String sortOrder , @Query(API_KEY_PARAM) String apiKey);


    @GET("movie/{id}/videos")
    Call<Trailers> getTrailers(@Path(ID) String movieId, @Query(API_KEY_PARAM) String apiKey);

    @GET("movie/{id}/reviews")
    Call<Reviews> getReviews(@Path(ID) String movieId, @Query(API_KEY_PARAM) String apiKey);
}

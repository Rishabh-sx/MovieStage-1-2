package com.rishabh.moviestage.data;

import com.rishabh.moviestage.pojo.MovieResponse;
import com.rishabh.moviestage.pojo.reviews.Reviews;
import com.rishabh.moviestage.pojo.trailers.Trailers;

import okhttp3.ResponseBody;
import retrofit2.Call;


interface IDataManager {

   Call<MovieResponse> getMovies(String sortOrder, String apiKey);

   Call<Trailers> getTrailers(String movieId, String apiKey);

    Call<Reviews> getReviews(String movieId, String apiKey);
}

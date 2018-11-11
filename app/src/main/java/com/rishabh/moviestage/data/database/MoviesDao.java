package com.rishabh.moviestage.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.rishabh.moviestage.pojo.Result;

import java.util.List;

@Dao
public interface MoviesDao {

    @Query("SELECT * FROM movies")
    LiveData<List<Result>> loadAllMovies();


    @Insert
    void insertMovie(Result taskEntry);

    @Insert
    void insertReview(com.rishabh.moviestage.pojo.reviews.Result review);

    @Insert
    void insertTrailer(com.rishabh.moviestage.pojo.trailers.Result trailer);


    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovie(Result taskEntry);

    @Delete
    void deleteTask(Result taskEntry);

    @Query("SELECT * FROM movies WHERE id = :id")
    LiveData<Result> loadMovieById(int id);

    @Query("SELECT * FROM reviews WHERE movie_id=:movieId")
    LiveData<List<com.rishabh.moviestage.pojo.reviews.Result>> loadReviews(final int movieId);

    @Query("SELECT * FROM trailers WHERE movie_id=:movieId")
    LiveData<List<com.rishabh.moviestage.pojo.trailers.Result>> loadTrailers(final int movieId);

    @Query("DELETE FROM movies")
    void deleteAll();

    @Query("SELECT * FROM movies WHERE isFav =1")
    LiveData<List<Result>> loadFavMovies();
}
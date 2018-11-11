package com.rishabh.moviestage.movielist;

import com.rishabh.moviestage.base.BaseModelListener;
import com.rishabh.moviestage.pojo.MovieResponse;


interface MovieListModelListener extends BaseModelListener {

    void onMoviesResponse(MovieResponse dog);

    void hideLoader();
}

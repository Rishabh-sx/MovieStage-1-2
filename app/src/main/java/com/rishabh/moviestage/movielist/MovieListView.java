package com.rishabh.moviestage.movielist;

import com.rishabh.moviestage.base.BaseView;
import com.rishabh.moviestage.pojo.MovieResponse;


public interface MovieListView extends BaseView {

    void onMoviesResponse(MovieResponse dog);
}

package com.rishabh.moviestage.movielist;

import com.rishabh.moviestage.base.BasePresenter;
import com.rishabh.moviestage.pojo.MovieResponse;




public class MovieListPresenter extends BasePresenter<MovieListView> implements MovieListModelListener {


    private MovieListModel model;

    public MovieListPresenter(MovieListView view) {
        super(view);
    }

    @Override
    protected void setModel() {
        model = new MovieListModel(this);
    }

    @Override
    protected void destroy() {
        model.detachListener();
        model = null;
    }

    @Override
    protected void initView() {

    }

    public void getMovies(String filterBy) {
        if(getView()!=null)
            getView().showProgressDialog();
        model.getMovies(filterBy);
    }

    @Override
    public void onMoviesResponse(MovieResponse dog) {
        if(getView()!=null)
            getView().hideProgressDialog();
        getView().onMoviesResponse(dog);

    }



    @Override
    public void hideLoader() {
        if (getView()!=null)
            getView().hideProgressDialog();
    }
}

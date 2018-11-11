package com.rishabh.moviestage.moviedetail;

import com.rishabh.moviestage.base.BasePresenter;
import com.rishabh.moviestage.pojo.Result;
import com.rishabh.moviestage.pojo.reviews.Reviews;
import com.rishabh.moviestage.pojo.trailers.Trailers;

/**
 * Created by appinventiv on 27/3/18.
 */


public class MovieDetailPresenter extends BasePresenter<MovieDetailView> implements MovieDetailModelListener {


    private MovieDetailModel model;

    public MovieDetailPresenter(MovieDetailView view) {
        super(view);
    }

    @Override
    protected void setModel() {
        model = new MovieDetailModel(this);
    }

    @Override
    protected void destroy() {
        model.detachListener();
        model = null;
    }

    @Override
    protected void initView() {
        if(getView()!=null)
            getView().initViews();
    }

    public void getTrailers(String movieId) {
        model.getTrailers(movieId);
    }

    public  void  getReviews(String movieId){
        model.getReviews(movieId);
    }

    @Override
    public void onTrailersResponse(Trailers trailers) {
        /*if(getView()!=null)
            getView().setupTrailersView(trailers);*/
    }

    @Override
    public void onReviewsResponse(Reviews reviews) {
        /*if(getView()!=null)
            getView().setupReviewsView(reviews);*/

    }

    public void setMovieFav(Result b) {
        model.setUserMoviePreference(b);
    }
}

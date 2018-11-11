package com.rishabh.moviestage.moviedetail;

import com.rishabh.moviestage.base.BaseModelListener;
import com.rishabh.moviestage.pojo.MovieResponse;
import com.rishabh.moviestage.pojo.reviews.Reviews;
import com.rishabh.moviestage.pojo.trailers.Trailers;

/**
 * Created by appinventiv on 27/3/18.
 */

interface MovieDetailModelListener extends BaseModelListener {

    void onTrailersResponse(Trailers trailers);

    void onReviewsResponse(Reviews reviews);
}

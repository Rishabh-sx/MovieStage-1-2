package com.rishabh.moviestage.base;

import com.rishabh.moviestage.pojo.FailureResponse;


public interface BaseView {

    void showNoNetworkError();
    void showToastLong(String message);
    void showSpecificError(FailureResponse failureResponse);
    void showProgressDialog();
    void hideProgressDialog();
}

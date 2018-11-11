package com.rishabh.moviestage.base;

import com.rishabh.moviestage.pojo.FailureResponse;


public interface BaseModelListener {
    void noNetworkError();
    void onErrorOccurred(FailureResponse failureResponse);
}
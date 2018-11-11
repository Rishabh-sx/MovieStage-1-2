package com.rishabh.moviestage.network;


/**
 * This is to be used for handling common responses
 * such as no network or authentication failed
 * */

public interface CommonResponseHandler {
    void onNetworkError();
}

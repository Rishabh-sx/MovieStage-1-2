package com.rishabh.moviestage.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import com.rishabh.moviestage.R;


/**
 * Provides convenience methods and abstractions to some tasks in Android
 * <p/>
 * <br/>
 * <br/>
 *
 * @author Jay
 */
public class AppUtils {
    private static AppUtils appUtils;
    private ProgressDialog mDialog;
    //private static final String TAG = AppUtils.class.getSimpleName();


    public static AppUtils getInstance() {
        if (appUtils == null) {
            appUtils = new AppUtils();
        }
        return appUtils;
    }


    /**
     * To show progress dialog for agent while api hit
     *
     * @param context Context of view
     */
    public void showProgressDialogForAgent(Context context) {
        hideProgressDialog();
        if (!((Activity) context).isFinishing()) {
            mDialog = new ProgressDialog(context, R.style.MyTheme);
            mDialog.setIndeterminate(true);
            mDialog.setCancelable(false);
            mDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            mDialog.setIndeterminateDrawable(context.getResources().getDrawable(R.drawable.
                    drawable_progress_loader_teal));
            mDialog.show();
        }
    }

    /**
     * hide progress dialog if open
     */
    public void hideProgressDialog() {
        if (mDialog != null && mDialog.isShowing())
            mDialog.dismiss();
    }


}
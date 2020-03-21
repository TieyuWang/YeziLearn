package com.yezi.yezilearn.ioc;

import android.app.Activity;
import android.view.View;

import androidx.fragment.app.Fragment;

/**
 * @author : yezi
 * @date : 2020/2/28 17:12
 * desc   :
 * version: 1.0
 */
public class ViewFinder {
    private Activity activity;
    private View view;
    public ViewFinder(Activity activity) {
        this.activity = activity;
    }

    public ViewFinder(View view) {
        this.view = view;
    }

    public ViewFinder(Fragment fragment) {
        this.activity = fragment.getActivity();
    }

    public View findViewById(int viewId){
        return activity != null ? activity.findViewById(viewId) : view.findViewById(viewId);
    }
}

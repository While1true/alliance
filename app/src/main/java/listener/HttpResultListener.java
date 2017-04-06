/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package listener;

import android.util.Log;

import Constance.GlobalDatas;


/**
 * Created by S0005 on 2017/3/17.
 */

public abstract class HttpResultListener {
    public abstract void success(int code, String message, String resultCode);

    public void login() {
    }

    public void failed(String failed) {
        if (GlobalDatas.DEBUG)
            Log.i(GlobalDatas.TAG, "failed: "+failed);
    }

    public void start() {
    }
    public void finish() {
    }
}

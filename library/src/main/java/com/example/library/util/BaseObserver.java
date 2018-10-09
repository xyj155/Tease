package com.example.library.util;

import android.util.Log;



import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Observer;

/**
 * @author : fada
 *         Date : 2018/5/5
 *         Description : input your description
 */
public abstract class BaseObserver<T> implements Observer<T> {
    private static final String TAG = "BaseObserver";


    //    @Override
    //    public abstract void onNext(Object o);

    /**
     * 拦截错误信息,转化为用户可以看懂的文字
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {


        Log.d(TAG, "onError() called with: e = [" + e + "]");
      if (e instanceof ConnectException) {
            onError("网络无法连接,请检查网络");
        } else if (e instanceof SocketTimeoutException) {
            onError("网络连接超时,请重试");
        } else if (e instanceof IOException) {
            onError(e.getMessage());
        }

    }

    //    @Override
    //    public abstract void onComplete();

    /**
     * 错误的详细信息
     *
     * @param error
     */
    public abstract void onError(String error);


}

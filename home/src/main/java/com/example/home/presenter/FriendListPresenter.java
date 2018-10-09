package com.example.home.presenter;

import android.util.Log;
import android.widget.Toast;

import com.example.home.contract.FriendListContract;
import com.example.home.model.FriendListModel;
import com.example.library.base.BaseGson;
import com.example.library.gson.ContactGson;
import com.example.library.util.BaseObserver;
import com.example.library.util.ToastUtils;

import rx.Observer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static cn.jpush.android.api.b.n;
import static cn.jpush.android.api.b.v;

/**
 * Created by Xuyijie on 2018/10/7.
 */

public class FriendListPresenter implements FriendListContract.Presenter {
    private FriendListModel model = new FriendListModel();
    private FriendListContract.View view;

    public FriendListPresenter(FriendListContract.View view) {
        this.view = view;
    }

    private static final String TAG = "FriendListPresenter";

    @Override
    public void getUserContactList(String userid) {
        view.loading();
        model.getUserContactList(userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<ContactGson>>() {
                    @Override
                    public void onCompleted() {
                        view.hide();
                    }


                    @Override
                    public void onError(String error) {
                        view.hide();
                        ToastUtils.getInstance().showText("获取好友列表");
                        Log.i(TAG, "onError: " + error);
                    }

                    @Override
                    public void onNext(BaseGson<ContactGson> contactGsonBaseGson) {
                        view.hide();
                        view.loadUserContactList(contactGsonBaseGson.getData());
                    }
                });
    }
}

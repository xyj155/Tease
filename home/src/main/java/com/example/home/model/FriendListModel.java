package com.example.home.model;

import com.example.home.contract.FriendListContract;
import com.example.library.base.BaseGson;
import com.example.library.gson.ContactGson;
import com.example.library.http.RetrofitUtil;

import rx.Observable;

/**
 * Created by Xuyijie on 2018/10/7.
 */

public class FriendListModel implements FriendListContract.Model {
    @Override
    public Observable<BaseGson<ContactGson>> getUserContactList(String userid) {
        return RetrofitUtil.getInstance(RetrofitUtil.BASE_URL).getServerices().getUserContactList(userid);
    }
}

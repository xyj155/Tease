package com.example.library.api;

import com.example.library.base.BaseGson;
import com.example.library.gson.ContactGson;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Xuyijie on 2018/10/7.
 */

public interface API {

    @FormUrlEncoded
    @POST("/QQ/public/index.php/index/User/getUserContactList")
    Observable<BaseGson<ContactGson>> getUserContactList(@Field("user_id")String uid);
}

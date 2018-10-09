package com.example.home.contract;

import com.example.library.base.BaseGson;
import com.example.library.gson.ContactGson;

import java.util.List;

import rx.Observable;

/**
 * Created by Xuyijie on 2018/10/7.
 */

public interface FriendListContract {
    interface Model {
        Observable<BaseGson<ContactGson>> getUserContactList(String userid);
    }

    interface View {
        void loadUserContactList(List<ContactGson> list);

        void loading();

        void hide();
    }

    interface Presenter {
        void getUserContactList(String userid);
    }
}

package com.example.home.contract;

import com.example.home.gson.HomeMessage;

import java.util.List;

/**
 * Created by Xuyijie on 2018/10/12.
 */

public interface MessageContract {
    interface Model {

    }

    interface View {
        void showLoading();

        void hideLoading();

        void setMessageList(List<HomeMessage> messageList);

        void login();
    }

    interface Presenter {

        void login(String username, String password);

        void showMessageList();
    }
}

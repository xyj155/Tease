package com.example.home.presenter;

import android.util.Log;

import com.example.home.contract.MessageContract;
import com.example.home.gson.HomeMessage;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

/**
 * Created by Xuyijie on 2018/10/12.
 */

public class MessagePresenter implements MessageContract.Presenter {
    private List<HomeMessage> homeMessages = new ArrayList<>();
    private List<Conversation> conversationList;
    private MessageContract.View view;

    public MessagePresenter(MessageContract.View view) {
        this.view = view;
    }

    @Override
    public void login(String username, String password) {
        view.showLoading();
        JMessageClient.login(username, password, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if (i == 0) {
                    view.hideLoading();
                    view.login();
                } else {
                    view.hideLoading();
                }
            }
        });
    }

    @Override
    public void showMessageList() {
        conversationList = JMessageClient.getConversationList();
        for (Conversation conversation : conversationList) {
            TextContent content = (TextContent) conversation.getLatestMessage().getContent();
            UserInfo targetInfo = (UserInfo) conversation.getTargetInfo();
            homeMessages.add(new HomeMessage(targetInfo.getAvatar(), targetInfo.getUserName(), content.getText()));
        }
        view.setMessageList(homeMessages);
    }
}

package com.example.conversation.presenter;

import android.util.Log;

import com.example.conversation.contract.ConversationContract;
import com.example.conversation.entity.ConversationEntity;
import com.example.library.util.IMUtils;
import com.example.library.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.MessageContent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.api.BasicCallback;

import static android.R.id.message;

/**
 * Created by Xuyijie on 2018/10/9.
 */

public class ConversationPresenter implements ConversationContract.Presenter {
    private ConversationContract.View view;
    private static final String TAG = "ConversationPresenter";

    public ConversationPresenter(ConversationContract.View view) {
        this.view = view;
    }

    /**
     *
     * @param message
     */
    @Override
    public void sendMessage(Message message) {
        if (message == null) {
            return;
        }
        message.setOnSendCompleteCallback(new BasicCallback() {
            @Override
            public void gotResult(int code, String s) {
                if (code == IMUtils.CODE_SUCCESS) {

                } else {
                    ToastUtils.getInstance().showText("发送失败，服务器错误");
                    Log.i(TAG, "gotResult: " + s + code);
                }
            }
        });
        JMessageClient.sendMessage(message);
    }

    @Override
    public void conversionToUser(List<Conversation> conversations) {
        conversations = JMessageClient.getConversationList();
    }

    /**
     * 信息赚实体类
     * @param message
     * @param username
     */
    @Override
    public void messageToEntity(List<Message> message,String username) {
        Log.i(TAG, "messageToEntity: " + message.size());
        List<ConversationEntity> list = new ArrayList<>();
        for (Message message1 : message) {
            if (message1.getFromName().equals(username)) {
                list.add(ConversationEntity.client(message1));
            } else {
                list.add(ConversationEntity.chat(message1));
            }
        }
        view.getConversation(list);
    }

    /**
     * 登陆
     * @param username
     * @param password
     */
    @Override
    public void login(String username,String password) {
        view.showDialog("数据加载中...");
        IMUtils.login(username, password, new BasicCallback() {
            @Override
            public void gotResult(int i, final String s) {
                if (i == 0) {
                    view.hideDialog();
                    view.loginSuccess();
                } else {
                    view.hideDialog();
                }
            }
        });
    }

    /**
     * 历史聊天记录
     * @param username
     */

    @Override
    public void getHistoryMessage(String username) {
        Conversation conversation = JMessageClient.getSingleConversation(username);
        List<Message> allMessage = conversation.getAllMessage();
        List<ConversationEntity> list = new ArrayList<>();
        for (Message message1 : allMessage) {
            if (message1.getFromName().equals(username)) {
                list.add(ConversationEntity.client(message1));
            } else {
                list.add(ConversationEntity.chat(message1));
            }
        }
        view.loadHistoryMessage(list);
    }
}

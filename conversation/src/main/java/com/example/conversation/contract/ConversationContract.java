package com.example.conversation.contract;

import com.example.conversation.entity.ConversationEntity;

import java.util.List;

import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;

/**
 * Created by Xuyijie on 2018/10/9.
 */

public interface ConversationContract {
    interface Model {
    }

    interface View {
        /**
         * 刷新消息
         */
        void refreshMessage();

        /**
         * 获取会话
         */
        void setConversation(List<ConversationEntity> conversationEntities);

        /**
         * 登录是否成功
         */
        void loginSuccess();

        void loadHistoryMessage(List<ConversationEntity> messages);

        void showDialog(String msg);

        void hideDialog();
    }

    interface Presenter {
        /**
         * 发送消息
         *
         * @param message
         */
        void sendMessage(Message message);

        /**
         * 会话列表
         *
         * @param conversations
         */
        void conversionToUser(List<Conversation> conversations);

        void messageToEntity(List<Message> message,String username);

        /**
         * im登录
         */
        void login(String username,String password);

        void getHistoryMessage(String username,int page);
    }
}

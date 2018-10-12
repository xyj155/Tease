package com.example.conversation.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import cn.jpush.im.android.api.model.Message;

public class ConversationEntity implements MultiItemEntity {

    public static final int TYPE_CHAT_MESSAGE = 1;

    public static final int TYPE_CLIENT_MESSAGE = 2;


    private int itemType;

    private Message data;

    public ConversationEntity(int itemType, Message data) {
        this.itemType = itemType;
        this.data = data;
    }

    /**
     * @param data
     * @return
     */
    public static ConversationEntity chat(Message data) {
        return new ConversationEntity(TYPE_CHAT_MESSAGE, data);
    }

    /**
     * @param data
     * @return
     */
    public static ConversationEntity client(Message data) {
        return new ConversationEntity(TYPE_CLIENT_MESSAGE, data);
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public Message getData() {
        return data;
    }

    public void setData(Message data) {
        this.data = data;
    }
}
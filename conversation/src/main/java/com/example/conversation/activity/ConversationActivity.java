package com.example.conversation.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.conversation.R;
import com.example.conversation.adapter.ConversationAdapter;
import com.example.conversation.contract.ConversationContract;
import com.example.conversation.entity.ConversationEntity;
import com.example.conversation.listener.EndLessOnScrollListener;
import com.example.conversation.presenter.ConversationPresenter;
import com.example.library.arouter.ArouterUrl;
import com.example.library.base.BaseActivity;
import com.example.library.util.IMUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.event.NotificationClickEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;

import static android.R.id.list;


@Route(path = ArouterUrl.CONVERSATION)
public class ConversationActivity extends BaseActivity implements ConversationContract.View, View.OnClickListener {

    private ConversationPresenter presenter;
    private EditText etInput;
    private TextView tvUsername;
    private Button btnSubmit;
    private List<Message> messages = new ArrayList<>();
    public Conversation singleConversation;
    private ConversationAdapter conversationAdapter = new ConversationAdapter(null);
    private RecyclerView ryConversation;
    private TextView tvClose;
    private LinearLayoutManager linearLayoutManager;
    private String fromID;
    private int count;
    private SmartRefreshLayout slChat;
    private List<ConversationEntity> conversationEntity = new ArrayList<>();

    @Override
    public int intiLayout() {
        return R.layout.activity_conversation;
    }

    @Override
    public void initView() {
        // TODO: add setContentView(...) invocation
        etInput = findViewById(R.id.et_input);
        slChat = findViewById(R.id.rl_chat);
        tvUsername = findViewById(R.id.tv_username);
        btnSubmit = findViewById(R.id.btn_submit);
        ryConversation = findViewById(R.id.ry_conversation);
        tvClose = findViewById(R.id.tv_close);
        tvClose.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        linearLayoutManager = new LinearLayoutManager(ConversationActivity.this, LinearLayoutManager.VERTICAL, false);
        ryConversation.setLayoutManager(linearLayoutManager);
        ryConversation.setHasFixedSize(true);
        conversationAdapter.notifyDataSetChanged();
        ryConversation.setFocusableInTouchMode(false);
        ryConversation.setHasFixedSize(true);
        ryConversation.setAdapter(conversationAdapter);
        slChat.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {

                if (count > 0) {
                    Log.i(TAG, "onRefresh: count=" + count);
                    List<Message> messagesFromOldest = new ArrayList<Message>();
                    List<ConversationEntity> list = new ArrayList<ConversationEntity>();
                    if (count > 12) {//这个是极光SDK 第一个参数是请求的位置  第二个是请求的条数 减一
                        count = count - 12;//每次获取12个
                        List<Message> messagesFromOldest1 = singleConversation.getMessagesFromOldest(count, 12);
                        messagesFromOldest.addAll(messagesFromOldest1);//获取位置   获取条数
                        Log.i(TAG, "onRefresh: " + messagesFromOldest1.size());
                    } else { // 第二次加载的数据比较多啊，不是 12 条了吧等我回家帮你看看吧，我路上想想我要下班了 好
                        List<Message> messagesFromOldest1 = singleConversation.getMessagesFromOldest(0, count);
                        messagesFromOldest.addAll(messagesFromOldest1);//获取位置   获取条数
                        Log.i(TAG, "onRefresh231: " + messagesFromOldest1.size());
                        count = 0;
                    }
                    for (Message message1 : messagesFromOldest) {
                        TextContent content = (TextContent) message1.getContent();
//                        Log.i(TAG, "onRefresh: " + content.getText());
                        if (message1.getFromName().equals("123456")) {
                            list.add(ConversationEntity.client(message1));
                        } else {
                            list.add(ConversationEntity.chat(message1));
                        }
                    }
                    slChat.finishRefresh(200);
                    conversationAdapter.addData(0, list);
                    list.clear();
                }
            }
        });
    }


    public void onEvent(NotificationClickEvent event) {
        //判断当前activity是否在顶层
        if (isForeground(this, "com.example.conversation.activity" +
                ".ConversationActivity")) {
            Message message = event.getMessage();
            fromID = message.getFromID();
            createConversation();
            refreshMessage();
        } else {
            Intent notificationIntent = new Intent(this, ConversationActivity.class);
            this.startActivity(notificationIntent);
        }
    }

    /**
     * 创建会话
     */
    private void createConversation() {
        singleConversation = IMUtils.cretaeConversation(fromID);
        if (singleConversation != null) {
            tvUsername.setText(singleConversation.getTitle());
        }
    }

    @Override
    public void initData() {
        presenter = new ConversationPresenter(this);
        presenter.login("123456", "123456");
        btnSubmit.setTextColor(0xff000000);
        btnSubmit.setBackgroundResource(R.drawable.btn_submit_bg_normal);
        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) {
                    btnSubmit.setBackgroundResource(R.drawable.btn_submit_bg_normal);
                    btnSubmit.setTextColor(0xff000000);
                    btnSubmit.setClickable(false);
                } else {
                    btnSubmit.setTextColor(0xffffffff);
                    btnSubmit.setBackgroundResource(R.drawable.btn_submit_bg_pressed);
                    btnSubmit.setClickable(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JMessageClient.registerEventReceiver(this);

    }


    @Override
    public void refreshMessage() {

    }

    /**
     * 创建对话
     *
     * @param conversationEntities
     */
    @Override
    public void setConversation(List<ConversationEntity> conversationEntities) {
        conversationAdapter.closeLoadAnimation();
        conversationEntity.addAll(conversationEntities);
        conversationAdapter.replaceData(conversationEntity);
        conversationAdapter.notifyDataSetChanged();
        ryConversation.setFocusableInTouchMode(false);
        Log.i(TAG, "setConversation: " + singleConversation.getAllMessage().size());
        ryConversation.smoothScrollToPosition(singleConversation.getAllMessage().size());
    }

    /**
     * 登陆成功
     */
    @Override
    public void loginSuccess() {
        singleConversation = Conversation.createSingleConversation("123456");
        count = singleConversation.getAllMessage().size();//获取历史消息总条数 1
        Log.i(TAG, "loginSuccess: " + count);
        count = count - 12;//减去已经获取的
        presenter.getHistoryMessage("123456", count);//获取历史聊天记录
        ryConversation.smoothScrollToPosition(conversationAdapter.getItemCount());//滑动到最后一个item
    }

    /**
     * 获取历史聊天记录
     *
     * @param messages
     */

    @Override
    public void loadHistoryMessage(List<ConversationEntity> messages) {
        conversationAdapter.setStartUpFetchPosition(messages.size());
        conversationEntity.addAll(messages);
        conversationAdapter.replaceData(conversationEntity);
        Log.i(TAG, "loadHistoryMessage: ");
    }

    @Override
    public void showDialog(String msg) {
        showmDialog(msg);
    }

    @Override
    public void hideDialog() {
        hidemDialog();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                String content = etInput.getText().toString();
                Log.i(TAG, "onClick: " + content);
                if (singleConversation != null) {
                    if (!etInput.getText().toString().isEmpty()) {
                        Message message = IMUtils.sendTextMessage(singleConversation, content);
                        etInput.setText("");
                        messages.add(message);
                        presenter.sendMessage(message);
                        presenter.messageToEntity(messages, "123456");
                        messages.clear();
                    }
                }
                break;
            case R.id.tv_close:
                finish();
                break;
        }
    }
}

package com.example.conversation.activity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
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
import com.example.conversation.presenter.ConversationPresenter;
import com.example.library.arouter.ArouterUrl;
import com.example.library.base.BaseActivity;
import com.example.library.util.IMUtils;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.NotificationClickEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.api.BasicCallback;

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

    @Override
    public int intiLayout() {
        return R.layout.activity_conversation;
    }

    @Override
    public void initView() {
        // TODO: add setContentView(...) invocation
        etInput = findViewById(R.id.et_input);
        tvUsername = findViewById(R.id.tv_username);
        btnSubmit = findViewById(R.id.btn_submit);
        ryConversation = findViewById(R.id.ry_conversation);
        tvClose = findViewById(R.id.tv_close);
        tvClose.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        linearLayoutManager = new LinearLayoutManager(ConversationActivity.this, LinearLayoutManager.VERTICAL, false);
        ryConversation.smoothScrollToPosition(conversationAdapter.getItemCount());
        ryConversation.setLayoutManager(linearLayoutManager);
        ryConversation.setHasFixedSize(true);
        conversationAdapter.closeLoadAnimation();
        conversationAdapter.notifyDataSetChanged();
        ryConversation.setFocusableInTouchMode(false);
        ryConversation.setHasFixedSize(true);
        ryConversation.setAdapter(conversationAdapter);
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

    @Override
    public void setConversation(List<ConversationEntity> conversationEntities) {
        conversationAdapter.closeLoadAnimation();
        conversationAdapter.replaceData(conversationEntities);
        Log.i(TAG, "setConversation: ");
        ryConversation.setFocusableInTouchMode(false);
        Log.i(TAG, "setConversation: " + singleConversation.getAllMessage().size());
        ryConversation.smoothScrollToPosition(singleConversation.getAllMessage().size());
        conversationAdapter.notifyDataSetChanged();
    }

    @Override
    public void loginSuccess() {
        singleConversation = Conversation.createSingleConversation("123456");
        presenter.getHistoryMessage("123456");
    }

    @Override
    public void loadHistoryMessage(List<ConversationEntity> messages) {
        conversationAdapter.replaceData(messages);
        Log.i(TAG, "loadHistoryMessage: ");
        ryConversation.smoothScrollToPosition(singleConversation.getAllMessage().size());
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
//                        conversationAdapter.addData(messages);
                        presenter.messageToEntity(messages, "123456");
                    }
                }
                break;
            case R.id.tv_close:
                finish();
                break;
        }
    }
}

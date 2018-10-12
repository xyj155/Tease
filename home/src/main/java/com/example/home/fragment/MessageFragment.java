package com.example.home.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.home.R;
import com.example.home.contract.MessageContract;
import com.example.home.gson.HomeMessage;
import com.example.home.presenter.MessagePresenter;
import com.example.library.base.BaseFragment;
import com.example.library.util.IMUtils;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.MessageContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

/**
 * Created by Xuyijie on 2018/10/5.
 */
@Route(path = "/fragment/message")
public class MessageFragment extends BaseFragment implements MessageContract.View {
    private RecyclerView ryMessage;
    private static final String TAG = "MessageFragment";
    private MessagePresenter presenter;

    @Override
    protected int setView() {
        return R.layout.fragment_message;
    }

    @Override
    protected void init(View view) {
        presenter = new MessagePresenter(this);
        ryMessage = view.findViewById(R.id.ry_message);
        ryMessage.setLayoutManager(new LinearLayoutManager(getContext()));
        presenter.login("123456", "123456");
//        JMessageClient.login("123456", "123456", new BasicCallback() {
//            @Override
//            public void gotResult(int i, String s) {
//                Log.i(TAG, "gotResult: " + i + s);
//                if (i == 0) {
//                    conversationList = JMessageClient.getConversationList();
//                    for (Conversation conversation : conversationList) {
//                        TextContent content = (TextContent) conversation.getLatestMessage().getContent();
//                        UserInfo targetInfo = (UserInfo) conversation.getTargetInfo();
//                        homeMessages.add(new HomeMessage(targetInfo.getAvatar(), targetInfo.getUserName(), content.getText()));
//                    }
//                    MessageAdapter adapter = new MessageAdapter(homeMessages);
//                    ryMessage.setAdapter(adapter);
//                }
//            }
//        });

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    public void showLoading() {
        showDialog();
    }

    @Override
    public void hideLoading() {
        hideDialog();
    }

    @Override
    public void setMessageList(List<HomeMessage> messageList) {
        MessageAdapter adapter = new MessageAdapter(messageList);
        ryMessage.setAdapter(adapter);
    }

    @Override
    public void login() {
        presenter.showMessageList();
    }

    private class MessageAdapter extends BaseQuickAdapter<HomeMessage, BaseViewHolder> {

        public MessageAdapter(List<HomeMessage> data) {
            super(R.layout.ry_message_list_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, HomeMessage item) {
            helper.setText(R.id.tv_message, item.getMessage())
                    .setText(R.id.tv_username, item.getUsername());
            Glide.with(getContext()).load(item.getAvatar()).asBitmap().error(R.mipmap.qq).into((ImageView) helper.getView(R.id.iv_avatar));
        }
    }
}

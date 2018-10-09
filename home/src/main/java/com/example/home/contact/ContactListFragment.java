package com.example.home.contact;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.home.R;
import com.example.home.adapter.ContactGroupAdapter;
import com.example.home.contract.FriendListContract;
import com.example.home.entity.GroupName;
import com.example.home.entity.GroupUserDetail;
import com.example.home.presenter.FriendListPresenter;
import com.example.library.base.BaseFragment;
import com.example.library.gson.ContactGson;
import com.example.library.weight.dialog.SweetAlertDialog;
import com.example.library.weight.loading.TranslationBallLoading;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static cn.jpush.android.api.b.j;
import static cn.jpush.android.api.b.l;
import static cn.jpush.android.api.b.v;


/**
 * Created by Xuyijie on 2018/10/6.
 */

public class ContactListFragment extends BaseFragment implements FriendListContract.View {
    private RecyclerView ryGroup;

    private FriendListPresenter presenter;

    @Override
    protected int setView() {
        return R.layout.fragment_contact_list;
    }

    @Override
    protected void init(View view) {
        ryGroup = view.findViewById(R.id.ry_group);
        ryGroup.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        presenter = new FriendListPresenter(this);
        presenter.getUserContactList("1");
    }

    private static final String TAG = "ContactListFragment";

    @Override
    public void loadUserContactList(List<ContactGson> list) {
        ArrayList<MultiItemEntity> res = new ArrayList<>();
        List<GroupName> lv0 = new ArrayList<>();
        lv0.clear();
        for (int i = 0; i < list.size(); i++) {
            lv0.add(new GroupName(list.get(i).getGroup(), list.get(i).getList().size()));
            for (int j = 0; j < list.get(i).getList().size(); j++) {
                GroupUserDetail lv1 = new GroupUserDetail(list.get(i).getList().get(j).getUsername(), list.get(i).getList().get(j).getSign(), list.get(i).getList().get(j).getAvatar());
                lv0.get(i).addSubItem(lv1);
            }
            res.add(lv0.get(i));
        }
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        ryGroup.setLayoutManager(manager);
        ContactGroupAdapter adapter = new ContactGroupAdapter(res, getContext());
        ryGroup.setAdapter(adapter);

    }

    @Override
    public void loading() {
        showDialog();
    }

    @Override
    public void hide() {
        hideDialog();
    }
}

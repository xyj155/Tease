package com.example.home;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.home.fragment.ActiveFragment;
import com.example.home.fragment.ContactFragment;
import com.example.home.fragment.MessageFragment;
import com.example.library.base.BaseActivity;
import com.example.library.weight.NavMenuLayout;


import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Xuyijie on 2018/10/5.
 */
@Route(path = "/home/main", group = "home")
public class HomeActivity extends BaseActivity implements NavMenuLayout.OnItemSelectedListener {
    private FragmentManager fragmentManager;
    //fragment 列表
    private ActiveFragment activeFragment;
    private MessageFragment fragmentMessage;
    private ContactFragment contactFragment;

    FrameLayout flContainer;
    NavMenuLayout navLayout;
    private int[] iconRes = {R.mipmap.home_bottom_message_normal, R.mipmap.home_bottom_contact_normal, R.mipmap.home_bottom_active_normal};
    private int[] iconResSelected = {R.mipmap.home_bottom_message_selected, R.mipmap.home_bottom_contact_selected, R.mipmap.home_bottom_active_selected};
    private String[] textRes = {"消息", "联系人", "动态"};

    @Override
    public int intiLayout() {
        return R.layout.activity_home;
    }

    @Override
    public void initView() {
        flContainer = findViewById(R.id.fl_container);
        navLayout = findViewById(R.id.nav_layout);
        navLayout.setIconRes(iconRes)//设置未选中图标
                .setIconResSelected(iconResSelected)//设置选中图标
                .setTextRes(textRes)//设置文字
                .setMsg(0, "99+")//设置显示消息
                .setTextColorSelected(0xff1296db)
                .setSelected(0)
                .setOnItemSelectedListener(this);//设置选中的位置
        showFirstPosition();
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
    private void showFirstPosition(){
        fragmentManager = getSupportFragmentManager();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        fragmentMessage = new MessageFragment();
        transaction.add(R.id.fl_container, fragmentMessage);
        transaction.commit();
    }

    @Override
    public void onItemSelected(int i) {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideAllFragment(transaction);
        switch (i) {
            case 0:
                if (fragmentMessage == null) {
                    fragmentMessage = new MessageFragment();
                    transaction.add(R.id.fl_container, fragmentMessage);
                } else {
                    transaction.show(fragmentMessage);
                }
                break;
            case 1:
                if (contactFragment == null) {
                    contactFragment = new ContactFragment();
                    transaction.add(R.id.fl_container, contactFragment);
                } else {
                    transaction.show(contactFragment);
                }
                break;
            case 2:
                if (activeFragment == null) {
                    activeFragment = new ActiveFragment();
                    transaction.add(R.id.fl_container, activeFragment);
                } else {
                    transaction.show(activeFragment);
                }
                break;
        }
        transaction.commit();
    }
    public void hideAllFragment(FragmentTransaction transaction) {
        if (fragmentMessage != null) {
            transaction.hide(fragmentMessage);
        }
        if (contactFragment != null) {
            transaction.hide(contactFragment);
        }
        if (activeFragment != null) {
            transaction.hide(activeFragment);
        }
    }

}

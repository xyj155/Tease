package com.example.home.activity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.CycleInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.example.home.R;
import com.example.home.fragment.ActiveFragment;
import com.example.home.fragment.ContactFragment;
import com.example.home.fragment.MessageFragment;
import com.example.library.arouter.ArouterUrl;
import com.example.library.base.BaseActivity;
import com.example.library.weight.bottom.NavMenuLayout;
import com.example.library.weight.drawlayout.DragLayout;
import com.example.library.weight.drawlayout.MainContentLinearLayout;
import com.gyf.barlibrary.ImmersionBar;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

import static android.R.attr.password;
import static cn.jpush.im.android.tasks.GetUserInfoListTask.IDType.username;


/**
 * Created by Xuyijie on 2018/10/5.
 */
@Route(path = ArouterUrl.HOMEACTIVITY)
public class HomeActivity extends BaseActivity implements NavMenuLayout.OnItemSelectedListener {
    Toolbar commonToolbar;
    TextView tvTitle;
    TextView tvMenu;
    ImageView ivAdd, ivHead;
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

    private DragLayout dragLayout;
    private MainContentLinearLayout mainContentLinearLayout;

    @Override
    public int intiLayout() {
        return R.layout.activity_home;
    }

    @Override
    public void initView() {

        ivHead = findViewById(R.id.iv_head);
        tvTitle = findViewById(R.id.tv_title);
        tvMenu = findViewById(R.id.tv_menu);
        ivAdd = findViewById(R.id.iv_add);
        commonToolbar = findViewById(R.id.common_toolbar);
        flContainer = findViewById(R.id.fl_container);
        navLayout = findViewById(R.id.nav_layout);
        dragLayout = findViewById(R.id.drag_layout);
        mainContentLinearLayout = findViewById(R.id.ll_main);
        JMessageClient.login("123456", "123456", new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                Log.i(TAG, "gotResult: "+i+s);
            }
        });
    }

    @Override
    public void initData() {
        ivAdd.setVisibility(View.VISIBLE);
        tvMenu.setVisibility(View.GONE);
        tvTitle.setText("消息");
        navLayout.setIconRes(iconRes)//设置未选中图标
                .setIconResSelected(iconResSelected)//设置选中图标
                .setTextRes(textRes)//设置文字
                .setMsg(0, "99+")//设置显示消息
                .setTextColorSelected(0xff1296db)
                .setSelected(0)
                .setOnItemSelectedListener(this);//设置选中的位置
        showFirstPosition();
        Glide.with(HomeActivity.this).load(R.mipmap.head).asBitmap().into(ivHead);
        dragLayout.setOnDragChangeListener(new DragLayout.OnDragChangeListener() {
            @Override
            public void OnClose() {
                ObjectAnimator animator = ObjectAnimator.ofFloat(ivHead, "translationX", 15);
                animator.setInterpolator(new CycleInterpolator(3));//设置环形差值器,来回移动4圈
                animator.setDuration(600);
                animator.start();
            }

            @Override
            public void OnOpen() {

            }

            @Override
            public void OnDraging(float percent) {
                ivHead.setAlpha(1 - percent);
            }
        });

        ivHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dragLayout.openLeftContent(true);
            }
        });
        mainContentLinearLayout.setDragLayout(dragLayout);
    }

    @Override
    public void onCreate( @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).init();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy(); //必须调用该方法，防止内存泄漏
    }

    private void showFirstPosition() {
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
                ivAdd.setVisibility(View.VISIBLE);
                tvMenu.setVisibility(View.GONE);
                tvTitle.setText("消息");
                if (fragmentMessage == null) {
                    fragmentMessage = (MessageFragment) ARouter.getInstance().build("/fragment/message").navigation();
                    transaction.add(R.id.fl_container, fragmentMessage);
                } else {
                    transaction.show(fragmentMessage);
                }
                break;
            case 1:
                ivAdd.setVisibility(View.GONE);
                tvMenu.setVisibility(View.VISIBLE);
                tvTitle.setText("联系人");
                tvMenu.setText("添加");
                if (contactFragment == null) {
                    contactFragment = (ContactFragment) ARouter.getInstance().build("/fragment/contact").navigation();
                    transaction.add(R.id.fl_container, contactFragment);
                } else {
                    transaction.show(contactFragment);
                }
                break;
            case 2:
                ivAdd.setVisibility(View.GONE);
                tvMenu.setVisibility(View.VISIBLE);
                tvMenu.setText("更多");
                tvTitle.setText("动态");
                if (activeFragment == null) {
                    activeFragment =  (ActiveFragment) ARouter.getInstance().build("/fragment/active").navigation();
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

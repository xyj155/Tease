package com.example.home.fragment;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.home.R;
import com.example.library.base.BaseFragment;

/**
 * Created by Xuyijie on 2018/10/5.
 */
@Route(path = "/fragment/active")
public class ActiveFragment extends BaseFragment {
    @Override
    protected int setView() {
        return R.layout.fragment_active;
    }

    @Override
    protected void init(View view) {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}

package com.example.home.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.home.R;
import com.example.home.contact.ContactListFragment;
import com.example.home.contact.DeviceFragment;
import com.example.home.contact.UserContactFragment;
import com.example.home.contact.GroupFragment;
import com.example.home.contact.ObserveFragment;
import com.example.library.base.BaseFragment;
import com.example.library.weight.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xuyijie on 2018/10/5.
 */

public class ContactFragment extends BaseFragment {
    private ViewPager vpHome;
    private PagerSlidingTabStrip ptHome;

    @Override
    protected int setView() {
        return R.layout.fragment_contact;
    }

    @Override
    protected void init(View view) {
        vpHome = view.findViewById(R.id.vp_home);
        ptHome = view.findViewById(R.id.pt_home);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        List<Fragment> list = new ArrayList<>();
        List<String > title = new ArrayList<>();
        title.add("好友");
        title.add("群组");
        title.add("设备");
        title.add("通讯录");
        title.add("公众号");
        list.add(new ContactListFragment());
        list.add(new GroupFragment());
        list.add(new UserContactFragment());
        list.add(new DeviceFragment());
        list.add(new ObserveFragment());
        vpHome.setAdapter(new ContactAdapter(getActivity().getSupportFragmentManager(), list,title));
        ptHome.setIndicatorColor(0xff000000);
        ptHome.setIndicatorHeight(11);
        ptHome.setIndicatorPadding(8);
        ptHome.setScrollOffset(5);
        ptHome.setTextSize(35);
        ptHome.setUnderlineHeight(1);
        ptHome.setUnderlineColor(0xffd8d8d8);
        ptHome.setDividerColor(0xffffffff);
        ptHome.setViewPager(vpHome);
    }

    private class ContactAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;
        private List<String> title;

        public ContactAdapter(FragmentManager fm, List<Fragment> fragments, List<String> title) {
            super(fm);
            this.fragments = fragments;
            this.title = title;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title.get(position);
        }
    }
}

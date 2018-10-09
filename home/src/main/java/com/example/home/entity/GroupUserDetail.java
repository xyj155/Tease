package com.example.home.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.home.adapter.ContactGroupAdapter;

public class GroupUserDetail implements MultiItemEntity {

    public String friendName;
    public String friendSculpture;
public String avatar;

    public GroupUserDetail(String friendName, String friendSculpture, String avatar) {
        this.friendName = friendName;
        this.friendSculpture = friendSculpture;
        this.avatar = avatar;
    }

    @Override
    public int getItemType() {
        return ContactGroupAdapter.TYPE_LEVEL_1;
    }
}


package com.example.home.entity;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.home.adapter.ContactGroupAdapter;

public class GroupName extends AbstractExpandableItem<GroupUserDetail> implements MultiItemEntity {

    public String friendGroup;
    public int count;

    public GroupName(String friendGroup, int count) {
        this.friendGroup = friendGroup;
        this.count = count;
    }



    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return ContactGroupAdapter.TYPE_LEVEL_0;
    }
}
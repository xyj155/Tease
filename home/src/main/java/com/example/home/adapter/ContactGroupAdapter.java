package com.example.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.home.R;
import com.example.home.entity.GroupName;
import com.example.home.entity.GroupUserDetail;
import com.example.library.arouter.ArouterUrl;

import java.util.List;

/**
 * Created by Xuyijie on 2018/10/7.
 */

public class ContactGroupAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;
    private Context context;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ContactGroupAdapter(List<MultiItemEntity> data, Context context) {
        super(data);
        addItemType(TYPE_LEVEL_0, R.layout.ry_contact_list_item);
        addItemType(TYPE_LEVEL_1, R.layout.ry_contact_list_detail_item);
        this.context = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, MultiItemEntity item) {
        switch (item.getItemType()) {
            case 0:
                final GroupName lv0 = (GroupName) item;
                helper.setText(R.id.tv_groupname, lv0.friendGroup)
                        .setText(R.id.tv_count, String.valueOf(lv0.count));
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = helper.getAdapterPosition();
                        if (lv0.isExpanded()) {
                            Glide.with(context).load(R.mipmap.contac_ry_item_down).asBitmap().into((ImageView) helper.getView(R.id.iv_direction));
                            collapse(pos);
                        } else {
                            Glide.with(context).load(R.mipmap.contac_ry_item_left).asBitmap().into((ImageView) helper.getView(R.id.iv_direction));
                            expand(pos);
                        }
                    }
                });
                break;
            case 1:
                final GroupUserDetail lv1 = (GroupUserDetail) item;
                helper.setText(R.id.tv_username, lv1.friendName)
                        .setText(R.id.tv_sign, lv1.friendSculpture)
                        .setOnClickListener(R.id.ll_item, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                ARouter.getInstance().build("/home/main").navigation();
                                Log.i(TAG, "onClick: ");
                                ARouter.getInstance().build(ArouterUrl.CONVERSATION).navigation();
//                                ARouter.getInstance().build("/conversation/home").navigation();
                            }
                        });
                Glide.with(context).load(lv1.avatar).asBitmap().into((ImageView) helper.getView(R.id.iv_avatar));
                break;

        }
    }

}

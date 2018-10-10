package com.example.library.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.library.weight.dialog.SweetAlertDialog;
import com.gyf.barlibrary.ImmersionBar;

import java.util.List;

import cn.jpush.im.android.api.model.Conversation;

public abstract class BaseActivity extends FragmentActivity {
    /***是否显示标题栏*/
    private boolean isshowtitle = true;
    /***是否显示标题栏*/
    private boolean isshowstate = true;
    /***封装toast对象**/
    private static Toast toast;
    /***获取TAG的activity名称**/
    protected final String TAG = this.getClass().getSimpleName();
    public SweetAlertDialog dialog;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(intiLayout());
        initView();
        initData();


    }
    public void showmDialog(String msg) {
        dialog = new SweetAlertDialog(this);
        dialog.setTitleText(msg);
        dialog.show();
    }
    public void hidemDialog(){
        dialog.dismissWithAnimation();
    }
    /**
     * 判断某个Activity 界面是否在前台
     *
     * @param context
     * @param activityName 某个界面名称
     * @return
     */
    public static boolean isForeground(Context context, String activityName) {
        if (context == null || TextUtils.isEmpty(activityName)) {
            return false;
        }
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (activityName.equals(cpn.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public void setNotitle() {
        ImmersionBar.with(this).init();
    }

    /**
     * 设置布局     *     * @return
     */
    public abstract int intiLayout();

    /**
     * 初始化布局
     */
    public abstract void initView();

    /**
     * 设置数据
     */
    public abstract void initData();



    /**
     * 设置是否显示状态栏     * @param ishow
     */
    public void setState(boolean ishow) {
        isshowstate = ishow;
    }

    /**
     * 显示长toast     * @param msg
     */
    public void toastLong(String msg) {
        if (null == toast) {
            toast = new Toast(this);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setText(msg);
            toast.show();
        } else {
            toast.setText(msg);
            toast.show();
        }
    }

    /**
     * 显示短toast     * @param msg
     */
    public void toastShort(String msg) {
        if (null == toast) {
            toast = new Toast(this);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setText(msg);
            toast.show();
        } else {
            toast.setText(msg);
            toast.show();
        }
    }
}

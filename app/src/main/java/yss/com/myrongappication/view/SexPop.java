package yss.com.myrongappication.view;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;


import yss.com.myrongappication.R;
import yss.com.myrongappication.util.BackgroundAlphaUtil;
import yss.com.myrongappication.util.ScreenUtil;

/**
 * 性别选择的pop
 * Created by hliman on 2016/4/14 0014.
 * email:hliman@163.com
 */
public class SexPop {
    private Activity activity;
    private PopupWindow popupWindow;
    private SexChangeListener sexChangeListener;

    public void setSexChangeListener(SexChangeListener sexChangeListener) {
        this.sexChangeListener = sexChangeListener;
    }

    public SexPop(Activity activity) {
        this.activity = activity;
        initPopView();
    }

    private void initPopView() {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        View sexPopView = layoutInflater.inflate(R.layout.pop_sex, null);
        initView(sexPopView);
        initPop(sexPopView);

    }

    /**
     * 初始化view
     *
     * @param sexPopView
     */
    private void initView(View sexPopView) {
        RelativeLayout sexPopWomanRl = (RelativeLayout) sexPopView.findViewById(R.id.sexPopWomanRl);
        RelativeLayout sexPopManRl = (RelativeLayout) sexPopView.findViewById(R.id.sexPopManRl);

        sexPopWomanRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSexState("女");
            }
        });
        sexPopManRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSexState("男");
            }
        });
    }

    /**
     * 初始化Popup
     *
     * @param sexPopView
     */
    private void initPop(View sexPopView) {
        popupWindow = new PopupWindow(sexPopView, ScreenUtil.getScreenWidth(activity) / 4*3,
                WindowManager.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x0000000));
        popupWindow.setAnimationStyle(R.style.sex_pop_window_anim_style);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                BackgroundAlphaUtil.bgAlphaUp(activity);
            }
        });
    }

    /**
     * 改变性别状态
     *
     * @param sex
     */
    private void changeSexState(String sex) {
        if (sexChangeListener != null)
            sexChangeListener.onSexChanged(sex);
        popupWindow.dismiss();
    }

    /**
     * 性别状态改变的listener
     */
    public interface SexChangeListener {
        void onSexChanged(String sex);
    }

    public void show() {
        popupWindow.showAtLocation(activity.findViewById(android.R.id.content), Gravity.CENTER, 0, 0);
        BackgroundAlphaUtil.bgAlphaDown(activity);
    }

    public void dissmiss() {
        popupWindow.dismiss();
    }
}

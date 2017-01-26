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
import yss.com.myrongappication.util.CameraUtil;

/**
 * 选择图片的pop
 * Created by hliman on 2016/4/5 0005.
 * email:hliman@163.com
 */
public class ChoosePicturePop {
    private Activity activity;
    private PopupWindow popupWindow;
    private CameraUtil cameraUtil;

    public CameraUtil getCameraUtil() {
        return cameraUtil;
    }

    public ChoosePicturePop(Activity activity) {
        this.activity = activity;
        cameraUtil = new CameraUtil(activity);
        initView();
    }

    /**
     * 初始化view
     */
    private void initView() {
        View contentView = LayoutInflater.from(activity).inflate(
                R.layout.pop_notice, null);
        RelativeLayout myPicNoticeRL = (RelativeLayout) contentView.findViewById(R.id.myPicNoticeRL);
        RelativeLayout phonePicNoticeRL = (RelativeLayout) contentView.findViewById(R.id.photoPicNoticeRL);
        RelativeLayout cancelNoticeRL = (RelativeLayout) contentView.findViewById(R.id.cancelNoticeRL);
        BottomClick bottomClick = new BottomClick();
        myPicNoticeRL.setOnClickListener(bottomClick);
        phonePicNoticeRL.setOnClickListener(bottomClick);
        cancelNoticeRL.setOnClickListener(bottomClick);
        popupWindow = new PopupWindow(contentView,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT, true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        popupWindow.setBackgroundDrawable(dw);
        // 设置popWindow的显示和消失动画
        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        //popWindow消失监听方法
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                BackgroundAlphaUtil.bgAlphaUp(activity);

            }
        });
    }

    //popup点击监听
    class BottomClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cancelNoticeRL:
                    popupWindow.dismiss();
                    break;
                case R.id.myPicNoticeRL:
                    cameraUtil.doTakeGallery();
                    popupWindow.dismiss();
                    break;
                case R.id.photoPicNoticeRL:
                    cameraUtil.doTakeCamera();
                    popupWindow.dismiss();
                    break;
            }
        }
    }

    /**
     * 在底部显示pop
     */
    public void showPop() {
        BackgroundAlphaUtil.bgAlphaDown(activity);
        popupWindow.showAtLocation(activity.findViewById(android.R.id.content),
                Gravity.BOTTOM, 0, 0);
    }

    /**
     * 让pop消失
     */
    public void dismissPop() {
        popupWindow.dismiss();
    }
}

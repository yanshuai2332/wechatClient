package yss.com.myrongappication.util;

import android.app.Activity;
import android.view.WindowManager;

/**
 * 控制屏幕变暗和恢复变亮
 */
public class BackgroundAlphaUtil {
    /**
     * 屏幕变暗
     */
    public static void bgAlphaDown(Activity activity) {
        backAlphaUtil(activity, 0.5f);
    }

    public static void bgAlphaDown(Activity activity, float alpha) {
        backAlphaUtil(activity, alpha);
    }

    /**
     * 屏幕变亮
     */
    public static void bgAlphaUp(Activity activity) {
        backAlphaUtil(activity, 1.0f);
    }

    /**
     * 可自定义调整亮度
     *
     * @param activity
     * @param alpha
     */
    public static void bgAlphaUp(Activity activity, float alpha) {
        backAlphaUtil(activity, alpha);
    }

    /**
     * 调整亮度的工具
     *
     * @param activity
     * @param alpha
     */
    private static void backAlphaUtil(Activity activity, float alpha) {
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.alpha = alpha;
        activity.getWindow().setAttributes(params);
    }
}

package yss.com.myrongappication.util;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import java.io.File;

/**
 * 打开相机和图库工具类
 * Created by hliman on 2016/4/5 0005.
 * email:hliman@163.com
 */
public class CameraUtil {
    private String mFilename;
    private Activity activity;
    public static final String IMG_DIRPATH = Environment.getExternalStorageDirectory() + "/SWManager/CameraCache";
    private static final File PHOTO_DIR = new File(IMG_DIRPATH);
    public static final int CAMERA_WITH_DATA = 3023;
    public static final int GALLERY_WITH_DATA = 3024;
    private boolean isDrop = false;

    public CameraUtil(Activity activity) {
        this.activity = activity;
    }

    public CameraUtil(Activity activity, boolean isDrop) {
        this.activity = activity;
        this.isDrop = isDrop;
    }

    public CameraUtil(Fragment fragment, boolean isDrop) {
        this.activity = fragment.getActivity();
        this.isDrop = isDrop;
    }

    public CameraUtil(Fragment fragment) {
        activity = fragment.getActivity();
    }

    /**
     * 启动相机，从相机拍摄照片
     */
    public void doTakeCamera() {
        File mCurrentPhotoFile;
        try {
            checkPhotoDir();
            mFilename = System.currentTimeMillis() + ".jpg";
            mCurrentPhotoFile = new File(PHOTO_DIR, mFilename);
            Intent intent = getTakePickIntent(mCurrentPhotoFile);
            activity.startActivityForResult(intent, CAMERA_WITH_DATA);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(activity, "未找到错误", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkPhotoDir() {
        if (!PHOTO_DIR.exists()) {
            PHOTO_DIR.mkdirs();
        }
    }

    public Intent getTakePickIntent(File f) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        return intent;
    }

    /**
     * 判断用户内存卡是否可用
     */
    public boolean checkExternalStorage() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 从相机取照片
     */
    public Uri getCameraUri() {
        File f = new File(PHOTO_DIR, mFilename);
        Uri imgUri = Uri.fromFile(f);
        return imgUri;
    }

    /**
     * 启动相册
     */
    public void doTakeGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        intent.setType("image/*");
        if (activity != null) {
            activity.startActivityForResult(intent, GALLERY_WITH_DATA);
        }
    }

    /**
     * 从相册取照片变为URI
     */
    public Uri getGalleryUri(Intent data) {
        Uri uri = data.getData();
        return uri;
    }

}


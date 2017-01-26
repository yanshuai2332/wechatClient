package yss.com.myrongappication.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yss.com.myrongappication.R;
import yss.com.myrongappication.application.ExitApplication;
import yss.com.myrongappication.http.Api;
import yss.com.myrongappication.preference.UserIdDao;
import yss.com.myrongappication.util.BitmapUtil;
import yss.com.myrongappication.util.CameraUtil;
import yss.com.myrongappication.util.KeyBoardUtil;
import yss.com.myrongappication.util.StringUtil;
import yss.com.myrongappication.view.ChoosePicturePop;
import yss.com.myrongappication.view.SexPop;
import yss.com.myrongappication.view.WithEditTextDialog;

public class ImprovePersonalInfoActivity extends AppCompatActivity {


    @BindView(R.id.improveUserHeadImageCiv)
    CircleImageView improveUserHeadImageCiv;
    @BindView(R.id.improveUserHeadImageRl)
    RelativeLayout improveUserHeadImageRl;
    @BindView(R.id.improveUserNameEt)
    TextView improveUserNameEt;
    @BindView(R.id.improveUserNameRl)
    RelativeLayout improveUserNameRl;
    @BindView(R.id.improveUserSexEt)
    TextView improveUserSexEt;
    @BindView(R.id.improveUserSexRl)
    RelativeLayout improveUserSexRl;
    @BindView(R.id.confirmImproveBT)
    TextView confirmImproveBT;
    @BindView(R.id.backIV)
    ImageView backIV;
    @BindView(R.id.txt1)
    TextView txt1;
    @BindView(R.id.rl)
    RelativeLayout rl;
    private ChoosePicturePop choosePicturePop;
    private SexPop sexPop;
    private Bitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_improve_personal_info);
        ButterKnife.bind(this);
        ExitApplication.getInstance().addActivity(ImprovePersonalInfoActivity.this);
        choosePicturePop = new ChoosePicturePop(this);

        sexPop = new SexPop(this);
        initText();
        initListener();
    }

    /**
     * 提交修改数据
     */
    private void commitData() {
//        String portrait = PortraitDao.getPortrait(ImprovePersonalInfoActivity.this);
        String userPic = "";
        String sex = "1";
        if (StringUtil.isEmpty(improveUserNameEt.getText().toString().trim())) {
            Toast.makeText(this, "昵称不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (StringUtil.isEmpty(improveUserNameEt.getText().toString().trim())) {
            Toast.makeText(this, "性别不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (improveUserSexEt.getText().toString().equals("女")) {
            sex = "0";
        }
        if (improveUserHeadImageCiv.getTag() != null)
            if (improveUserHeadImageCiv.getTag().equals("userPic")) {
                image = ((BitmapDrawable) improveUserHeadImageCiv.getDrawable()).getBitmap();
                userPic = BitmapUtil.Bitmap2StrByBase64(image);
            }
        File file = BitmapUtil.bitmapToFile("/mnt/sdcard/portrait.jpg", image);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("portrait", file.getName(), requestBody);
        Api.getApi(this).updateUserInfo(body, UserIdDao.getUserId(this), improveUserNameEt.getText().toString(), sex).enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                Map map = response.body();
                String result = (String) map.get("result");
                Toast.makeText(ImprovePersonalInfoActivity.this, result, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {

            }
        });

    }

    private void initListener() {
        sexPop.setSexChangeListener(new SexPop.SexChangeListener() {
            @Override
            public void onSexChanged(String sex) {
                improveUserSexEt.setText(sex);
                if (sex.equals("男"))
                    improveUserSexEt.setTag("0");
                else if (sex.equals("女"))
                    improveUserSexEt.setTag("1");
            }
        });
    }

    private void initText() {
        //默认值
        improveUserSexEt.setText("男");
        improveUserSexEt.setTag("0");
//        if (StringUtil.isNotEmpty(NameDao.getName(this)))
//            improveUserNameEt.setText(NameDao.getName(this));
//        if (StringUtil.isNotEmpty(PortraitDao.getPortrait(this)))
//            Picasso.with(this).load(PortraitDao.getPortrait(this)).placeholder(R.drawable.default_person_portrait).into(improveUserHeadImageCiv);
//        if (StringUtil.isNotEmpty(GenderDao.getGender(this))) {
//            if (GenderDao.getGender(this).equals("1")) {
//                improveUserSexEt.setText("女");
//                improveUserSexEt.setTag("1");
//            }
//        }

    }

    @OnClick(R.id.improveUserSexRl)
    void changeSex(View view) {
        KeyBoardUtil.dismissKeyBoard(this);
        sexPop.show();
    }


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ImprovePersonalInfoActivity.class);
        context.startActivity(intent);
    }

    /**
     * 将软键盘消失，并弹出下面的pop
     */
    @OnClick(R.id.improveUserHeadImageRl)
    void changeImage() {
        KeyBoardUtil.dismissKeyBoard(this);
        choosePicturePop.showPop();
    }

    @OnClick(R.id.confirmImproveBT)
    void changeData() {

        commitData();

    }

//    /**
//     * 刷新数据
//     */
//    private void refreshUserData() {
//        Api.getApi(this).userGetting(AccessTokenDao.getAccessToken(ImprovePersonalInfoActivity.this)).enqueue(new Callback<UserInfoResp>() {
//            @Override
//            public void onResponse(Call<UserInfoResp> call, Response<UserInfoResp> response) {
//                UserInfoResp userInfoResp = response.body();
//                handleDao(userInfoResp);
//                ImprovePersonalInfoActivity.this.finish();
//            }
//
//            @Override
//            public void onFailure(Call<UserInfoResp> call, Throwable t) {
//                ToastUtil.show(ImprovePersonalInfoActivity.this, "网络连接失败");
//            }
//        });
//    }

//    /**
//     * 将获取的数据放入本地的xml文件中
//     *
//     * @param
//     */
//    private void handleDao(UserInfoResp userInfoResp) {
//        DescriptionDao.setDescription(ImprovePersonalInfoActivity.this, userInfoResp.getUserInfo().getDescription());
//        GenderDao.setGender(ImprovePersonalInfoActivity.this, userInfoResp.getUserInfo().getGender());
//        NameDao.setName(ImprovePersonalInfoActivity.this, userInfoResp.getUserInfo().getName());
//        PhoneDao.setPhone(ImprovePersonalInfoActivity.this, userInfoResp.getUserInfo().getPhone());
//        PortraitDao.setPortrait(ImprovePersonalInfoActivity.this, userInfoResp.getUserInfo().getPortrait());
//        TeamNameDao.setTeamName(ImprovePersonalInfoActivity.this, userInfoResp.getUserInfo().getTeamName());
//        UserTypeDao.setUserType(ImprovePersonalInfoActivity.this, userInfoResp.getUserInfo().getType());
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Bitmap bitmap = null;
            switch (requestCode) {
                case CameraUtil.CAMERA_WITH_DATA: //拍摄图片并选择
                    bitmap = BitmapUtil.reduceBitmapQuality(ImprovePersonalInfoActivity.this, choosePicturePop.getCameraUtil().getCameraUri());
                    startUcrop(bitmap);
                    bitmap = null;
                    break;
                case CameraUtil.GALLERY_WITH_DATA:
                    Log.i("Msg","is null"+(choosePicturePop.getCameraUtil().getGalleryUri(data)==null));
                    bitmap = BitmapUtil.reduceBitmapQuality(ImprovePersonalInfoActivity.this, choosePicturePop.getCameraUtil().getGalleryUri(data));
                    startUcrop(bitmap);
                    bitmap = null;
                    break;
                case UCrop.REQUEST_CROP:
                    if (data != null)
                        bitmap = BitmapUtil.uriToBitmap(this, UCrop.getOutput(data));
                    break;
                case UCrop.RESULT_ERROR:
                    Toast.makeText(this, "获取照片错误", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
            if (bitmap != null) {
                improveUserHeadImageCiv.setImageBitmap(bitmap);
                improveUserHeadImageCiv.setTag("userPic");//设置tag表示有新增图片
            }

        }
    }

    /**
     * 启动裁剪
     *
     * @param bitmap
     */
    private void startUcrop(Bitmap bitmap) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Uri bitmapUri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null, null));
        UCrop.Options options = new UCrop.Options();
        options.setHideBottomControls(true);
        UCrop.of(bitmapUri, destination)
                .withAspectRatio(1, 1)
                .withMaxResultSize(300, 300)
                .withOptions(options)
                .start(this);
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick(R.id.improveUserNameRl)
    void changeName(View view) {
        final WithEditTextDialog withEditTextDialog = new WithEditTextDialog(ImprovePersonalInfoActivity.this, "用户昵称", "确定", "取消");
        withEditTextDialog.getDialogContentET().setText(improveUserNameEt.getText().toString());
        withEditTextDialog.getDialogContentET().setSelection(improveUserNameEt.getText().toString().length());
        withEditTextDialog.setClicklistener(new WithEditTextDialog.ClickListenerInterface() {
            @Override
            public void doConfirm() {
                improveUserNameEt.setText(withEditTextDialog.getDialogContentET().getText().toString().trim());
                withEditTextDialog.dismiss();

            }

            @Override
            public void doCancel() {
                withEditTextDialog.dismiss();
            }
        });
        withEditTextDialog.show();
    }

    @OnClick(R.id.backIV)
    public void onClick() {
        this.finish();
    }
}

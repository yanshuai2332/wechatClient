package yss.com.myrongappication.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yss.com.myrongappication.R;
import yss.com.myrongappication.activity.ImprovePersonalInfoActivity;
import yss.com.myrongappication.activity.PersonalActivity;
import yss.com.myrongappication.http.Api;
import yss.com.myrongappication.preference.NickNameDao;
import yss.com.myrongappication.preference.PhoneDao;
import yss.com.myrongappication.preference.PortraitDao;
import yss.com.myrongappication.preference.SexDao;
import yss.com.myrongappication.preference.UserIdDao;
import yss.com.myrongappication.resp.UserInfoVo;
import yss.com.myrongappication.util.StringUtil;
import yss.com.myrongappication.view.TwoOptionsDialog;

@SuppressLint("ValidFragment")
public class PersonalFragment extends Fragment {
    @BindView(R.id.userPhotoPersonalCIV)
    CircleImageView userPhotoPersonalCIV;
    @BindView(R.id.status)
    TextView status;
    @BindView(R.id.userNamePersonalTV)
    TextView userNamePersonalTV;
    @BindView(R.id.companyPersonalTV)
    TextView companyPersonalTV;
    @BindView(R.id.personalTopLL)
    LinearLayout personalTopLL;
    @BindView(R.id.infoImprovePersonalLL)
    LinearLayout infoImprovePersonalLL;
    @BindView(R.id.passwordResetPersonalLL)
    LinearLayout passwordResetPersonalLL;
    @BindView(R.id.callPersonalLL)
    LinearLayout callPersonalLL;
    @BindView(R.id.morePersonalLL)
    LinearLayout morePersonalLL;
    @BindView(R.id.pagelistPersonalLL)
    LinearLayout pagelistPersonalLL;

    String name = null;
    String company = null;
    String url = null;
    public static PersonalFragment getInstance() {
        PersonalFragment sf = new PersonalFragment();
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_personal, null);
        ButterKnife.bind(this, v);
        init();
        updateUserInfo();
        return v;
    }

    private void init() {
        name= NickNameDao.getNickName(getContext());
        url= PortraitDao.getPortrait(getContext());
        if (StringUtil.isNotEmpty(name))
            userNamePersonalTV.setText(name);
        if (StringUtil.isNotEmpty(url)) {
            Picasso.with(getContext()).load(url).placeholder(R.drawable.default_person_portrait).into(userPhotoPersonalCIV);
        }
    }

    private void updateUserInfo() {
        Api.getApi(getContext()).getUserInfo(UserIdDao.getUserId(getContext())).enqueue(new Callback<UserInfoVo>() {
            @Override
            public void onResponse(Call<UserInfoVo> call, Response<UserInfoVo> response) {
                UserInfoVo userInfoVo=response.body();
                PhoneDao.setPhone(getContext(),userInfoVo.getPhone());
                PortraitDao.setPortrait(getContext(),userInfoVo.getPortrait());
                NickNameDao.setNickName(getContext(),userInfoVo.getNickName());
                SexDao.setSex(getContext(),userInfoVo.getSex());
                init();
            }

            @Override
            public void onFailure(Call<UserInfoVo> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.infoImprovePersonalLL)
    public void improveInfoonclick() {
        ImprovePersonalInfoActivity.startActivity(getContext());
    }
    @OnClick(R.id.callPersonalLL)
    public void callonclick() {
        call(getContext());
    }
    public void call(Context context) {
        final TwoOptionsDialog twoOptionsDialog = new TwoOptionsDialog(context, "拨打生活有我客服热线\n4009956886", "拨打", "取消");
        twoOptionsDialog.show();
        twoOptionsDialog.setClicklistener(new TwoOptionsDialog.ClickListenerInterface() {
            @Override
            public void doConfirm() {
                // TODO Auto-generated method stub

                try {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "4009956886"));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                twoOptionsDialog.dismiss();
            }

            @Override
            public void doCancel() {
                // TODO Auto-generated method stub
                twoOptionsDialog.dismiss();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUserInfo();
    }
}
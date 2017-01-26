package yss.com.myrongappication.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import yss.com.myrongappication.application.ExitApplication;
import yss.com.myrongappication.fast.activity.LoginActivity;
import yss.com.myrongappication.http.Api;
import yss.com.myrongappication.preference.AccessTokenDao;
import yss.com.myrongappication.preference.PhoneDao;
import yss.com.myrongappication.util.StringUtil;
import yss.com.myrongappication.view.TwoOptionsDialog;

public class PersonalActivity extends AppCompatActivity {
    String name = null;
    String company = null;
    String url = null;
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
    private String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        ButterKnife.bind(this);
        ExitApplication.getInstance().addActivity(PersonalActivity.this);
    }





    public static void startActivity(Context context) {
        Intent intent = new Intent(context, PersonalActivity.class);
        context.startActivity(intent);
    }



    @OnClick(R.id.callPersonalLL)
    public void callonclick() {
        call(PersonalActivity.this);
    }

    @OnClick(R.id.infoImprovePersonalLL)
    public void improveInfoonclick() {
        ImprovePersonalInfoActivity.startActivity(PersonalActivity.this);
    }

    @OnClick(R.id.morePersonalLL)
    public void moreonclick() {
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {

        super.onResume();
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

}

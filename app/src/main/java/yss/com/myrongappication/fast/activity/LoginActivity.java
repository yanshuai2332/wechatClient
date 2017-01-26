package yss.com.myrongappication.fast.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yss.com.myrongappication.R;
import yss.com.myrongappication.activity.MainActivity;
import yss.com.myrongappication.activity.RegistActivity;
import yss.com.myrongappication.fast.App;
import yss.com.myrongappication.http.Api;
import yss.com.myrongappication.preference.AccessTokenDao;
import yss.com.myrongappication.preference.PhoneDao;
import yss.com.myrongappication.preference.PortraitDao;
import yss.com.myrongappication.preference.UserIdDao;
import yss.com.myrongappication.resp.LoginInfo;
import yss.com.myrongappication.util.StringUtil;


/**
 * Created by Bob on 15/8/19.
 * 登录页面
 * 1，token 从自己 server 获取的演示
 * 2，拿到 token 后，做 connect 操作
 */
public class LoginActivity extends Activity {


    @BindView(R.id.bt1)
    Button bt1;
    @BindView(R.id.registTV)
    TextView registTV;
    @BindView(R.id.userNameLoginET)
    EditText userNameLoginET;
    @BindView(R.id.passwordLoginET)
    EditText passwordLoginET;
    /**
     * token 的主要作用是身份授权和安全，因此不能通过客户端直接访问融云服务器获取 token，
     * 您必须通过 Server API 从融云服务器 获取 token 返回给您的 App，并在之后连接时使用
     */
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.login);
        ButterKnife.bind(this);

        if (StringUtil.isNotEmpty(UserIdDao.getUserId(this))) {
            connect(AccessTokenDao.getAccessToken(this));
        }
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    /**
     * 用户登录，用户登录成功，获得 cookie，将cookie 保存
     */
    private void login() {
        Api.getApi(this).login(userNameLoginET.getText().toString(), passwordLoginET.getText().toString()).enqueue(new Callback<LoginInfo>() {
            @Override
            public void onResponse(Call<LoginInfo> call, Response<LoginInfo> response) {
                LoginInfo loginInfo = response.body();
                PhoneDao.setPhone(getApplicationContext(), loginInfo.getLoginInfo().getPhone());
                UserIdDao.setUserId(getApplicationContext(), loginInfo.getLoginInfo().getId());
                PortraitDao.setPortrait(getApplicationContext(), loginInfo.getLoginInfo().getPortrait());
                AccessTokenDao.setAccessToken(getApplicationContext(),loginInfo.getToken());
                connect(loginInfo.getToken());
            }

            @Override
            public void onFailure(Call<LoginInfo> call, Throwable t) {

            }
        });
    }

    /**
     * 建立与融云服务器的连接
     *
     * @param token
     */
    private void connect(String token) {
        if (getApplicationInfo().packageName.equals(App.getCurProcessName(getApplicationContext()))) {

            /**
             * IMKit SDK调用第二步,建立与服务器的连接
             */
            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
                 */
                @Override
                public void onTokenIncorrect() {

                    Log.d("LoginActivity", "--onTokenIncorrect");
                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token
                 */
                @Override
                public void onSuccess(String userid) {

                    Log.d("LoginActivity", "--onSuccess" + userid);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 *                  http://www.rongcloud.cn/docs/android.html#常见错误码
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {

                    Log.d("LoginActivity", "--onError" + errorCode);
                }
            });
        }
    }

    @OnClick(R.id.registTV)
    public void regist() {
        RegistActivity.startActivity(this);
    }

    public static void startActivity(Context context) {

        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);

    }
}

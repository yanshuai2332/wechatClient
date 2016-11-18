package yss.com.myrongappication.fast.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yss.com.myrongappication.R;
import yss.com.myrongappication.activity.MainActivity;
import yss.com.myrongappication.fast.App;
import yss.com.myrongappication.http.Api;
import yss.com.myrongappication.preference.OpenIdDao;
import yss.com.myrongappication.resp.LoginInfo;

import static android.os.Build.VERSION_CODES.M;

/**
 * Created by Bob on 15/8/19.
 * 登录页面
 * 1，token 从自己 server 获取的演示
 * 2，拿到 token 后，做 connect 操作
 */
public class LoginActivity extends Activity {

    @BindView(R.id.et1)
    EditText et1;
    @BindView(R.id.et2)
    EditText et2;
    @BindView(R.id.bt1)
    Button bt1;
    /**
     * token 的主要作用是身份授权和安全，因此不能通过客户端直接访问融云服务器获取 token，
     * 您必须通过 Server API 从融云服务器 获取 token 返回给您的 App，并在之后连接时使用
     */
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);
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
        Api.getApi(this).login(et1.getText().toString(), et2.getText().toString()).enqueue(new Callback<LoginInfo>() {
            @Override
            public void onResponse(Call<LoginInfo> call, Response<LoginInfo> response) {
                LoginInfo loginInfo= response.body();
                OpenIdDao.setOpenId(getApplicationContext(),loginInfo.getLoginInfo().getOpenId());
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
}

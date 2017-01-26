package yss.com.myrongappication.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yss.com.myrongappication.R;
import yss.com.myrongappication.application.ExitApplication;
import yss.com.myrongappication.fast.activity.LoginActivity;
import yss.com.myrongappication.http.Api;

public class RegistActivity extends AppCompatActivity {


    @BindView(R.id.loginTV)
    TextView loginTV;
    @BindView(R.id.userNameRegistET)
    EditText userNameRegistET;
    @BindView(R.id.phoneRegistET)
    EditText phoneRegistET;
    @BindView(R.id.passwordRegistET)
    EditText passwordRegistET;
    @BindView(R.id.registBT)
    Button registBT;

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
        setContentView(R.layout.regisit);
        ButterKnife.bind(this);
        ExitApplication.getInstance().addActivity(RegistActivity.this);

    }


    public static void startActivity(Context context) {

        Intent intent = new Intent(context, RegistActivity.class);
        context.startActivity(intent);

    }


    @OnClick({R.id.registBT, R.id.loginTV})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.registBT:
                Api.getApi(this).register(userNameRegistET.getText().toString(),phoneRegistET.getText().toString(),passwordRegistET.getText().toString()).enqueue(new Callback<Map>() {
                    @Override
                    public void onResponse(Call<Map> call, Response<Map> response) {
                        Map map=response.body();
                        String result=map.get("result").toString();
                        if(result.equals("success"))
                        {
                            Toast.makeText(RegistActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                            ExitApplication.getInstance().exit();
                        }
                    }

                    @Override
                    public void onFailure(Call<Map> call, Throwable t) {

                    }
                });
                break;
            case R.id.loginTV:
                LoginActivity.startActivity(this);
                ExitApplication.getInstance().exit();
                break;
        }
    }
}

package yss.com.myrongappication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yss.com.myrongappication.R;
import yss.com.myrongappication.adapter.UserInfoListAdapter;
import yss.com.myrongappication.application.ExitApplication;
import yss.com.myrongappication.http.Api;
import yss.com.myrongappication.preference.UserIdDao;
import yss.com.myrongappication.resp.UserInfoVo;
import yss.com.myrongappication.view.TwoOptionsDialog;

public class AddFriendsActivity extends AppCompatActivity {

    @BindView(R.id.img3)
    ImageView img3;
    @BindView(R.id.searchFriendsIV)
    ImageView searchFriendsIV;
    @BindView(R.id.searchFriendTV)
    TextView searchFriendTV;
    @BindView(R.id.searchFriendRL)
    RelativeLayout searchFriendRL;
    @BindView(R.id.rl)
    RelativeLayout rl;
    @BindView(R.id.activity_add_friends)
    RelativeLayout activityAddFriends;
    @BindView(R.id.searchContentET)
    EditText searchContentET;
    @BindView(R.id.newFriendsLV)
    ListView newFriendsLV;

    private List<UserInfoVo> list;
    private UserInfoListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);
        ButterKnife.bind(this);
        ExitApplication.getInstance().addActivity(AddFriendsActivity.this);
    }

    @OnClick(R.id.searchFriendTV)
    public void onClick() {
        Api.getApi(this).searchFriends(UserIdDao.getUserId(this), searchContentET.getText().toString()).enqueue(new Callback<List<UserInfoVo>>() {
            @Override
            public void onResponse(Call<List<UserInfoVo>> call, Response<List<UserInfoVo>> response) {
                list = response.body();
                if (list.size() <= 0)
                    Toast.makeText(AddFriendsActivity.this, "找不到此用户", Toast.LENGTH_SHORT).show();
                adapter = new UserInfoListAdapter(list, getApplicationContext());
                newFriendsLV.setAdapter(adapter);
                newFriendsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        final TwoOptionsDialog twoOptionsDialog = new TwoOptionsDialog(AddFriendsActivity.this, "发送好友申请", "申请", "取消");
                        twoOptionsDialog.show();
                        twoOptionsDialog.setClicklistener(new TwoOptionsDialog.ClickListenerInterface() {
                            @Override
                            public void doConfirm() {
                                // TODO Auto-generated method stub
                                Api.getApi(getApplicationContext()).addFriend(UserIdDao.getUserId(getApplicationContext()), adapter.getItem(position).getId()).enqueue(new Callback<Map>() {
                                    @Override
                                    public void onResponse(Call<Map> call, Response<Map> response) {
                                        Map map = response.body();
                                        String result = map.get("result").toString();
                                        if (result.equals("success")) {
                                            Toast.makeText(AddFriendsActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                                            ExitApplication.getInstance().exit();
                                            list.remove(position);
                                            adapter.notifyDataSetChanged();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Map> call, Throwable t) {

                                    }
                                });
                                twoOptionsDialog.dismiss();
                            }

                            @Override
                            public void doCancel() {
                                // TODO Auto-generated method stub
                                twoOptionsDialog.dismiss();
                            }
                        });
                    }
                });
            }

            @Override
            public void onFailure(Call<List<UserInfoVo>> call, Throwable t) {

            }
        });
    }

    public void apply(final Context context, final String targetUserId, final int position) {

    }

    public static void startActivity(Context context) {

        Intent intent = new Intent(context, AddFriendsActivity.class);
        context.startActivity(intent);

    }

    @OnClick(R.id.img3)
    public void onBackClick() {
        finish();
    }
}

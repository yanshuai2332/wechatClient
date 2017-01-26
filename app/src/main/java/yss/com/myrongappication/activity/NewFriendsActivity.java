package yss.com.myrongappication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yss.com.myrongappication.R;
import yss.com.myrongappication.adapter.ApplyFriendsListAdapter;
import yss.com.myrongappication.http.Api;
import yss.com.myrongappication.preference.UserIdDao;
import yss.com.myrongappication.resp.FriendsList;

public class NewFriendsActivity extends AppCompatActivity {

    @BindView(R.id.img3)
    ImageView img3;
    @BindView(R.id.txt1)
    TextView txt1;
    @BindView(R.id.addFriendTV)
    TextView addFriendTV;
    @BindView(R.id.rl)
    RelativeLayout rl;
    @BindView(R.id.searchFriendsIV)
    ImageView searchFriendsIV;
    @BindView(R.id.searchFriendRL)
    RelativeLayout searchFriendRL;
    @BindView(R.id.newFriendsTV)
    TextView newFriendsTV;
    @BindView(R.id.newFriendsLV)
    ListView newFriendsLV;
    @BindView(R.id.activity_new_friends)
    RelativeLayout activityNewFriends;

    private ApplyFriendsListAdapter applyFriendsListAdapter;
    List<FriendsList> friendsLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friends);
        ButterKnife.bind(this);
        Api.getApi(this).applyFriendsList(UserIdDao.getUserId(this)).enqueue(new Callback<List<FriendsList>>() {
            @Override
            public void onResponse(Call<List<FriendsList>> call, Response<List<FriendsList>> response) {
                friendsLists = response.body();
                applyFriendsListAdapter = new ApplyFriendsListAdapter(friendsLists, getApplicationContext());
                newFriendsLV.setAdapter(applyFriendsListAdapter);
            }

            @Override
            public void onFailure(Call<List<FriendsList>> call, Throwable t) {

            }
        });

    }

    @OnClick(R.id.searchFriendRL)
    public void onClick() {
        AddFriendsActivity.startActivity(this);
    }

    @OnClick(R.id.addFriendTV)
    public void addFriend() {
        AddFriendsActivity.startActivity(this);
    }

    public static void startActivity(Context context) {

        Intent intent = new Intent(context, NewFriendsActivity.class);
        context.startActivity(intent);

    }

    @OnClick(R.id.img3)
    public void onBackClick() {
        finish();
    }
}

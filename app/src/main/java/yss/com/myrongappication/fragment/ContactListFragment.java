package yss.com.myrongappication.fragment;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yss.com.myrongappication.R;
import yss.com.myrongappication.activity.NewFriendsActivity;
import yss.com.myrongappication.contact.ChineseToEnglish;
import yss.com.myrongappication.contact.CompareSort;
import yss.com.myrongappication.contact.SideBarView;
import yss.com.myrongappication.contact.User;
import yss.com.myrongappication.contact.UserAdapter;
import yss.com.myrongappication.http.Api;
import yss.com.myrongappication.preference.UserIdDao;
import yss.com.myrongappication.resp.FriendsList;
import yss.com.myrongappication.util.DemoContext;

@SuppressLint("ValidFragment")
public class ContactListFragment extends Fragment implements SideBarView.LetterSelectListener {
    List<FriendsList> friendsLists;
    ListView mListview;
    UserAdapter mAdapter;
    TextView mTip;

    public static ContactListFragment getInstance() {
        ContactListFragment sf = new ContactListFragment();
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_contact_list, null);

        mListview = (ListView) v.findViewById(R.id.listview);
        final SideBarView sideBarView = (SideBarView) v.findViewById(R.id.sidebarview);
        // String[] contactsArray = getResources().getStringArray(R.array.data);
        final String[] headArray = getResources().getStringArray(R.array.head);
        mTip = (TextView) v.findViewById(R.id.tip);
        //初始化默认界面（基础控件如群聊，新的朋友等）
        initAdapter(headArray);
        //从服务器查找好友列表
        loadData(headArray);
        //设置回调
        sideBarView.setOnLetterSelectListen(this);
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==3)
                    NewFriendsActivity.startActivity(getContext());
                if (RongIM.getInstance() != null && position > 3)
                    RongIM.getInstance().startPrivateChat(getActivity(), mAdapter.getItem(position).getFriendId(), "title");
            }
        });

        return v;
    }

    private void loadData(final String[] headArray) {
        Api.getApi(getContext()).friendsList(UserIdDao.getUserId(getContext())).enqueue(new Callback<List<FriendsList>>() {
            @Override
            public void onResponse(Call<List<FriendsList>> call, Response<List<FriendsList>> response) {
                friendsLists = response.body();
                //模拟添加数据到Arraylist
                int length = friendsLists.size();
                ArrayList<User> users = new ArrayList<>();
                List<UserInfo> userInfoList = new ArrayList<UserInfo>();
                for (int i = 0; i < length; i++) {
                    //通讯录用户信息
                    User user = new User();
                    user.setName(friendsLists.get(i).getNickName());
                    user.setFriendId(friendsLists.get(i).getUserId());
                    user.setState(friendsLists.get(i).getState());
                    user.setPortrait(friendsLists.get(i).getPortrait());
                    //会话列表用户信息存入内存
                    UserInfo userInfo = new UserInfo(friendsLists.get(i).getUserId(), friendsLists.get(i).getNickName(), Uri.parse(friendsLists.get(i).getPortrait()));
                    userInfoList.add(userInfo);

                    RongIM.getInstance().refreshUserInfoCache(userInfo);
                    String firstSpell = ChineseToEnglish.getFirstSpell(friendsLists.get(i).getNickName());
                    String substring = firstSpell.substring(0, 1).toUpperCase();
                    if (substring.matches("[A-Z]")) {
                        user.setLetter(substring);
                    } else {
                        user.setLetter("#");
                    }
                    users.add(user);
                }
                //将会话联系人信息存入内存为getUserInfo提供数据
                DemoContext.getInstance().setmUserInfos((ArrayList<UserInfo>) userInfoList);
                for (int i = 0; i < headArray.length; i++) {
                    User user = new User();
                    user.setName(headArray[i]);
                    user.setLetter("@");
                    users.add(user);
                }
                //排序
                Collections.sort(users, new CompareSort());

                //设置数据
                mAdapter = new UserAdapter(getContext());
                mAdapter.setData(users);
                mListview.setAdapter(mAdapter);


            }

            @Override
            public void onFailure(Call<List<FriendsList>> call, Throwable t) {

            }

        });
    }

    private void initAdapter(String[] headArray) {
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < headArray.length; i++) {
            User user = new User();
            user.setName(headArray[i]);
            user.setLetter("@");
            users.add(user);
        }
        //排序
        Collections.sort(users, new CompareSort());
        //设置数据
        mAdapter = new UserAdapter(getContext());
        mAdapter.setData(users);
        mListview.setAdapter(mAdapter);
    }

    @Override
    public void onLetterSelected(String letter) {
        setListviewPosition(letter);
        mTip.setText(letter);
        mTip.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLetterChanged(String letter) {
        setListviewPosition(letter);
        mTip.setText(letter);
    }

    @Override
    public void onLetterReleased(String letter) {
        mTip.setVisibility(View.GONE);
    }

    private void setListviewPosition(String letter) {
        int firstLetterPosition = mAdapter.getFirstLetterPosition(letter);
        if (firstLetterPosition != -1) {
            mListview.setSelection(firstLetterPosition);
        }
    }
}
package yss.com.myrongappication.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yss.com.myrongappication.R;
import yss.com.myrongappication.adapter.MessageListAdapter;
import yss.com.myrongappication.http.Api;
import yss.com.myrongappication.preference.OpenIdDao;
import yss.com.myrongappication.resp.FriendsList;

@SuppressLint("ValidFragment")
public class SimpleCardFragment extends Fragment {
    private String mTitle;
    private ListView listView;
    private MessageListAdapter messageListAdapter;
    List<FriendsList> friendsLists;
    public static SimpleCardFragment getInstance(String title) {
        SimpleCardFragment sf = new SimpleCardFragment();
        sf.mTitle = title;
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_simple_card, null);
        TextView card_title_tv = (TextView) v.findViewById(R.id.card_title_tv);
        card_title_tv.setText(mTitle);
        listView= (ListView) v.findViewById(R.id.messageRongLV);
        RongIM.getInstance().getLatestMessages(Conversation.ConversationType.PRIVATE, "201605", 1, new RongIMClient.ResultCallback<List<Message>>() {
            @Override
            public void onSuccess(List<Message> messages) {
                Log.d("messages","="+messages);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });

        Api.getApi(getContext()).friendsList(OpenIdDao.getOpenId(getContext())).enqueue(new Callback<List<FriendsList>>() {
            @Override
            public void onResponse(Call<List<FriendsList>> call, Response<List<FriendsList>> response) {
                friendsLists=response.body();
                messageListAdapter=new MessageListAdapter(friendsLists,getContext());
                listView.setAdapter(messageListAdapter);
            }

            @Override
            public void onFailure(Call<List<FriendsList>> call, Throwable t) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (RongIM.getInstance() != null)
                    RongIM.getInstance().startPrivateChat(getActivity(),messageListAdapter.getItem(position).getFriendId() , "title");
            }
        });
        return v;
    }
}
package yss.com.myrongappication.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import yss.com.myrongappication.R;
import yss.com.myrongappication.resp.FriendsList;

/**
 * Created by Administrator on 2016-11-16.
 */

public class MessageListAdapter extends BaseAdapter {
    private List<FriendsList> datalist;
    private Context context;

    public MessageListAdapter(List<FriendsList> datalist, Context context) {
        super();
        this.datalist = datalist;
        this.context = context;
    }

    @Override
    public int getCount() {
        return datalist.size();
    }

    @Override
    public FriendsList getItem(int position) {
        return datalist == null || position < 0 || position > datalist.size() ? null : datalist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context,
                    R.layout.message_item, null);
            new ViewHolder(convertView);
        }
        final ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.nameItemRongTV.setText(getItem(position).getFriendId());
        //查询最后一条消息
//        RongIM.getInstance().getLatestMessages(Conversation.ConversationType.PRIVATE, getItem(position).getFriendId(), 1, new RongIMClient.ResultCallback<List<Message>>() {
//            @Override
//            public void onSuccess(List<Message> messages) {
//                Log.d("messages","="+messages);
//                holder.contentItemRongTV.setText(messages.get(0).toString());
//            }
//
//            @Override
//            public void onError(RongIMClient.ErrorCode errorCode) {
//
//            }
//        });
        return convertView;
    }
    class ViewHolder {
        ImageView photoItemRongIV;
        TextView nameItemRongTV;
        TextView contentItemRongTV;
        TextView timeItemRongTV;

        public ViewHolder(View view) {
            photoItemRongIV = (ImageView) view.findViewById(R.id.photoItemRongIV);
            nameItemRongTV = (TextView) view.findViewById(R.id.nameItemRongTV);
            contentItemRongTV = (TextView) view.findViewById(R.id.contentItemRongTV);
            timeItemRongTV = (TextView) view.findViewById(R.id.timeItemRongTV);
            view.setTag(this);
        }
    }

}

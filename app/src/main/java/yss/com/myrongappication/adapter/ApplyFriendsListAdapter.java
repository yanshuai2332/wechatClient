package yss.com.myrongappication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yss.com.myrongappication.R;
import yss.com.myrongappication.activity.RegistActivity;
import yss.com.myrongappication.application.ExitApplication;
import yss.com.myrongappication.http.Api;
import yss.com.myrongappication.preference.UserIdDao;
import yss.com.myrongappication.resp.FriendsList;

import static yss.com.myrongappication.R.id.photoItemRongIV;

/**
 * Created by Administrator on 2016-11-16.
 */

public class ApplyFriendsListAdapter extends BaseAdapter {
    private List<FriendsList> datalist;
    private Context context;

    public ApplyFriendsListAdapter(List<FriendsList> datalist, Context context) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context,
                    R.layout.apply_friends_item, null);
            new ViewHolder(convertView);
        }
        final ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.nameApplicantItemTV.setText(getItem(position).getNickName());
        Picasso.with(context).load(getItem(position).getPortrait()).into(holder.picApplicantItemIV);
        if(getItem(position).getState().equals("1")){
            holder.acceptApplicantItemTV.setText("accepted");
            holder.acceptApplicantItemTV.setBackgroundResource(R.color.grey);
            holder.acceptApplicantItemTV.setEnabled(false);
        }
        holder.acceptApplicantItemTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "click", Toast.LENGTH_SHORT).show();
                Api.getApi(context).agreeFriendsApply(UserIdDao.getUserId(context),getItem(position).getUserId()).enqueue(new Callback<Map>() {
                    @Override
                    public void onResponse(Call<Map> call, Response<Map> response) {
                        Map map=response.body();
                        String result=map.get("result").toString();
                        if(result.equals("success"))
                        {
                            holder.acceptApplicantItemTV.setText("accepted");
                            holder.acceptApplicantItemTV.setBackgroundResource(R.color.grey);
                            holder.acceptApplicantItemTV.setClickable(false);
                            notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<Map> call, Throwable t) {

                    }
                });
            }
        });
        return convertView;
    }
    class ViewHolder {
        ImageView picApplicantItemIV;
        TextView nameApplicantItemTV;
        TextView contentApplicantItemTV;
        TextView acceptApplicantItemTV;

        public ViewHolder(View view) {
            picApplicantItemIV = (ImageView) view.findViewById(R.id.picApplicantItemIV);
            nameApplicantItemTV = (TextView) view.findViewById(R.id.nameApplicantItemTV);
            contentApplicantItemTV = (TextView) view.findViewById(R.id.contentApplicantItemTV);
            acceptApplicantItemTV = (TextView) view.findViewById(R.id.acceptApplicantItemTV);
            view.setTag(this);
        }
    }

}

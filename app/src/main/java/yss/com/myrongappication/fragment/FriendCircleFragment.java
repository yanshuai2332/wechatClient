package yss.com.myrongappication.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yss.com.myrongappication.R;
import yss.com.myrongappication.friendcircle.friendcircledemo.activity.PublishCircleActivity;
import yss.com.myrongappication.friendcircle.friendcircledemo.adapter.CircleAdapter;
import yss.com.myrongappication.friendcircle.friendcircledemo.bean.CircleBean;
import yss.com.myrongappication.http.Api;
import yss.com.myrongappication.preference.UserIdDao;
import yss.com.myrongappication.resp.UserInfoVo;

@SuppressLint("ValidFragment")
public class FriendCircleFragment extends Fragment implements View.OnClickListener {
    private List<CircleBean> mCircleBeans;
    private ListView mListView;
    private List<String> mImgList;
    private String mTitle;

    public static FriendCircleFragment getInstance(String title) {
        FriendCircleFragment sf = new FriendCircleFragment();
        sf.mTitle = title;
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_friendcirclemain, null);
        initData();
        mListView = (ListView) v.findViewById(R.id.listview);
        v.findViewById(R.id.iv_publish_circle).setOnClickListener(this);

        return v;
    }

    // 初始化数据
    private void initData() {
        Api.getApi(getContext()).getCircleList(UserIdDao.getUserId(getContext())).enqueue(new Callback<List<CircleBean>>() {
            @Override
            public void onResponse(Call<List<CircleBean>> call, Response<List<CircleBean>> response) {
                mCircleBeans = response.body();
                mListView.setAdapter(new CircleAdapter(mCircleBeans, getContext()));
            }

            @Override
            public void onFailure(Call<List<CircleBean>> call, Throwable t) {

            }
        });

        // 模拟请求网络获取到数据
//        mImgList = new ArrayList<>();
//        mImgList.add("http://imgsrc.baidu.com/forum/w%3D580/sign=7eb05e9eddf9d72a17641015e428282a/3e87194c510fd9f9b3d66fc8212dd42a2a34a4c9.jpg");
//        mImgList.add("http://imgsrc.baidu.com/forum/w%3D580/sign=f09bb261cfbf6c81f7372ce08c3eb1d7/c213c895d143ad4bbd0a10c981025aafa40f06b6.jpg");
//        mImgList.add("http://img5.ph.126.net/PpGsC74VUQWuERP1gdwKGQ==/580964351947981315.jpg");
//        mImgList.add("http://img1.imgtn.bdimg.com/it/u=3749455878,661128293&fm=21&gp=0.jpg");
//        mImgList.add("http://ww2.sinaimg.cn/mw600/a1957912gw1e8aj508g1mj20go0b40uh.jpg");
//        mImgList.add("http://img7.aili.com/201511/11/1447231729_95100300.jpg");
//        mImgList.add("http://ww2.sinaimg.cn/large/83dcc5dbjw1dtpbjzipdaj.jpg");
//        mImgList.add("http://p18.qhimg.com/t0144d6a0802f22be4f.jpg");
//        mImgList.add("http://img2.3lian.com/2014/f5/158/d/90.jpg");
//        mCircleBeans = new ArrayList<>();
//        for (int i = 1; i < 5; i++) {
//            CircleBean bean = new CircleBean();
//            bean.portrait = "http://img2.3lian.com/2014/f5/36/d/9.jpg";
//            bean.nickName = "昵称" + i;
//            bean.dynamicContent = "这几天北京的大雾霾，朋友圈被各种照片刷爆。两句话让我印象深刻。“我们面对如此的恶劣，为何不是反抗，而是自嘲。”“也许，这一天，让很多人重新思考他们的人生。”后来。真的有朋友发了很长的文字。说自己即将离开北京的打算。底下很多人发表着自己的意见。更多的观点其实也像是自我的安慰表达——过几年我们也一定会离开的。 用黑乎乎的照片发了一条微博。感慨了一小句。这是一座用年轻梦想健康金钱作为代价才能好......";
//            bean.releaseTime = "2016-12-" + i;
//            bean.circleImg = mImgList;
//            bean.goodCount = 11 + i;
//            mCircleBeans.add(bean);
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_publish_circle:
                startActivity(new Intent(getContext(), PublishCircleActivity.class));
                break;
        }
    }
}
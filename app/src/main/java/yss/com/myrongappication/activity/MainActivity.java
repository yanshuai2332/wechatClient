package yss.com.myrongappication.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import yss.com.myrongappication.fragment.ConversationListDynamicFragment;
import yss.com.myrongappication.R;
import yss.com.myrongappication.fragment.SimpleCardFragment;
import yss.com.myrongappication.adapter.MainViewPagerAdapter;
import yss.com.myrongappication.entity.TabEntity;

public class MainActivity extends AppCompatActivity {
    private Context mContext = this;
    private ArrayList<Fragment> mFragments2 = new ArrayList<>();

    private String[] mTitles = {"首页", "消息", "联系人", "更多"};
    private int[] mIconUnselectIds = {
            R.mipmap.tab_home_unselect, R.mipmap.tab_speech_unselect,
            R.mipmap.tab_contact_unselect, R.mipmap.tab_more_unselect};
    private int[] mIconSelectIds = {
            R.mipmap.tab_home_select, R.mipmap.tab_speech_select,
            R.mipmap.tab_contact_select, R.mipmap.tab_more_select};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private View mDecorView;
    private ViewPager mViewPager;
    private CommonTabLayout mTabLayout_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_tab);
        //初始化控件
        init();
        //给控件添加监听器
        initViewListener();


    }

    private void initViewListener() {
        mTabLayout_3.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position,true);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout_3.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void init() {
        ConversationListDynamicFragment conversationListDynamicFragment=new ConversationListDynamicFragment();
        mFragments2.add(conversationListDynamicFragment);
        mFragments2.add(conversationListDynamicFragment);
        mFragments2.add(SimpleCardFragment.getInstance("ss" ));
        mFragments2.add(SimpleCardFragment.getInstance("dd" ));

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        mDecorView = getWindow().getDecorView();
        mViewPager = (ViewPager) mDecorView.findViewById(R.id.vp_2);
        mTabLayout_3 = (CommonTabLayout) mDecorView.findViewById(R.id.tl_3);
        mViewPager.setAdapter(new MainViewPagerAdapter(getSupportFragmentManager(),mFragments2));
        mTabLayout_3.setTabData(mTabEntities);
        mTabLayout_3.setCurrentTab(1);
        mViewPager.setCurrentItem(1);
        //显示未读红点
        mTabLayout_3.showDot(1);
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    protected int dp2px(float dp) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
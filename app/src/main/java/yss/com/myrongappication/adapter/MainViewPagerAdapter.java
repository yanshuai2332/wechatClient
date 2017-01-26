package yss.com.myrongappication.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016-11-11.
 */

public class MainViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragmetList = new ArrayList<>();
    public MainViewPagerAdapter(FragmentManager fm,ArrayList<Fragment> fragmetList) {
        super(fm);
        this.fragmetList=fragmetList;

    }


    @Override
    public int getCount() {
        return fragmetList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragmetList == null || position < 0 || position > fragmetList.size() ? null : fragmetList.get(position);
    }
}

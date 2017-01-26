package yss.com.myrongappication.friendcircle.friendcircledemo.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import yss.com.myrongappication.R;
import yss.com.myrongappication.friendcircle.friendcircledemo.utils.MeasureUtils;
import yss.com.myrongappication.friendcircle.friendcircledemo.utils.UiUtils;


public class CircleGridAdapter extends BaseAdapter {
	
	private String[] mFiles;
	private LayoutInflater mLayoutInflater;
	
	public CircleGridAdapter(String[] files) {
		this.mFiles = files;
		mLayoutInflater = LayoutInflater.from(UiUtils.getContext());
	}

	@Override
	public int getCount() {
		return mFiles == null ? 0 : mFiles.length;
	}

	@Override
	public String getItem(int position) {
		return mFiles[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mLayoutInflater.inflate(R.layout.item_gridview_circle,parent, false);
			holder.imageView = (ImageView) convertView.findViewById(R.id.album_image);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// 根据屏幕宽度动态设置图片宽高
		int width = MeasureUtils.getWidth(UiUtils.getContext());
		//根据图片数量动态设置图片宽高 i为摆放列数
		int i=1;
		if(getCount()>1)
			i=2;
		if(getCount()>4)
			i=3;
		//空余宽度为左边距15+46右边距+32grid空间+5*i
		int bankWidth=UiUtils.dip2px(93+6*i);
		int imageWidth = (width - bankWidth)/ i;
		LayoutParams lp = holder.imageView.getLayoutParams();
		lp.width = imageWidth;
		lp.height = imageWidth;
		holder.imageView.setLayoutParams(lp);
		String url = getItem(position);
		ImageLoader.getInstance().displayImage(url, holder.imageView);
		return convertView;
	}

	private static class ViewHolder {
		ImageView imageView;
	}
}

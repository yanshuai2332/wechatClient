package yss.com.myrongappication.friendcircle.friendcircledemo.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import yss.com.myrongappication.R;
import yss.com.myrongappication.friendcircle.friendcircledemo.activity.ImagePagerActivity;
import yss.com.myrongappication.friendcircle.friendcircledemo.bean.CircleBean;
import yss.com.myrongappication.friendcircle.friendcircledemo.utils.UiUtils;
import yss.com.myrongappication.friendcircle.friendcircledemo.view.NoScrollGridView;


public class CircleAdapter extends BaseAdapter {
		
		private List<CircleBean> mCircleBeans;
		private Context mContext;
		
		public CircleAdapter(List<CircleBean> beans, Context context) {
			this.mCircleBeans = beans;
			this.mContext = context;
		}
		
		@Override
		public int getCount() {
			return mCircleBeans.size();
		}

		@Override
		public Object getItem(int position) {
			return mCircleBeans.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if(convertView == null){
				convertView = UiUtils.inflate(R.layout.item_listview_circle);
						//View.inflate(mContext,R.layout.item_listview_circle,null);
				holder = new ViewHolder();
				holder.iv_commentCount = (ImageView) convertView.findViewById(R.id.iv_commentCount);
				holder.tv_dynamicDesc = (TextView) convertView.findViewById(R.id.tv_dynamicDesc);
				holder.tv_releaseTime = (TextView) convertView.findViewById(R.id.tv_releaseTime);
				holder.gridView = (NoScrollGridView) convertView.findViewById(R.id.gridView);
				holder.tv_nickname = (TextView) convertView.findViewById(R.id.tv_nickname);
				holder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			final CircleBean bean = (CircleBean) getItem(position);
			if(null != bean){
				// 设置头像
				ImageLoader.getInstance().displayImage(bean.portrait, holder.iv_icon);
				// 设置昵称
				holder.tv_nickname.setText(bean.nickName);
				// 设置说说
				holder.tv_dynamicDesc.setText(bean.dynamicContent);
				// 设置发布时间
				holder.tv_releaseTime.setText(bean.releaseTime);
				// 设置点赞总数
				//holder.tv_goodCount.setText(String.valueOf(bean.goodCount));
				// 设置评论总数
				//holder.tv_commentCount.setText(String.valueOf(bean.commentCount));
				// 判断如果用户是否上传图片
				if(bean.circleImg != null && bean.circleImg.size() > 0){
					// 有：设置Adapter显示图片
					holder.gridView.setVisibility(View.VISIBLE);
					//设置图片摆放格式
					if(bean.circleImg.size()>4)
						holder.gridView.setNumColumns(3);
					else if(bean.circleImg.size()>1)
						holder.gridView.setNumColumns(2);
					// 图片数组转图片集合
					final String[] urls = bean.circleImg.toArray(new String[bean.circleImg.size()]);
					holder.gridView.setAdapter(new CircleGridAdapter(urls));
					// 设置点击事件
					holder.gridView.setOnItemClickListener(new OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							enterPhotoDetailed(urls, position);
						}
					});
				}else{
					// 否：隐藏
					holder.gridView.setVisibility(View.GONE);
				}
			}
			return convertView;
		}
		
		/**
		 * 进入图片详情页
		 * @param urls 图片数组
		 * @param position 角标
		 */
		protected void enterPhotoDetailed(String[] urls, int position) {
			Intent intent = new Intent(mContext, ImagePagerActivity.class);
			intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls);
			intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
			mContext.startActivity(intent);
		}

		private static class ViewHolder{
			ImageView iv_icon;
			TextView tv_nickname;
			TextView tv_dynamicDesc;
			TextView tv_releaseTime;
			//TextView tv_goodCount;
			ImageView iv_commentCount;
			NoScrollGridView gridView;
		}
}
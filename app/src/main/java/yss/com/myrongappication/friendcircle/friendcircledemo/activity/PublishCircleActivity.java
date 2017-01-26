package yss.com.myrongappication.friendcircle.friendcircledemo.activity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yss.com.myrongappication.R;
import yss.com.myrongappication.activity.ImprovePersonalInfoActivity;
import yss.com.myrongappication.friendcircle.friendcircledemo.base.BaseActivity;
import yss.com.myrongappication.friendcircle.friendcircledemo.utils.MyAlertDialog;
import yss.com.myrongappication.friendcircle.friendcircledemo.utils.MyPopupWindow;
import yss.com.myrongappication.friendcircle.friendcircledemo.utils.SystemUtils;
import yss.com.myrongappication.friendcircle.friendcircledemo.utils.ToastUtils;
import yss.com.myrongappication.friendcircle.friendcircledemo.view.NoScrollGridView;
import yss.com.myrongappication.friendcircle.photo.selector.Bimp;
import yss.com.myrongappication.friendcircle.photo.selector.FileUtils;
import yss.com.myrongappication.friendcircle.photo.selector.PhotoActivity;
import yss.com.myrongappication.friendcircle.photo.selector.TestPicActivity;
import yss.com.myrongappication.http.Api;
import yss.com.myrongappication.preference.UserIdDao;
import yss.com.myrongappication.util.BitmapUtil;
import yss.com.myrongappication.util.StringUtil;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static java.util.UUID.randomUUID;
import static yss.com.myrongappication.R.id.et_input;
import static yss.com.myrongappication.R.id.improveUserNameEt;
import static yss.com.myrongappication.friendcircle.photo.selector.FileUtils.files2Parts;


/**
 * 发布圈子
 * @author zhouyou
 */
public class PublishCircleActivity extends BaseActivity implements TextWatcher, OnClickListener {
	private static final int MAX_INPUT_LENGTH = 140;// 最大输入长度 
	private static final int TAKE_PICTURE = 0x000000;
	private NoScrollGridView mGridView;
	private EditText mEditText;
	private TextView mHintView;
	private Button mSendCircle;
	private GridAdapter mAdapter;
	private String mImageFileName;
	public static String mImagePath;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_publish_circle);
		initView();
		addListener();
	}

	private void initView() {
		setTitle("发布圈子");
		setTitleLeftImg(R.drawable.ico_back);
		mSendCircle = (Button) findViewById(R.id.btn_send_circle);
		mEditText = (EditText) findViewById(et_input);
		mHintView = (TextView) findViewById(R.id.tv_hint);
		mGridView = (NoScrollGridView) findViewById(R.id.gv_gridview);
		mEditText.addTextChangedListener(this);
		mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		mAdapter = new GridAdapter(this);
		mAdapter.update();
		mGridView.setAdapter(mAdapter);
		mGridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (arg2 == Bimp.mBmps.size()) {
					showPopupWindow();
				} else {
					Intent intent = new Intent(PublishCircleActivity.this, PhotoActivity.class);
					intent.putExtra("ID", arg2);
					startActivity(intent);
				}
			}
		});
	}
	
	private void showPopupWindow() {
		isKeyboardShownToHideKeyboard();
		MyPopupWindow popupWindow = new MyPopupWindow(this);
		String[] str = {"拍照","从相册中选择"};
		popupWindow.showPopupWindowForFoot(str, new MyPopupWindow.Callback() {
			@Override
			public void callback(String text, int position) {
				switch (position) {
				case 0:
					photo();
					break;
				case 1:
					Intent intent = new Intent(PublishCircleActivity.this, TestPicActivity.class);
					startActivity(intent);
					break;
				}
			}
		});
	}
	
	@SuppressLint("HandlerLeak")
	public class GridAdapter extends BaseAdapter {
		private LayoutInflater inflater; // 视图容器
		private int selectedPosition = -1;// 选中的位置
		private boolean shape;

		public boolean isShape() {
			return shape;
		}

		public void setShape(boolean shape) {
			this.shape = shape;
		}

		public GridAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public void update() {
			loading();
		}

		public int getCount() {
			return (Bimp.mBmps.size() + 1);
		}

		public Object getItem(int arg0) {
			return null;
		}

		public long getItemId(int arg0) {
			return 0;
		}

		public void setSelectedPosition(int position) {
			selectedPosition = position;
		}

		public int getSelectedPosition() {
			return selectedPosition;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_published_grida, parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView.findViewById(R.id.item_grida_image);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (position == Bimp.mBmps.size()) {
				holder.image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon_addpic_unfocused));
				if (position == 9) {
					holder.image.setVisibility(View.GONE);
				}
			}else {
				holder.image.setImageBitmap(Bimp.mBmps.get(position));
			}
			return convertView;
		}

		public class ViewHolder {
			public ImageView image;
		}

		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					mAdapter.notifyDataSetChanged();
					break;
				}
				super.handleMessage(msg);
			}
		};

		public void loading() {
			new Thread(new Runnable() {
				public void run() {
					while (true) {
						if (Bimp.mMax == Bimp.mDrr.size()) {
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
							break;
						} else {
							try {
								if(Bimp.mDrr.size() <= 0)
									return;
								String path = Bimp.mDrr.get(Bimp.mMax);
								Bitmap bm = Bimp.revitionImageSize(path);
								Bimp.mBmps.add(bm);
								String newStr = path.substring(path.lastIndexOf("/") + 1,path.lastIndexOf("."));
								FileUtils.saveBitmap(bm, "" + newStr);
								Bimp.mMax += 1;
								Message message = new Message();
								message.what = 1;
								handler.sendMessage(message);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}).start();
		}
	}

	public String getString(String s) {
		String path = null;
		if (s == null)
			return "";
		for (int i = s.length() - 1; i > 0; i++) {
			s.charAt(i);
		}
		return path;
	}

	protected void onRestart() {
		mAdapter.update();
		super.onRestart();
	}

	/** 拍照 */
	public void photo() {
		// 随机缓存照片名
		mImageFileName = FileUtils.getFileNameForSystemTime(".jpg");
		// 首先判断SD卡是否存在
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(new File(Environment.getExternalStorageDirectory(),mImageFileName)));
			startActivityForResult(intent, TAKE_PICTURE);
		}else{
			ToastUtils.showToast(this, "内存卡不存在");
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK){
			// 拍照
			if(requestCode == TAKE_PICTURE){
				if (Bimp.mDrr.size() < 9 && resultCode == -1) {
					File file = new File(Environment.getExternalStorageDirectory() + "/" + mImageFileName);
					mImagePath = file.getPath();
					Bimp.mDrr.add(mImagePath); 
				}
			}
		}
	}
	
	private void addListener() {
		mSendCircle.setOnClickListener(this);
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,int after) {}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		int length = mEditText.getText().toString().length();
		mHintView.setVisibility(length >= 1 ? View.VISIBLE : View.GONE);
		mHintView.setTextColor(length < MAX_INPUT_LENGTH ? Color.BLACK : Color.RED);
		mHintView.setText(String.valueOf(MAX_INPUT_LENGTH - length));
	}

	@Override
	public void afterTextChanged(Editable s) {}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_send_circle:// 发送圈子
			sendCircle();
			break;
		}
	}

	private void sendCircle() {
		List<String> list = new ArrayList<String>();				
		for (int i = 0; i < Bimp.mDrr.size(); i++) {
			String Str = Bimp.mDrr.get(i).substring(Bimp.mDrr.get(i).lastIndexOf("/") + 1, Bimp.mDrr.get(i).lastIndexOf("."));
			list.add(FileUtils.SDPATH + Str+".JPEG");				
		}
		// 高清的压缩图片全部就在  list 路径里面了
		// 高清的压缩过的 bmp 对象  都在 Bimp.mBmps里面
		// 完成上传服务器后删除缓存
//		File file = BitmapUtil.bitmapToFile("/mnt/sdcard/portrait.jpg", image);
//		RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
//		MultipartBody.Part body = MultipartBody.Part.createFormData("portrait", file.getName(), requestBody);
//		Api.getApi(this).updateUserInfo(body, UserIdDao.getUserId(this), improveUserNameEt.getText().toString(), sex).enqueue(new Callback<Map>() {
//			@Override
//			public void onResponse(Call<Map> call, Response<Map> response) {
//				Map map = response.body();
//				String result = (String) map.get("result");
//				Toast.makeText(ImprovePersonalInfoActivity.this, result, Toast.LENGTH_SHORT).show();
//				finish();
//			}
//
//			@Override
//			public void onFailure(Call<Map> call, Throwable t) {
//
//			}
//		});
		int i=0;
		String[] filePaths=new String[Bimp.mBmps.size()];
		for (Bitmap bm:Bimp.mBmps){
			String pathi= StringUtil.concatString("/mnt/sdcard/",String.valueOf(i),UUID.randomUUID().toString(),".jpg");
			File file = BitmapUtil.bitmapToFile(pathi, bm);
			filePaths[i]=pathi;
			i++;
		}
		List<MultipartBody.Part> imgList=FileUtils.files2Parts("imgList",filePaths,MediaType.parse("image/*"));
		Api.getApi(this).publishCircle(UserIdDao.getUserId(this),mEditText.getText().toString(),imgList).enqueue(new Callback<Map>() {
			@Override
			public void onResponse(Call<Map> call, Response<Map> response) {
				Map map = response.body();
				String result = (String) map.get("result");
				Toast.makeText(PublishCircleActivity.this, result, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onFailure(Call<Map> call, Throwable t) {

			}
		});
		FileUtils.deleteDir();
		list.clear();
		Bimp.mBmps.clear();
		Bimp.mDrr.clear();
		Bimp.mMax = 0;
		finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			exitHint();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onLeftImageViewClick(View view) {
		exitHint();
	}

	/** 退出提示  */
	private void exitHint() {
		// 判断是否有改动
		if(mEditText.getText().toString().length() > 0 || Bimp.mDrr.size() > 0 || !TextUtils.isEmpty(mImagePath)){
			isKeyboardShownToHideKeyboard();
			// 弹出提示框提示是否确认退出
			MyAlertDialog.showDialogForIOS(this, "提示", "确认要舍弃内容并退出当前页面吗？", new MyAlertDialog.OnCallbackListener() {
				@Override
				public void onConfrimClick(AlertDialog dialog) {
					dialog.dismiss();
					FileUtils.deleteDir();
					Bimp.mBmps.clear();
					Bimp.mDrr.clear();
					Bimp.mMax = 0;
					PublishCircleActivity.this.finish();
				}

				@Override
				public void onCancelClick(AlertDialog dialog) {
					dialog.dismiss();
				}
			});
		}else{
			finish();
		}
	}
	
	/** 判断软键盘是否弹起如弹起则隐藏 */
	private void isKeyboardShownToHideKeyboard(){
		if(SystemUtils.isKeyboardShown(mEditText.getRootView())){
			SystemUtils.hideKeyboard(this, mEditText.getApplicationWindowToken());
		}
	}
}

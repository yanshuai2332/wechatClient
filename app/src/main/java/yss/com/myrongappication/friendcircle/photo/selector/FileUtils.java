package yss.com.myrongappication.friendcircle.photo.selector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.graphics.Bitmap;
import android.os.Environment;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class FileUtils {
	
	public static String SDPATH = Environment.getExternalStorageDirectory() + "/cache/";
	
	/**
	 * 使用系统时间获取文件名
	 * @param fileExtension 文件后缀名
	 * @return 文件名
	 */
	public static String getFileNameForSystemTime(String fileExtension){
		if(fileExtension.length() < 1 || !fileExtension.startsWith(".")){
			return "";
		}
		Calendar cl = Calendar.getInstance();
		cl.setTime(Calendar.getInstance().getTime());
		long milliseconds = cl.getTimeInMillis();
		return String.valueOf(milliseconds) + fileExtension;
	}

	public static void saveBitmap(Bitmap bm, String picName) {
		try {
			if (!isFileExist("")) {
				createSDDir("");
			}
			File f = new File(SDPATH, picName + ".JPEG"); 
			if (f.exists()) {
				f.delete();
			}
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static File createSDDir(String dirName) throws IOException {
		File dir = new File(SDPATH + dirName);
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			System.out.println("createSDDir:" + dir.getAbsolutePath());
			System.out.println("createSDDir:" + dir.mkdir());
		}
		return dir;
	}

	public static boolean isFileExist(String fileName) {
		File file = new File(SDPATH + fileName);
		file.isFile();
		return file.exists();
	}
	
	public static void delFile(String fileName){
		File file = new File(SDPATH + fileName);
		if(file.isFile()){
			file.delete();
        }
		file.exists();
	}

	public static void deleteDir() {
		File dir = new File(SDPATH);
		if (dir == null || !dir.exists() || !dir.isDirectory())
			return;
		for (File file : dir.listFiles()) {
			if (file.isFile())
				file.delete(); // 删除所有文件
			else if (file.isDirectory())
				deleteDir(); // 递规的方式删除文件夹
		}
		dir.delete();// 删除目录本身
	}

	public static boolean fileIsExists(String path) {
		try {
			File f = new File(path);
			if (!f.exists()) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	/**
	 * 将文件路径数组封装为{@link List < MultipartBody.Part>}
	 * @param key 对应请求正文中name的值。目前服务器给出的接口中，所有图片文件使用<br>
	 * 同一个name值，实际情况中有可能需要多个
	 * @param filePaths 文件路径数组
	 * @param imageType 文件类型
	 */
	public static List<MultipartBody.Part> files2Parts(String key,
													   String[] filePaths, MediaType imageType) {
		List<MultipartBody.Part> parts = new ArrayList<>(filePaths.length);
		for (String filePath : filePaths) {
			File file = new File(filePath);
			// 根据类型及File对象创建RequestBody（okhttp的类）
			RequestBody requestBody = RequestBody.create(imageType, file);
			// 将RequestBody封装成MultipartBody.Part类型（同样是okhttp的）
			MultipartBody.Part part = MultipartBody.Part.
					createFormData(key, file.getName(), requestBody);
			// 添加进集合
			parts.add(part);
		}
		return parts;
	}
}

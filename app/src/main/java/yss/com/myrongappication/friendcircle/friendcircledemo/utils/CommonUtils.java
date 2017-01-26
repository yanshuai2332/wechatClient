package yss.com.myrongappication.friendcircle.friendcircledemo.utils;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

public class CommonUtils {

	public static String getRealPathFromURI(Uri uri, ContentResolver resolver) {
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = resolver.query(uri, proj, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		String str = cursor.getString(column_index);
		cursor.close();
		return str;
	}

}

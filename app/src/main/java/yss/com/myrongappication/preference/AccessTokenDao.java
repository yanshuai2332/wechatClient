package yss.com.myrongappication.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * token的dao
 * Created by Administrator on 2016/4/11.
 */
public class AccessTokenDao {
    public static final String key="accessToken";
    public  static void setAccessToken(Context context,String accessToken){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(key,accessToken);
        editor.apply();
    }
    public static String getAccessToken(Context context){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        return  sharedPreferences.getString(key,null);
    }
}

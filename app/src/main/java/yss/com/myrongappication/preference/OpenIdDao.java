package yss.com.myrongappication.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class OpenIdDao {
    public static final String key="openId";
    public  static void setOpenId(Context context,String openId){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(key, openId);
        editor.apply();
    }
    public static String getOpenId(Context context){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        return  sharedPreferences.getString(key, null);
    }
}

package yss.com.myrongappication.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class NickNameDao {
    public static final String key="nickName";
    public  static void setNickName(Context context,String nickName){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(key, nickName);
        editor.apply();
    }
    public static String getNickName(Context context){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        return  sharedPreferences.getString(key, null);
    }
}

package yss.com.myrongappication.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class SexDao {
    public static final String key="sex";
    public  static void setSex(Context context,String sex){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(key, sex);
        editor.apply();
    }
    public static String getSex(Context context){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        return  sharedPreferences.getString(key, null);
    }
}

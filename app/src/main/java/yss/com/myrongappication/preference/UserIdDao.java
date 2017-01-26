package yss.com.myrongappication.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static io.rong.imlib.statistics.UserData.phone;


public class UserIdDao {
    public static final String key="userId";
    public  static void setUserId(Context context,String userId){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(key, userId);
        editor.apply();
    }
    public static String getUserId(Context context){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        return  sharedPreferences.getString(key, null);
    }
}

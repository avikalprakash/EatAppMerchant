package organisation.app.eat.eatapp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Fujitsu on 14/06/2017.
 */

public class SaveUserId {
    private static final String SHARED_PREF_NAME1 = "saveuserid";
    private static final String SHARED_PREF_NAME2 = "savestaffid";
    private static final String TAG_TOKEN = "save";

    private static SaveUserId mInstance;
    private static Context mCtx;

    private SaveUserId(Context context) {
        mCtx = context;
    }

    public static synchronized SaveUserId getInstance(Context context)
    {
        if (mInstance == null) {
            mInstance = new SaveUserId(context);
        }
        return mInstance;
    }

    //this method will save the device token to shared preferences
    public boolean saveuserId(String id)
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME1, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TAG_TOKEN, id);
        editor.apply();
        return true;
    }

    public boolean savestaffId(String staff_id)
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME2, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TAG_TOKEN, staff_id);
        editor.apply();
        return true;
    }

    //this method will fetch the device token from shared preferences
    public String getUserId()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME1, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(TAG_TOKEN, null);
    }

    public String getStaffId()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME2, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(TAG_TOKEN, null);
    }

}

package dinodia.pwn.com.ncs_v5.Models;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import dinodia.pwn.com.ncs_v5.R;

public class Ward {
    private Context context=null;
    private SharedPreferences preferences;

    public Ward(Context context) {
        this.context=context;
        preferences=context.getSharedPreferences(context.getResources().getString(R.string.fields),Context.MODE_PRIVATE);
    }
    public String getUuid() {
        return preferences.getString(PreferenceKeys.uuid,context.getResources().getString(R.string.nopatient));
    }
    public void setUuid(String uuid) {
        preferences.edit().putString(PreferenceKeys.uuid,uuid).apply();
    }
    public String getWard_name() {
        return preferences.getString(PreferenceKeys.wardName,context.getResources().getString(R.string.nopatient));
    }
    public void setWard_name(String ward_name) {
        preferences.edit().putString(PreferenceKeys.wardName,ward_name).apply();
    }
    public int getBed_no() {
        return preferences.getInt(PreferenceKeys.bedNO,0);
    }
    public void setBed_no(int bed_no) {
        preferences.edit().putInt(PreferenceKeys.bedNO,bed_no).apply();
    }

    public boolean isConnected() {
        return preferences.getBoolean(PreferenceKeys.connected,false);
    }
    public void setConnected(boolean connected){
        preferences.edit().putBoolean(PreferenceKeys.connected,connected).apply();
    }
    public void printWard(){
        Log.d("events","UUID:> "+this.getUuid());
        Log.d("events","Ward Name:> "+this.getWard_name());
        Log.d("events","Bed no.:> "+this.getBed_no());
    }
}

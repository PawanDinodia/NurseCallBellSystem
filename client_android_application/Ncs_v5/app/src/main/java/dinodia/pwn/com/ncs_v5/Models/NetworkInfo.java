package dinodia.pwn.com.ncs_v5.Models;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import dinodia.pwn.com.ncs_v5.R;

public class NetworkInfo {
    private Context context=null;
    private SharedPreferences preferences;

    public NetworkInfo(Context context) {
        this.context=context;
        preferences=context.getSharedPreferences(context.getResources().getString(R.string.fields),Context.MODE_PRIVATE);
    }

    public String getIp_address() {
        return preferences.getString(PreferenceKeys.ipAddress,context.getResources().getString(R.string.nopatient));
    }

    public void setIp_address(String ip_address) {
        preferences.edit().putString(PreferenceKeys.ipAddress,ip_address).apply();
    }

    public int getPort() {
        return preferences.getInt(PreferenceKeys.portNo,0);
    }

    public void setPort(int port) {
        preferences.edit().putInt(PreferenceKeys.portNo,port).apply();
    }
    public void printNetworkInfo(){
        Log.d("events","Ip:> "+this.getIp_address());
        Log.d("events","Port:> "+this.getPort());
    }
}

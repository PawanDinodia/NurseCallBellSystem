package dinodia.pwn.com.ncs_v5;

import android.content.Context;
import android.content.SharedPreferences;

public class Fields {
    private String name,reg_no,ward_name,ip_address;
    private int port_no,bed_no;
    private boolean night_mode;
    private String UUID;
    private boolean connected=false;
    private SharedPreferences preferences;
    private Context context;
    private int color;

    public Fields getAll(){
       return this;
    }

    public boolean isConnected() {
        return preferences.getBoolean("connected",false);
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
        preferences.edit().putBoolean("connected",connected).apply();
    }

    public String getUUID() {
        return preferences.getString("uuid",context.getResources().getString(R.string.nopatient));
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
        preferences.edit().putString("uuid",UUID).apply();
    }

    public int getColor() {
        return preferences.getInt("color",R.color.topColor3);
    }

    public void setColor(int color) {
        this.color = color;
        preferences.edit().putInt("color",color).apply();
    }

    public Fields(Context context) {
        this.context=context;
        preferences=context.getSharedPreferences(context.getResources().getString(R.string.fields),Context.MODE_PRIVATE);
    }

    public String getName() {
        return preferences.getString(context.getResources().getString(R.string.patient_name),context.getResources().getString(R.string.nopatient));
    }

    public void setName(String name) {
        this.name = name;
        preferences.edit().putString(context.getResources().getString(R.string.patient_name),name).apply();
    }

    String getReg_no() {
        return preferences.getString(context.getResources().getString(R.string.registration_no),context.getResources().getString(R.string.nopatient));
    }

    void setReg_no(String reg_no) {
        this.reg_no = reg_no;
        preferences.edit().putString(context.getResources().getString(R.string.registration_no),reg_no).apply();
    }

    public String getWard_name() {
        return preferences.getString(context.getResources().getString(R.string.ward_name),context.getResources().getString(R.string.nopatient));
    }

    public void setWard_name(String ward_name) {
        this.ward_name = ward_name;
        preferences.edit().putString(context.getResources().getString(R.string.ward_name),ward_name).apply();
    }

    public String getIp_address() {
        return preferences.getString(context.getResources().getString(R.string.ip_address),context.getResources().getString(R.string.nopatient));
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
        preferences.edit().putString(context.getString(R.string.ip_address),ip_address).apply();
    }

    public int getPort_no() {
        return preferences.getInt(context.getResources().getString(R.string.port_no),0);
    }

    public void setPort_no(int port_no) {
        this.port_no = port_no;
        preferences.edit().putInt(context.getResources().getString(R.string.port_no),port_no).apply();
    }

    public int getBed_no() {
        return preferences.getInt(context.getResources().getString(R.string.bed_no),0);
    }

    public void setBed_no(int bed_no) {
        this.bed_no = bed_no;
        preferences.edit().putInt(context.getResources().getString(R.string.bed_no),bed_no).apply();
    }

    public boolean isNight_mode() {
        return preferences.getBoolean(context.getResources().getString(R.string.night_mode),false);
    }

    public void setNight_mode(boolean night_mode) {
        this.night_mode = night_mode;
        preferences.edit().putBoolean(context.getResources().getString(R.string.night_mode),night_mode).apply();
    }
}
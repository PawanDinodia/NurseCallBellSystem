package dinodia.pwn.com.ncs_v5.Models;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import dinodia.pwn.com.ncs_v5.R;

public class Patient {
    private Context context=null;
    private SharedPreferences preferences;

    public Patient(Context context) {
        this.context=context;
        preferences=context.getSharedPreferences(context.getResources().getString(R.string.fields),Context.MODE_PRIVATE);
    }

    public String getPatient_name() {
        return preferences.getString(PreferenceKeys.patientName,context.getResources().getString(R.string.nopatient));
    }

    public void setPatient_name(String patient_name) {
        preferences.edit().putString(PreferenceKeys.patientName,patient_name).apply();
    }

    public String getReg_no() {
        return preferences.getString(PreferenceKeys.regNo,context.getResources().getString(R.string.nopatient));
    }

    public void setReg_no(String reg_no) {
        preferences.edit().putString(PreferenceKeys.regNo,reg_no).apply();
    }
    public void printPatient(){
        Log.d("events","Patient name:> "+this.getPatient_name());
        Log.d("events","Registration no.:> "+this.getReg_no());
    }
}

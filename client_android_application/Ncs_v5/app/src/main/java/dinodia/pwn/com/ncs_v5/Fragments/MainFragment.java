package dinodia.pwn.com.ncs_v5.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import dinodia.pwn.com.ncs_v5.MainActivity;
import dinodia.pwn.com.ncs_v5.Models.AlarmState;
import dinodia.pwn.com.ncs_v5.Models.Patient;
import dinodia.pwn.com.ncs_v5.Models.Ward;
import dinodia.pwn.com.ncs_v5.NetworkControl.SettingEvents;
import dinodia.pwn.com.ncs_v5.PopupDialog;
import dinodia.pwn.com.ncs_v5.Prefs;
import dinodia.pwn.com.ncs_v5.R;
import dinodia.pwn.com.ncs_v5.RunTimeSettings;

public class MainFragment extends Fragment implements View.OnClickListener, PopupDialog.DialogEvents {
    private TextView ward_name_tv, bed_no_tv;
    private String bedNo;
    private Button alarm_btn;
    private ImageView online_offline_iv;

    private Ward ward;
    private SettingEvents events;
    public static boolean connectionStatus=false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_main, container, false);
        ward_name_tv = (TextView) view.findViewById(R.id.ward_name_tv);
        bed_no_tv = (TextView) view.findViewById(R.id.bed_no_tv);
        online_offline_iv = (ImageView) view.findViewById(R.id.online_offline_iv);

        final TextView temp_tv = (TextView) view.findViewById(R.id.temp_tv);
        final TextView hum_tv = (TextView) view.findViewById(R.id.hum_tv);
        ward = new Ward(getActivity().getApplicationContext());
        events = new SettingEvents();


        if (!ward.getWard_name().equals(getResources().getString(R.string.nopatient))) {
            ward_name_tv.setText(ward.getWard_name());
        }
        if (ward.getBed_no() != 0) {
            bedNo = "Bed no." + ward.getBed_no();
            bed_no_tv.setText(bedNo);
        }

        alarm_btn = (Button) view.findViewById(R.id.alarm_btn);

        if (Prefs.getAlarm_settings() == AlarmState.ALARM_ONN) {
            alarm_btn.setBackgroundResource(R.drawable.bell_onn);
        }
        alarm_btn.setOnClickListener(this);

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (Prefs.getCurrent_fragment() == RunTimeSettings.MAIN_FRAGMENT) {
                    try {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateOnlineOfflineState();
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 1000, 1000);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.alarm_btn:
                if (!(new Patient(getActivity().getApplicationContext()).getPatient_name()).equals(getActivity().getResources().getString(R.string.nopatient))) {
                    if(connectionStatus){
                    if (Prefs.getAlarm_settings() == AlarmState.ALARM_OFF) {
                        Prefs.setAlarm_settings(AlarmState.ALARM_ONN);
                        alarm_btn.setBackgroundResource(R.drawable.bell_onn);
                        events.alarmChange(AlarmState.ALARM_ONN,ward.getUuid());
                    } else {
                        Prefs.setAlarm_settings(AlarmState.ALARM_OFF);
                        alarm_btn.setBackgroundResource(R.drawable.bell_off);
                        events.alarmChange(AlarmState.ALARM_OFF,ward.getUuid());
                    }
                    }else {
                        Toast.makeText(getActivity().getApplicationContext(), "Not connected to server", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Please add a patient first", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                Toast.makeText(getActivity().getApplicationContext(), "Invalid event", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void positiveClick(DialogFragment fragment, String context) {
        switch (context) {
            case "name":
                break;
            case "registration":
                break;
            case "bed":
                bedNo = "Bed no." + ward.getBed_no();
                bed_no_tv.setText(bedNo);
                events.editWard(ward);
                break;
            case "ward":
                ward_name_tv.setText(ward.getWard_name());
                events.editWard(ward);
                break;
            case "ip":
                break;
            case "port":
                break;
            case "about":
                break;
            case "add":
                break;
            default:
                break;
        }
    }

    @Override
    public void negativeClick(DialogFragment fragment, String context) {

    }

    @Override
    public void onNumberEditTextChange(DialogFragment fragment, String newText, String context) {

    }

    @Override
    public void onTextEditTextChange(DialogFragment fragment, String newText, String context) {

    }

    private static long network_attempt = 0;

    public void updateOnlineOfflineState() {
        if (ward.isConnected()) {
            online_offline_iv.setImageResource(R.drawable.green_dot);
            network_attempt = 0;
            connectionStatus=true;
        } else {
            String LOG_TAG = "network";
            if (network_attempt>5&&network_attempt % 4==0) {
                connectionStatus=false;
                online_offline_iv.setImageResource(R.drawable.red_dot);
                Log.d(LOG_TAG, "-------Not connected to server!");
            }
            Log.d(LOG_TAG, "Network attempt:>" + network_attempt);
            network_attempt++;
        }
    }

}
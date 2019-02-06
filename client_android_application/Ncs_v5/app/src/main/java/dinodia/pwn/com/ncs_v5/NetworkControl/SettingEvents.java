package dinodia.pwn.com.ncs_v5.NetworkControl;

import android.util.Log;

import dinodia.pwn.com.ncs_v5.MainActivity;
import dinodia.pwn.com.ncs_v5.Models.AlarmState;
import dinodia.pwn.com.ncs_v5.Models.NetworkInfo;
import dinodia.pwn.com.ncs_v5.Models.Patient;
import dinodia.pwn.com.ncs_v5.Models.Ward;

public class SettingEvents {
    //private EventsListener listener;
    
    public SettingEvents() {
        //listener= MainActivity.networkThread;
    }


    public void alarmChange(AlarmState alarmState,String clientId){
        MainActivity.networkThread.alarmChange(alarmState,clientId);
        Log.d("events","Alarm change with:> "+alarmState.toString());
    }

    public void editWard(Ward ward) {
        MainActivity.networkThread.editWard(ward);
      // listener.editWard(ward);
        Log.d("events","Ward Edited:>\n");
        ward.printWard();
    }

    public void addPatient(String client_id,Patient patient) {
        MainActivity.networkThread.addPatient(client_id,patient);
        Log.d("events","Patient added:>\n");
        patient.printPatient();
    }

    public void editPatient(String client_id, Patient patient) {
        MainActivity.networkThread.editPatient(client_id,patient);
        Log.d("events","Patient Setting edited:>\n");
        patient.printPatient();
    }
    public void addBed(Ward ward){
        MainActivity.networkThread.addBed(ward);
        Log.d("events","bed is edited:>\n");
        ward.printWard();
    }
    public boolean editNetworkInfo(NetworkInfo networkInfo){
        Log.d("events","Network Info edited:>\n");
        networkInfo.printNetworkInfo();
        return  MainActivity.networkThread.editNetworkInfo(networkInfo);
    }
}

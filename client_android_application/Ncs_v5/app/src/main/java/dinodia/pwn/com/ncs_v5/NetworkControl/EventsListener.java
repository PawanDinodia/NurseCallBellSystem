package dinodia.pwn.com.ncs_v5.NetworkControl;

import dinodia.pwn.com.ncs_v5.Models.AlarmState;
import dinodia.pwn.com.ncs_v5.Models.NetworkInfo;
import dinodia.pwn.com.ncs_v5.Models.Patient;
import dinodia.pwn.com.ncs_v5.Models.Ward;

public interface EventsListener {
    void alarmChange(AlarmState alarmState,String clientId);
    void editWard(Ward ward);
    void addPatient(String client_id, Patient patient);
    void editPatient(String client_id, Patient patients);
    void addBed(Ward ward);
    boolean editNetworkInfo(NetworkInfo networkInfo);
}

package FrameWork.BedTileFrameWork;

import AlarmHandler.AlarmState;

/**
 * all bed fields
 */
public class BedModel {
    
    private int temperature =0;
    private int humidity=0;
    private int bed_no=999;
    private String patient_name="NoPatient";
    private AlarmState alarm_state =AlarmState.ALARM_OFF;
    private String reg_no="";
    private String client_id="";
    private boolean connected=false;
    private String ip="";
    private int port=0;


    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public int getBed_no() {
        return bed_no;
    }

    public void setBed_no(int bed_no) {
        this.bed_no = bed_no;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getRegNo() {
        return reg_no;
    }

    public void setRegNo(String reg_no) {
        this.reg_no = reg_no;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temp) {
        this.temperature = temp;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public String getPatientName() {
        return patient_name;
    }

    public void setPatientName(String patient_name) {
        this.patient_name = patient_name;
    }

    public AlarmState getAlarmstate() {
        return alarm_state;
    }

    public void setAlarmState(AlarmState alarm_state) {
        this.alarm_state = alarm_state;
    }
}

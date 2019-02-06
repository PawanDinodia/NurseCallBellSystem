package dinodia.pwn.com.ncs_v5;


import dinodia.pwn.com.ncs_v5.Models.AlarmState;

public class Prefs {

    static RunTimeSettings current_fragment=RunTimeSettings.MAIN_FRAGMENT;

    private static AlarmState alarm_settings=AlarmState.ALARM_OFF;

    public static AlarmState getAlarm_settings() {
        return alarm_settings;
    }

    public static void setAlarm_settings(AlarmState alarm_btn_settings) {
        Prefs.alarm_settings = alarm_btn_settings;
    }

    public static RunTimeSettings getCurrent_fragment() {
        return current_fragment;
    }

    public static void setCurrent_fragment(RunTimeSettings current_fragment) {
        Prefs.current_fragment = current_fragment;
    }
}

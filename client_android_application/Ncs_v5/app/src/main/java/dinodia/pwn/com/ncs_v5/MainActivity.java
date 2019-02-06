package dinodia.pwn.com.ncs_v5;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.PowerManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dinodia.pwn.com.ncs_v5.Fragments.MainFragment;
import dinodia.pwn.com.ncs_v5.Fragments.MainSettings;
import dinodia.pwn.com.ncs_v5.Models.AlarmState;
import dinodia.pwn.com.ncs_v5.Models.Patient;
import dinodia.pwn.com.ncs_v5.Models.Ward;
import dinodia.pwn.com.ncs_v5.NetworkControl.NetworkThread;
import dinodia.pwn.com.ncs_v5.NetworkControl.SettingEvents;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, PopupDialog.DialogEvents {
    Button settings_btn;
    Button patient_add_remove_btn;
    private Animation scale_up, scale_down, little_slide_left, little_slide_right;
    LinearLayout small_app_bar, big_top_bar;
    ImageView back;
    TextView patient_name_tv;
    Fields fields;
    List<Integer> colors = new ArrayList<>();
    PowerManager.WakeLock wakeLock;

    private Patient patient;
    private SettingEvents networkSettingEvents;
    @SuppressLint("StaticFieldLeak")
    public volatile static NetworkThread networkThread;


    @SuppressLint("WakelockTimeout")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        if (pm != null) {
            this.wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "My Tag");
            this.wakeLock.acquire();
        }

        settings_btn = (Button) findViewById(R.id.setting_btn);
        patient_add_remove_btn = (Button) findViewById(R.id.add_remove_btn);
        small_app_bar = (LinearLayout) findViewById(R.id.small_app_bar);
        back = (ImageView) findViewById(R.id.back_icon_iv);
        fields = new Fields(getApplicationContext());
        patient = new Patient(getApplicationContext());
        networkSettingEvents = new SettingEvents();
        patient_name_tv = (TextView) findViewById(R.id.patient_name_tv);
        big_top_bar = (LinearLayout) findViewById(R.id.top_container);
        big_top_bar.setBackgroundResource(fields.getColor());

        colors.add(R.color.color_one);
        colors.add(R.color.color_two);
        colors.add(R.color.color_thr);
        colors.add(R.color.color_for);
        colors.add(R.color.color_fiv);
        colors.add(R.color.color_six);
        colors.add(R.color.color_svn);
        colors.add(R.color.color_egt);
        colors.add(R.color.color_nin);
        colors.add(R.color.color_ten);
        colors.add(R.color.color_elv);

        settings_btn.setOnClickListener(this);
        patient_add_remove_btn.setOnClickListener(this);

        if ((patient.getPatient_name()).equals(getResources().getString(R.string.nopatient))) {
            patient_add_remove_btn.setBackgroundResource(R.drawable.add);
        } else {
            patient_add_remove_btn.setBackgroundResource(R.drawable.remove);
            patient_name_tv.setText(patient.getPatient_name());
        }

        small_app_bar.setOnClickListener(this);
        big_top_bar.setOnClickListener(this);

        big_top_bar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int index = colors.indexOf(fields.getColor());
                if (index == colors.size() - 1) {
                    fields.setColor(colors.get(0));
                } else {
                    fields.setColor(colors.get(index + 1));
                }
                big_top_bar.setBackgroundResource(fields.getColor());
                return true;
            }
        });

        scale_up = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scale_down = AnimationUtils.loadAnimation(this, R.anim.scale_down);
        little_slide_left = AnimationUtils.loadAnimation(this, R.anim.little_slide_left);
        little_slide_right = AnimationUtils.loadAnimation(this, R.anim.little_slide_right);

        MainFragment fragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment).commit();

        networkThread = new NetworkThread(getApplicationContext());
        networkThread.start();
        networkSettingEvents.editWard(new Ward(getApplicationContext()));
//        networkSettingEvents.editPatient(new Ward(getApplicationContext()).getUuid(),new Patient(getApplicationContext()));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_btn:
                settings_btn.setEnabled(false);
                patient_add_remove_btn.setEnabled(false);
                settings_btn.startAnimation(scale_down);
                patient_add_remove_btn.startAnimation(scale_down);
                small_app_bar.startAnimation(little_slide_right);
                back.startAnimation(scale_up);
                back.setVisibility(View.VISIBLE);
                Prefs.setCurrent_fragment(RunTimeSettings.SETTING_FRAGMENT);
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left).replace(R.id.main_layout, new MainSettings()).commit();
                break;
            case R.id.add_remove_btn:
                if (patient.getPatient_name().equals(getResources().getString(R.string.nopatient))) {
                    Bundle args = new Bundle();
                    args.putString("context", "add");
                    DialogFragment dialogFragment = new PopupDialog();
                    dialogFragment.setArguments(args);
                    dialogFragment.show(getSupportFragmentManager(), "add");
                } else {
                    patient_add_remove_btn.setBackgroundResource(R.drawable.add);
                    patient.setPatient_name(getResources().getString(R.string.nopatient));
                    patient_name_tv.setText(getResources().getString(R.string.nopatient));
                    networkSettingEvents.editPatient(new Ward(getApplicationContext()).getUuid(),patient);
                    ((Button) findViewById(R.id.alarm_btn)).setBackgroundResource(R.drawable.bell_off);
                    Prefs.setAlarm_settings(AlarmState.ALARM_OFF);
//                    networkSettingEvents.editPatient(new Ward(getApplicationContext()).getUuid(), patient);
                    Toast.makeText(getApplicationContext(), "Patient Removed", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.small_app_bar:
                if (Prefs.current_fragment == RunTimeSettings.SETTING_FRAGMENT) {
                    Prefs.setCurrent_fragment(RunTimeSettings.MAIN_FRAGMENT);
                    settings_btn.startAnimation(scale_up);
                    patient_add_remove_btn.startAnimation(scale_up);
                    small_app_bar.startAnimation(little_slide_left);
                    back.startAnimation(scale_down);
                    back.setVisibility(View.INVISIBLE);
                    settings_btn.setEnabled(true);
                    patient_add_remove_btn.setEnabled(true);
                    settings_btn.setVisibility(View.VISIBLE);
                    patient_add_remove_btn.setVisibility(View.VISIBLE);
                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_from_left, R.anim.slide_out_to_right).replace(R.id.main_layout, new MainFragment()).commit();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (Prefs.current_fragment == RunTimeSettings.SETTING_FRAGMENT) {
            Prefs.setCurrent_fragment(RunTimeSettings.MAIN_FRAGMENT);
            settings_btn.startAnimation(scale_up);
            patient_add_remove_btn.startAnimation(scale_up);
            small_app_bar.startAnimation(little_slide_left);
            back.startAnimation(scale_down);
            back.setVisibility(View.INVISIBLE);
            settings_btn.setEnabled(true);
            patient_add_remove_btn.setEnabled(true);
            settings_btn.setVisibility(View.VISIBLE);
            patient_add_remove_btn.setVisibility(View.VISIBLE);
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_from_left, R.anim.slide_out_to_right).replace(R.id.main_layout, new MainFragment()).commit();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void positiveClick(DialogFragment fragment, String context) {
        switch (context) {
            case "add":
                patient_add_remove_btn.setBackgroundResource(R.drawable.remove);
                patient_name_tv.setText(patient.getPatient_name());
//                networkSettingEvents.editPatient(new Ward(getApplicationContext()).getUuid(), patient);
                Toast.makeText(getApplicationContext(), "Patient Added", Toast.LENGTH_SHORT).show();
                break;
            case "name":
                patient_name_tv.setText(patient.getPatient_name());
                networkSettingEvents.editPatient(new Ward(getApplicationContext()).getUuid(), patient);
                break;
            case "registration":
                break;
            case "bed":

                break;
            case "ward":
                break;
            case "ip":
                break;
            case "port":
                break;
            case "about":
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

}
package dinodia.pwn.com.ncs_v5.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dinodia.pwn.com.ncs_v5.Fields;
import dinodia.pwn.com.ncs_v5.Models.Patient;
import dinodia.pwn.com.ncs_v5.NetworkControl.SettingEvents;
import dinodia.pwn.com.ncs_v5.PopupDialog;
import dinodia.pwn.com.ncs_v5.Prefs;
import dinodia.pwn.com.ncs_v5.R;
import dinodia.pwn.com.ncs_v5.RunTimeSettings;


public class MainSettings extends Fragment implements View.OnClickListener{
    private LinearLayout patient_name_ll,registration_no_ll,bed_no_ll,ward_name_ll,ip_address_ll,port_ll,about_ll;
    private CheckBox night_mode_chk;
    private DialogFragment dialogFragment;
    Fields fields;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.settings_layout,container,false);

        patient_name_ll=(LinearLayout) view.findViewById(R.id.patient_name_ll);
        registration_no_ll=(LinearLayout) view.findViewById(R.id.registration_ll);
        bed_no_ll=(LinearLayout) view.findViewById(R.id.bed_no_ll);
        ward_name_ll=(LinearLayout) view.findViewById(R.id.ward_name_ll);
        ip_address_ll=(LinearLayout) view.findViewById(R.id.ip_address_ll);
        port_ll=(LinearLayout) view.findViewById(R.id.port_ll);
        about_ll=(LinearLayout) view.findViewById(R.id.about_ll);
        night_mode_chk=(CheckBox) view.findViewById(R.id.night_mode_chk);
        fields = new Fields(getActivity().getApplicationContext());
        night_mode_chk.setChecked(fields.isNight_mode());

        patient_name_ll.setOnClickListener(this);
        registration_no_ll.setOnClickListener(this);
        bed_no_ll.setOnClickListener(this);
        ward_name_ll.setOnClickListener(this);
        ip_address_ll.setOnClickListener(this);
        port_ll.setOnClickListener(this);
        night_mode_chk.setOnClickListener(this);
        about_ll.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        Bundle args=new Bundle();
        switch (view.getId()){
            case R.id.patient_name_ll:
                if(!(new Patient(getActivity().getApplicationContext()).getPatient_name()).equals(getActivity().getResources().getString(R.string.nopatient))) {
                    args.putString("context","name");
                    dialogFragment=new PopupDialog();
                    dialogFragment.setArguments(args);
                    dialogFragment.show(getFragmentManager(),"name");
                }else{
                    Toast.makeText(getActivity().getApplicationContext(), "Please add a patient First", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.registration_ll:
                if(!(new Patient(getActivity().getApplicationContext()).getPatient_name()).equals(getActivity().getResources().getString(R.string.nopatient))) {
                    args.putString("context","registration");
                    dialogFragment=new PopupDialog();
                    dialogFragment.setArguments(args);
                    dialogFragment.show(getFragmentManager(),"registration");
                }else{
                    Toast.makeText(getActivity().getApplicationContext(), "Please add a patient First", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.night_mode_chk:
                if(night_mode_chk.isChecked()) {
                    Toast.makeText(getActivity().getApplicationContext(), "Night Mode On", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity().getApplicationContext(), "Night Mode Off", Toast.LENGTH_SHORT).show();
                }
                fields.setNight_mode(night_mode_chk.isChecked());
                break;
            case R.id.bed_no_ll:
                if((new Patient(getActivity().getApplicationContext()).getPatient_name()).equals(getActivity().getResources().getString(R.string.nopatient))) {
                    args.putString("context","bed");
                    dialogFragment=new PopupDialog();
                    dialogFragment.setArguments(args);
                    dialogFragment.show(getFragmentManager(),"bed");
                }else{
                    Toast.makeText(getActivity().getApplicationContext(), "Please remove patient First", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ward_name_ll:
                if((new Patient(getActivity().getApplicationContext()).getPatient_name()).equals(getActivity().getResources().getString(R.string.nopatient))) {
                    args.putString("context","ward");
                    dialogFragment=new PopupDialog();
                    dialogFragment.setArguments(args);
                    dialogFragment.show(getFragmentManager(),"ward");
                }else{
                    Toast.makeText(getActivity().getApplicationContext(), "Please remove patient First", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ip_address_ll:
                args.putString("context","ip");
                dialogFragment=new PopupDialog();
                dialogFragment.setArguments(args);
                dialogFragment.show(getFragmentManager(),"ip");
                break;
            case R.id.port_ll:
                args.putString("context","port");
                dialogFragment=new PopupDialog();
                dialogFragment.setArguments(args);
                dialogFragment.show(getFragmentManager(),"port");
                break;
            case R.id.about_ll:
                args.putString("context","about");
                dialogFragment=new PopupDialog();
                dialogFragment.setArguments(args);
                dialogFragment.show(getFragmentManager(),"about");
                break;
        }
    }
}

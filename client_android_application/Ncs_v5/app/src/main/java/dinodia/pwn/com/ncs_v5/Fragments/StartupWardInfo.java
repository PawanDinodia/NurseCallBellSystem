package dinodia.pwn.com.ncs_v5.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import dinodia.pwn.com.ncs_v5.Models.Ward;
import dinodia.pwn.com.ncs_v5.NetworkControl.SettingEvents;
import dinodia.pwn.com.ncs_v5.R;

public class StartupWardInfo extends Fragment{
    private EditText ward,bed;
    Ward wardInfo;
    Button next;
    ImageView ward_right_wrong,bed_right_wrong;
    private static boolean ward_status=false,bed_status=false;
    //SettingEvents event;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.startup_ward_info,container,false);
        ward=(EditText) view.findViewById(R.id.startup_ward_ed);
        bed=(EditText) view.findViewById(R.id.startup_bed_ed);
        next=(Button) view.findViewById(R.id.startup_next_btn);
        wardInfo=new Ward(getActivity().getApplicationContext());
        ward_right_wrong=(ImageView) view.findViewById(R.id.startup_ward_right_wrong);
        bed_right_wrong=(ImageView) view.findViewById(R.id.startup_bed_right_wrong);
        //event=new SettingEvents();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wardInfo.setWard_name(ward.getText().toString().trim().toUpperCase());
                wardInfo.setBed_no(Integer.parseInt(bed.getText().toString().trim().replace(".","")));
                //event.addBed(wardInfo);
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_from_right,R.anim.slide_out_to_left).replace(R.id.startup_container,new StartupNetworkInfo()).commit();
            }
        });
        ward.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals("")){
                    ward_status=true;
                    ward_right_wrong.setImageResource(R.drawable.right);
                }else{
                    ward_status=false;
                    ward_right_wrong.setImageResource(R.drawable.wrong);
                }
                checkStatus(ward_status,bed_status);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        bed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals("")){
                    bed_status=true;
                    bed_right_wrong.setImageResource(R.drawable.right);
                }else{
                    bed_status=false;
                    bed_right_wrong.setImageResource(R.drawable.wrong);
                }
                checkStatus(ward_status,bed_status);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });




        return view;
    }

    private void checkStatus(boolean ward_status,boolean bed_status){
        if(ward_status&&bed_status){
            next.setEnabled(true);
        }else {
            next.setEnabled(false);
        }
    }
}

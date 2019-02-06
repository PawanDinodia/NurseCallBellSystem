package dinodia.pwn.com.ncs_v5.Fragments;


import android.content.Intent;
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
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dinodia.pwn.com.ncs_v5.MainActivity;
import dinodia.pwn.com.ncs_v5.Models.NetworkInfo;
import dinodia.pwn.com.ncs_v5.Models.Ward;
import dinodia.pwn.com.ncs_v5.NetworkControl.SettingEvents;
import dinodia.pwn.com.ncs_v5.R;

public class StartupNetworkInfo extends Fragment {
    private EditText ip, port;
    NetworkInfo networkInfo;
    ImageView ip_right_wrong, port_right_wrong;
    Button done, back;
    private static boolean ip_status, port_status;
    //SettingEvents settingEvents;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.startup_network_info, container, false);
        ip = (EditText) view.findViewById(R.id.startup_ip_ed);
        port = (EditText) view.findViewById(R.id.startup_port_ed);
        done = (Button) view.findViewById(R.id.startup_done_btn);
        networkInfo=new NetworkInfo(getActivity().getApplicationContext());
        back = (Button) view.findViewById(R.id.startup_back_btn);
        ip_right_wrong = (ImageView) view.findViewById(R.id.startup_ip_right_wrong);
        port_right_wrong = (ImageView) view.findViewById(R.id.startup_port_right_wrong);
        //settingEvents =new SettingEvents();

        done.setEnabled(false);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_from_left, R.anim.slide_out_to_right).replace(R.id.startup_container, new StartupWardInfo()).commit();
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                networkInfo.setIp_address(ip.getText().toString().trim());
                networkInfo.setPort(Integer.parseInt(port.getText().toString().trim().replace(".","")));
              //  if(settingEvents.editNetworkInfo(networkInfo)){
                Intent i = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                startActivity(i);
                getActivity().finish();
//                }else {
//                    Toast.makeText(getActivity().getApplicationContext(),"Network Unreachable", Toast.LENGTH_SHORT).show();
//                }
            }
        });
        ip.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().equals("") && checkIp(charSequence.toString())) {
                    ip_right_wrong.setImageResource(R.drawable.right);
                    ip_status = true;
                } else {
                    ip_right_wrong.setImageResource(R.drawable.wrong);
                    done.setEnabled(false);
                    ip_status = false;
                }
                checkStatus(ip_status, port_status);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        port.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().equals("")) {
                    port_right_wrong.setImageResource(R.drawable.right);
                    port_status = true;
                } else {
                    port_right_wrong.setImageResource(R.drawable.wrong);
                    port_status = false;
                }
                checkStatus(ip_status, port_status);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        return view;
    }

    private static boolean checkIp(String ip) {
        boolean flag = false;
        String[] params = ip.split("[.]");

        Pattern pattern = Pattern.compile("[\\d{1,3}]");
        Pattern nan = Pattern.compile("\\D+");
        if (params.length == 4) {
            for (String param : params) {
                if (!nan.matcher(param).find()) {
                    Matcher matcher = pattern.matcher(param);
                    if (matcher.find()) {
                        if (Integer.parseInt(param) < 255) {
                            flag = true;
                        } else {
                            flag = false;
                            break;
                        }
                    } else {
                        flag = false;
                        break;
                    }

                } else {
                    flag = false;
                    break;
                }
            }
        } else {
            flag = false;
        }
        return flag;
    }

    private void checkStatus(boolean ip_status, boolean port_status) {
        if (ip_status && port_status) {
            done.setEnabled(true);
        } else {
            done.setEnabled(false);
        }
    }
}

package dinodia.pwn.com.ncs_v5;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dinodia.pwn.com.ncs_v5.Models.NetworkInfo;
import dinodia.pwn.com.ncs_v5.Models.Patient;
import dinodia.pwn.com.ncs_v5.Models.Ward;
import dinodia.pwn.com.ncs_v5.NetworkControl.SettingEvents;

public class PopupDialog extends DialogFragment implements View.OnClickListener {
    private EditText number, text;
    private Button okay;
    private ImageView text_checker, number_checker;
    private DialogEvents events;
    private DialogFragment dialogFragment = this;
    private String context = "";
    TextView dialog_text;
    private static boolean add_number = false, add_text = false;

    private Patient patient;
    private Ward ward;
    private NetworkInfo networkInfo;
    private SettingEvents networkSettingEvents;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.patient_add_alert, container);

        number = (EditText) view.findViewById(R.id.dialog_number_input);
        text = (EditText) view.findViewById(R.id.dialog_text_input);
        dialog_text = (TextView) view.findViewById(R.id.dialog_textview);
        TextView title = (TextView) view.findViewById(R.id.dialog_title);
        okay = (Button) view.findViewById(R.id.dialog_okay_btn);
        Button cancel = (Button) view.findViewById(R.id.dialog_cancle_btn);
        text_checker = (ImageView) view.findViewById(R.id.dialog_text_checker);
        number_checker = (ImageView) view.findViewById(R.id.dialog_number_checker);
        LinearLayout text_container = (LinearLayout) view.findViewById(R.id.dialog_text_container);
        LinearLayout number_container = (LinearLayout) view.findViewById(R.id.dialog_number_container);
        patient=new Patient(getActivity().getApplicationContext());
        ward=new Ward(getActivity().getApplicationContext());
        networkSettingEvents=new SettingEvents();
        networkInfo=new NetworkInfo(getActivity().getApplicationContext());

        okay.setOnClickListener(this);
        cancel.setOnClickListener(this);

        context = getArguments().getString("context");
        if (context != null && !context.equals("")) {
            switch (context) {
                case "name":
                    number_container.setVisibility(View.GONE);
                    text_container.setVisibility(View.VISIBLE);
                    dialog_text.setVisibility(View.GONE);
                    okay.setVisibility(View.VISIBLE);
                    cancel.setVisibility(View.VISIBLE);

                    title.setText(getResources().getString(R.string.patient_name));
                    if (patient.getPatient_name().equals(getActivity().getResources().getString(R.string.nopatient))) {
                        text.setHint("Enter Patient Name");
                    } else {
                        text.setHint(patient.getPatient_name());
                    }
                    okay.setEnabled(false);
                    break;
                case "registration":
                    number_container.setVisibility(View.GONE);
                    text_container.setVisibility(View.VISIBLE);
                    dialog_text.setVisibility(View.GONE);
                    okay.setVisibility(View.VISIBLE);
                    cancel.setVisibility(View.VISIBLE);

                    title.setText(getResources().getString(R.string.registration_no));
                    if (patient.getReg_no().equals(getActivity().getResources().getString(R.string.nopatient))) {
                        text.setHint("Enter Registration No.");
                    } else {
                        text.setHint(patient.getReg_no());
                    }
                    okay.setEnabled(false);
                    break;
                case "bed":
                    number_container.setVisibility(View.VISIBLE);
                    text_container.setVisibility(View.GONE);
                    dialog_text.setVisibility(View.GONE);
                    okay.setVisibility(View.VISIBLE);
                    cancel.setVisibility(View.VISIBLE);

                    title.setText(getResources().getString(R.string.bed_no));
                    if (ward.getBed_no() == 0) {
                        number.setHint("Enter Bed No.");
                    } else {
                        number.setHint(ward.getBed_no() + "");
                    }
                    okay.setEnabled(false);
                    break;
                case "ward":
                    number_container.setVisibility(View.GONE);
                    text_container.setVisibility(View.VISIBLE);
                    dialog_text.setVisibility(View.GONE);
                    okay.setVisibility(View.VISIBLE);
                    cancel.setVisibility(View.VISIBLE);

                    title.setText(getResources().getString(R.string.ward_name));
                    if (ward.getWard_name().equals(getActivity().getResources().getString(R.string.nopatient))) {
                        text.setHint("Enter Ward no.");
                    } else {
                        text.setHint(ward.getWard_name());
                    }
                    okay.setEnabled(false);
                    break;
                case "ip":
                    number_container.setVisibility(View.GONE);
                    text_container.setVisibility(View.VISIBLE);
                    dialog_text.setVisibility(View.GONE);
                    okay.setVisibility(View.VISIBLE);
                    cancel.setVisibility(View.VISIBLE);
                    title.setText(getResources().getString(R.string.ip_address));
                    if (networkInfo.getIp_address().equals(getActivity().getResources().getString(R.string.nopatient))) {
                        number.setHint("Enter new Ip");
                    } else {
                        text.setHint(networkInfo.getIp_address());
                    }
                    okay.setEnabled(false);
                    break;
                case "port":
                    number_container.setVisibility(View.VISIBLE);
                    text_container.setVisibility(View.GONE);
                    dialog_text.setVisibility(View.GONE);
                    okay.setVisibility(View.VISIBLE);
                    cancel.setVisibility(View.VISIBLE);

                    title.setText(getResources().getString(R.string.port_no));
                    if (networkInfo.getPort() == 0) {
                        number.setHint("Enter new port");
                    } else {
                        number.setHint(networkInfo.getPort() + "");
                    }
                    okay.setEnabled(false);
                    break;
                case "about":
                    number_container.setVisibility(View.GONE);
                    text_container.setVisibility(View.GONE);
                    dialog_text.setVisibility(View.VISIBLE);
                    okay.setVisibility(View.VISIBLE);
                    cancel.setVisibility(View.GONE);
                    title.setText(getResources().getString(R.string.about));
                    dialog_text.setText(Html.fromHtml(getResources().getString(R.string.about_app)));
                    break;
                case "add":
                    number_container.setVisibility(View.VISIBLE);
                    text_container.setVisibility(View.VISIBLE);
                    dialog_text.setVisibility(View.GONE);
                    okay.setVisibility(View.VISIBLE);
                    cancel.setVisibility(View.VISIBLE);
                    text.setHint("Enter Patient Name");
                    number.setHint("Enter Registration no.");
                    okay.setEnabled(false);
                    title.setText(getResources().getString(R.string.patient_info));
                    break;
                default:
                    break;
            }
        }

        number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                events.onNumberEditTextChange(dialogFragment, charSequence.toString(), context);
                switch (context) {
                    case "bed":
                        if (!charSequence.toString().equals("")) {
                            okay.setEnabled(true);
                            number_checker.setImageResource(R.drawable.right);
                        } else {
                            okay.setEnabled(false);
                            number_checker.setImageResource(R.drawable.wrong);
                        }
                        break;
                    case "port":
                        if (!charSequence.toString().equals("")) {
                            okay.setEnabled(true);
                            number_checker.setImageResource(R.drawable.right);
                        } else {
                            okay.setEnabled(false);
                            number_checker.setImageResource(R.drawable.wrong);
                        }
                        break;
                    case "ip":
                        if (!charSequence.toString().equals("") && checkIp(charSequence.toString())) {
                            okay.setEnabled(true);
                            text_checker.setImageResource(R.drawable.right);
//                            number_checker.setImageResource(R.drawable.right);
                        } else {
                            okay.setEnabled(false);
                            text_checker.setImageResource(R.drawable.wrong);
//                            number_checker.setImageResource(R.drawable.wrong);
                        }
                        break;
                    case "add":
                        if (!charSequence.toString().equals("")) {
                            add_number = true;
                            add_patient_cred_cheker();
                            number_checker.setImageResource(R.drawable.right);
                        } else {
                            add_number = false;
                            add_patient_cred_cheker();
                            number_checker.setImageResource(R.drawable.wrong);
                        }
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                events.onTextEditTextChange(dialogFragment, charSequence.toString(), context);

                switch (context) {
                    case "name":
                        if (!charSequence.toString().equals("")) {
                            okay.setEnabled(true);
                            text_checker.setImageResource(R.drawable.right);
                        } else {
                            okay.setEnabled(false);
                            text_checker.setImageResource(R.drawable.wrong);
                        }
                        break;
                    case "registration":
                        if (!charSequence.toString().equals("")) {
                            okay.setEnabled(true);
                            text_checker.setImageResource(R.drawable.right);
                        } else {
                            okay.setEnabled(false);
                            text_checker.setImageResource(R.drawable.wrong);
                        }
                        break;
                    case "ward":
                        if (!charSequence.toString().equals("")) {
                            okay.setEnabled(true);
                            text_checker.setImageResource(R.drawable.right);
                        } else {
                            okay.setEnabled(false);
                            text_checker.setImageResource(R.drawable.wrong);
                        }
                        break;
                    case "ip":
                        if (!charSequence.toString().equals("") && checkIp(charSequence.toString())) {
                            okay.setEnabled(true);
                            text_checker.setImageResource(R.drawable.right);
                        } else {
                            okay.setEnabled(false);
                            text_checker.setImageResource(R.drawable.wrong);
                        }
                        break;
                    case "add":
                        if (!charSequence.toString().equals("")) {
                            add_text = true;
                            add_patient_cred_cheker();
                            text_checker.setImageResource(R.drawable.right);
                        } else {
                            add_text = false;
                            add_patient_cred_cheker();
                            text_checker.setImageResource(R.drawable.wrong);
                        }
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_okay_btn:
                if(ward.isConnected()) {
                    switch (context) {
                        case "name":
                            patient.setPatient_name(text.getText().toString().trim().toUpperCase());
                            networkSettingEvents.editPatient(ward.getUuid(), patient);
                            break;
                        case "registration":
                            patient.setReg_no(text.getText().toString().trim());
                            networkSettingEvents.editPatient(ward.getUuid(), patient);
                            break;
                        case "bed":
                            ward.setBed_no(Integer.parseInt(number.getText().toString().trim()));
                            networkSettingEvents.editWard(ward);
                            break;
                        case "ward":
                            ward.setWard_name(text.getText().toString().trim().toUpperCase());
                            //networkSettingEvents.editWard(ward);
                            break;
                        case "ip":
                            networkInfo.setIp_address(text.getText().toString().trim());
                            networkSettingEvents.editNetworkInfo(networkInfo);
                            break;
                        case "port":
                            networkInfo.setPort(Integer.parseInt(number.getText().toString().trim().replace(".", "")));
                            networkSettingEvents.editNetworkInfo(networkInfo);
                            break;
                        case "about":
                            break;
                        case "add":
                            patient.setPatient_name(text.getText().toString().trim().toUpperCase());
                            patient.setReg_no(number.getText().toString().trim());
                            networkSettingEvents.addPatient(ward.getUuid(), patient);
                            break;
                        default:
                            break;
                    }
                    events.positiveClick(dialogFragment, context);
                    this.dismiss();
                }else{
                    Toast.makeText(getActivity().getApplicationContext(),"Not connected to server", Toast.LENGTH_SHORT).show();
                    events.negativeClick(dialogFragment, context);
                    this.dismiss();
                }
                break;
            case R.id.dialog_cancle_btn:
                events.negativeClick(dialogFragment, context);
                this.dismiss();
                break;
            default:
                break;
        }
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

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            events = (DialogEvents) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement NoticeDialogListener");
        }

    }

    private void add_patient_cred_cheker() {
        if (add_number && add_text) {
            okay.setEnabled(true);
        } else {
            okay.setEnabled(false);
        }
    }

    public interface DialogEvents {
        void positiveClick(DialogFragment fragment, String context);

        void negativeClick(DialogFragment fragment, String context);

        void onNumberEditTextChange(DialogFragment fragment, String newText, String context);

        void onTextEditTextChange(DialogFragment fragment, String newText, String context);
    }
}

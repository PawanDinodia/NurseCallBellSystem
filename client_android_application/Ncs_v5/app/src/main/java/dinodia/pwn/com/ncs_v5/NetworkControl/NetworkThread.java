package dinodia.pwn.com.ncs_v5.NetworkControl;

import android.content.Context;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import dinodia.pwn.com.ncs_v5.Models.AlarmState;
import dinodia.pwn.com.ncs_v5.Models.NetworkInfo;
import dinodia.pwn.com.ncs_v5.Models.Patient;
import dinodia.pwn.com.ncs_v5.Models.Ward;
import dinodia.pwn.com.ncs_v5.R;

public class NetworkThread extends Thread implements EventsListener {
    private Socket socket;
    private Ward ward;
    private Context context;
    private NetworkInfo networkInfo;
    private String LOG_TAG="network";

    public NetworkThread(Context context) {
        networkInfo = new NetworkInfo(context);
        ward = new Ward(context);
        this.context = context;
        Log.d(LOG_TAG, "Starting network thread");
    }

    @Override
    public void run() {
        try {
            socket = new Socket(networkInfo.getIp_address(), networkInfo.getPort());
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(LOG_TAG, getClass().getCanonicalName()+": Error opening socket");
        }

        //sendMessage("_ad_-" + ward.getBed_no() + "-");

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!ward.getUuid().equals(context.getResources().getString(R.string.nopatient))) {
                    sendMessage("_cn_-" + ward.getUuid() + "-");
                } else {
                    sendMessage("_ad_-" + ward.getBed_no() + "-");
                }
            }
        }, 1000, 5000);
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                ward.setConnected(false);
            }
        }, 1000, 10000);
        Thread receive = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted()) {
                    String cmd = receiveMessage();
                    analyseCommand(cmd);
                }
            }
        });
        receive.start();
    }

    @Override
    public void alarmChange(AlarmState alarmState, String clientId) {
        String cmd = "";
        switch (alarmState) {
            case ALARM_OFF:
                cmd = "_ac_-" + clientId + "-0-";
                break;
            case ALARM_ONN:
                cmd = "_ac_-" + clientId + "-1-";
                break;
        }
        sendMessage(cmd);
        Log.d(LOG_TAG, "NetworkThread got the alarm change signal");
    }

    @Override
    public void editWard(Ward ward) {
        String cmd = "";
        cmd = "_ed_-" + ward.getUuid()+ "-"+ ward.getBed_no()+"-";
        sendMessage(cmd);
        Log.d("events", "NetworkThread got the editWard signal");
    }

    @Override
    public void addPatient(String client_id, Patient patient) {
        String cmd = "";
        cmd = "_cp_-" +client_id+ "-"+patient.getPatient_name()+"-"+patient.getReg_no()+"-";
        sendMessage(cmd);
        Log.d(LOG_TAG, "NetworkThread got the add patient signal");
    }

    @Override
    public void editPatient(String client_id, Patient patient) {
        String cmd = "";
        cmd = "_cp_-" +client_id+ "-"+patient.getPatient_name()+"-"+patient.getReg_no()+"-";
        sendMessage(cmd);
        Log.d(LOG_TAG, "NetworkThread got the editPatient signal");
    }

    @Override
    public void addBed(Ward ward) {
        Log.d("events", "NetworkThread got the add Bed signal");
    }

    @Override
    public boolean editNetworkInfo(NetworkInfo networkInfo) {
        Log.d(LOG_TAG, "NetworkThread got the network setting change!");
        return true;
    }

    private synchronized void sendMessage(final String msg) {
        Thread sendThread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                    switch (msg.substring(0, 4)) {
                        case "_cn_":
                            Log.d(LOG_TAG, "msg sent:>>>>>>>>>> " + msg);
                            break;
                        default:
                            Log.d(LOG_TAG, "msg sent:************ " + msg);
                            break;
                    }
                    dos.writeUTF(msg);
                    dos.flush();
                    Thread.currentThread().interrupt();
                    //socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        socket = new Socket(networkInfo.getIp_address(), networkInfo.getPort());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        Log.d(LOG_TAG, getClass().getCanonicalName()+": Error opening socket");
                    }
                    Log.d(LOG_TAG, "Error writing socket");
                }
            }
        });
        sendThread.start();
    }

    private String receiveMessage() {
        DataInputStream dis;
        String str = "";
        try {
            dis = new DataInputStream(socket.getInputStream());
            str = (String) dis.readUTF();
            // Log.d("events", "response got:> " + str + " with length:>" + str.length());
        } catch (Exception e) {
           // e.printStackTrace();
        }
        return str;
    }

    private void analyseCommand(String cmd) {
        if (cmd.length() > 3) {
            switch (cmd.substring(0, 4)) {
                case "_id_":
                    String[] params = cmd.split("-");
                    ward.setUuid(params[1]);
                    break;
                case "_cn_":
                    ward.setConnected(true);
                    break;
                default:
                    break;
            }
        }
    }
}

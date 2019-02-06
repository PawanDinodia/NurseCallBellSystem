package NetworkHandler;

import AlarmHandler.AlarmState;
import AlarmHandler.CollectiveBedHandler;
import FrameWork.BedTileFrameWork.BedModel;
import FrameWork.Main;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.DataOutputStream;
import java.lang.reflect.Type;
import java.net.Socket;

class Commander {
    private Socket socket;
    private CollectiveBedHandler collectiveBedHandler=new CollectiveBedHandler();
    Commander(Socket socket){
        this.socket=socket;
    }
    void getCommand(String cmd){
        boolean flag;
        try {
            String[] params;
            switch (cmd.substring(0,4)){
                case "_cn_":
                    params=cmd.split("-");
                    if(CollectiveBedHandler.models.get(CollectiveBedHandler.searchBed(params[1])).getAlarmstate()== AlarmState.ALARM_OFF) {
                        CollectiveBedHandler.updateState(params[1], 1);
                        Main.updateTiles();
                    }
                    sendMessage("_cn_");
                    break;
                case "_ad_":
                    //_ad_-bedno-
                    params=cmd.split("-");
                    BedModel model=new BedModel();
                    model.setBed_no(Integer.parseInt(params[1]));
                    System.out.println(params[1]);
                    model.setIp(socket.getInetAddress().toString());
                    model.setIp(socket.getPort()+"");
                    String client_id=collectiveBedHandler.addBed(model);
                    sendMessage("_id_-"+client_id+"-");
                    break;
                case "_ac_":
                    // _ac_-client_id-0or1-
                    params=cmd.split("-");
                    flag = collectiveBedHandler.alarmChange(params[1],params[2]);
                    if(flag){
                       sendMessage("Successful");
                   }else{
                       sendMessage("failed");
                   }
                    break;
                case "_ed_":
                    //_ed_-client_id-new_bed_no-
                    params=cmd.split("-");
                    model=new BedModel();
                    model.setClient_id(params[1]);
                    model.setBed_no(Integer.parseInt(params[2]));
                    model.setIp(socket.getInetAddress().toString());
                    model.setIp(socket.getPort()+"");
                    flag=collectiveBedHandler.editBed(model);
                    if(flag) {
                        sendMessage("_ed_-1-"); //successful
                    }else{
                        sendMessage("_ed_-0-"); //unsuccessful
                    }
                    break;
                case "_cp_":
                    //_ap_-client_id-patient_name-reg_no-  **change patient**
                    params=cmd.split("-");
                    model=new BedModel();
                    model.setClient_id(params[1]);
                    model.setPatientName(params[2]);
                    model.setRegNo(params[3]);
                    model.setIp(socket.getInetAddress().toString());
                    model.setIp(socket.getPort()+"");
                    flag=collectiveBedHandler.changePatient(model);
                    if(flag) {
                        sendMessage("_cp_-1-"); //successful
                    }else{
                        sendMessage("_cp_-0-"); //unsuccessful
                    }
                    break;

                case "_dl_":
                    //_dl_-client_id-
                    params=cmd.split("-");
                    flag=collectiveBedHandler.removeBed(params[1]);
                    if(flag){
                        sendMessage("Successful");
                    }else{
                        sendMessage("failed");
                    }
                    sendMessage("Delete Bed");
                    break;
                case "_qt_":
                    break;
                default:
                    sendMessage("_cn_");
                    break;

            }
        }catch (Exception e){
            e.getStackTrace();
        }
    }
    private void sendMessage(String msg){
        try {
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(msg);
            dos.flush();
           // dos.close();
        }catch (Exception e) {
            e.getStackTrace();
        }
    }
}

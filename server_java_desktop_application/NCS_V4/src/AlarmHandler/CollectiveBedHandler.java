package AlarmHandler;

import FrameWork.BedTileFrameWork.BedModel;
import FrameWork.BedTileFrameWork.BedSort;
import FrameWork.Main;
import PermanentDataHandler.SqliteHandler;
import ncs_v4.Logs.GenralLogs;
import ncs_v4.Logs.LogLevel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CollectiveBedHandler {

    public static List<BedModel> models=new ArrayList<>();

    public String addBed(BedModel model){
        String uuid="0";
        String old_uuid=searchBedByBedNo(model.getBed_no());


        if(old_uuid.equals("0")){
            uuid = UUID.randomUUID().toString().replace("-","");
            model.setClient_id(uuid);
            models.add(model);
            SqliteHandler.insertData(model);
            sortBeds();
            Main.updateTiles();
            GenralLogs.glog(this.getClass().getCanonicalName(),"Bed no."+model.getBed_no()+" is added with client id:-"+model.getClient_id(), LogLevel.HIGH);
        }else{
            uuid=old_uuid+"-plese don't use this information for wrong purpose!";
            GenralLogs.glog(this.getClass().getCanonicalName(),"Bed no."+model.getBed_no()+" requests for duplicate client_id", LogLevel.HIGH);
        }
        return uuid;
    }
    public boolean removeBed(String client_id){
        boolean flag=false;
       int index=searchBed(client_id);
       if(index!=999){
           models.remove(models.get(index));
           SqliteHandler.deleteData(models.get(index).getClient_id());
           flag =true;
           GenralLogs.glog(this.getClass().getCanonicalName(),"Bed no."+models.get(index).getBed_no()+" is removed successfully", LogLevel.HIGH);
       }
        sortBeds();
        Main.updateTiles();
       return flag;
    }
    public boolean alarmChange(String client_id,String state){
        AlarmState alarmState=AlarmState.ALARM_OFF;
        if(state.equals("1")){
            alarmState=AlarmState.ALARM_ON;
        }
        boolean flag=false;
        int index=searchBed(client_id);
        if(index!=999) {
            flag=true;
            models.get(index).setAlarmState(alarmState);
            GenralLogs.glog(getClass().getCanonicalName(),"alarm change request for "+models.get(index).getBed_no()+" "+models.get(index).getAlarmstate().toString(),LogLevel.HIGH);
        }
        checkAlarmChange();
        Main.updateTiles();
        return flag;
    }
    public static int searchBed(String client_id){
        int index=999;
        for(BedModel bedModel:models){
            if (bedModel.getClient_id().equals(client_id)) {
                index=models.indexOf(bedModel);
            }
        }
            return index;
    }
    private String searchBedByBedNo(int bedNo){
        String client_id="0";
        for(BedModel model:models) {
        if(model.getBed_no()==bedNo){
            client_id=model.getClient_id();
        }
        }
        return client_id;
    }

    private void checkAlarmChange(){
        int alarm=0;
        for (BedModel model : models) {
            if (model.getAlarmstate() == AlarmState.ALARM_ON) {
                alarm = 1;
            }
        }
        if(alarm==1){
          AlarmControl.play();
        }
        else{
           AlarmControl.stop();
        }
    }

    private void sortBeds(){
        models.sort(new BedSort());
    }


    public static void updateState(String client_id, int state){
        switch (state){
            case 0:
                models.get(searchBed(client_id)).setConnected(false);
                break;
            case 1:
                models.get(searchBed(client_id)).setConnected(true);
                break;
        }
        Main.updateTiles();
    }

    public boolean editBed(BedModel bedModel){
        boolean flag=false;
        int index=searchBed(bedModel.getClient_id());
         if(index!=999) {
             GenralLogs.glog(getClass().getCanonicalName(),"Bed edited:-----> ",LogLevel.HIGH);
             GenralLogs.glog(getClass().getCanonicalName(),"Old bed no:-----> "+models.get(index).getBed_no(),LogLevel.HIGH);
             GenralLogs.glog(getClass().getCanonicalName(),"New bed no:-----> "+bedModel.getBed_no(),LogLevel.HIGH);
             flag=true;
            models.get(index).setBed_no(bedModel.getBed_no());
             SqliteHandler.upadateData(models.get(index));
        }
        sortBeds();
        Main.updateTiles();
        return flag;
    }

    public boolean changePatient(BedModel bedModel){
        boolean flag=false;
        int index=searchBed(bedModel.getClient_id());
        if(index!=999) {
            GenralLogs.glog(getClass().getCanonicalName(),"Patient Change:-----> ",LogLevel.HIGH);
            GenralLogs.glog(getClass().getCanonicalName(),"Old Patient name:-----> "+models.get(index).getPatientName(),LogLevel.HIGH);
            GenralLogs.glog(getClass().getCanonicalName(),"New Patient name:-----> "+bedModel.getPatientName(),LogLevel.HIGH);
            GenralLogs.glog(getClass().getCanonicalName(),"Old RegNo name:-----> "+models.get(index).getRegNo(),LogLevel.HIGH);
            GenralLogs.glog(getClass().getCanonicalName(),"New RegNo name:-----> "+bedModel.getRegNo(),LogLevel.HIGH);
            flag=true;
            models.get(index).setPatientName(bedModel.getPatientName());
            models.get(index).setRegNo(bedModel.getRegNo());
            SqliteHandler.upadateData(models.get(index));
        }
        Main.updateTiles();
        return flag;
    }

}

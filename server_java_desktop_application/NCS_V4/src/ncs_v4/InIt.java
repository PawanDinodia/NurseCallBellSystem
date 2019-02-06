package ncs_v4;

import java.io.File;

import PermanentDataHandler.SqliteHandler;
import ncs_v4.Files.FilePaths;
import ncs_v4.Logs.GenralLogs;
import ncs_v4.Logs.LogLevel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * 1.check file tree
 */

class InIt {
    void init(){
    init_file_tree();
    init_sql();
    }

      private void init_file_tree(){
        GenralLogs.glog(this.getClass().getCanonicalName(), "Checking file tree",LogLevel.MEDIUM);
          
        boolean file_tree_repair_flag=false;
        File root=new File(FilePaths.root);
        File database_file=new File(FilePaths.database_file);
        File genral_logs=new File(FilePaths.genral_logs);
        File bed_log_files=new File(FilePaths.bed_log_files);
        File special_logs=new File(FilePaths.special_logs);
        
        if(!root.exists()){
            file_tree_repair_flag=root.mkdir();
            GenralLogs.glog(this.getClass().getCanonicalName(), "Root folder created Successfully",LogLevel.HIGH);
        }else{
             GenralLogs.glog(this.getClass().getCanonicalName(), "Root folder Exists",LogLevel.HIGH);
        }
        if(!database_file.exists()){
            file_tree_repair_flag=database_file.mkdir()||file_tree_repair_flag;
            GenralLogs.glog(this.getClass().getCanonicalName(), "DataBase folder created Successfully",LogLevel.HIGH);
        }else{
             GenralLogs.glog(this.getClass().getCanonicalName(), "DataBase folder Exists",LogLevel.HIGH);
        }
        if(!genral_logs.exists()){
            file_tree_repair_flag=genral_logs.mkdir()||file_tree_repair_flag;
            GenralLogs.glog(this.getClass().getCanonicalName(), "Genral Log folder created Successfully",LogLevel.HIGH);
        }else{
             GenralLogs.glog(this.getClass().getCanonicalName(), "Genral Log folder Exists",LogLevel.HIGH);
        }
        if(!bed_log_files.exists()){
            file_tree_repair_flag=bed_log_files.mkdir()||file_tree_repair_flag;
            GenralLogs.glog(this.getClass().getCanonicalName(), "Bed log folder created Successfully",LogLevel.HIGH);
        }else{
             GenralLogs.glog(this.getClass().getCanonicalName(), "Bed log folder Exists",LogLevel.HIGH);
        }
        if(!special_logs.exists()){
            file_tree_repair_flag=special_logs.mkdir()||file_tree_repair_flag;
            GenralLogs.glog(this.getClass().getCanonicalName(), "Special log folder created Successfully",LogLevel.HIGH);
        }else{
             GenralLogs.glog(this.getClass().getCanonicalName(), "Special log folder Exists",LogLevel.HIGH);
        }
        
        if(file_tree_repair_flag){
            GenralLogs.glog(this.getClass().getCanonicalName(), "File tree checked and repaired",LogLevel.MEDIUM);
        } 
        else{
            GenralLogs.glog(this.getClass().getCanonicalName(), "File tree Ok",LogLevel.MEDIUM);
        }
        
      }
      
      
        private void init_sql(){
        final String DB_NAME="nsc_db.sqlite";
        final String TABLE_CLIENT="client";
        final String TABLE_PREF="pref";
        
        String create_table_client="CREATE TABLE if not exists "+TABLE_CLIENT+" " +
                                "(c_id varchar(32) Primary key ,\n" +
                                "p_id varchar(30) not null,\n" +
                                "bedno int(5) unique not null,\n" +
                                "name varchar(30) not null);";
        /*String create_table_pref="create table if not exists "+TABLE_PREF+" (\n" +
                                "ward_name varchar(30) not null,\n" +
                                "no_of_bed int(999) not null default 25,\n" +
                                "frame_mode varchar(30) check (frame_mode in ('stric','mulitpurpose')),\n" +
                                "theme int(2),\n" +
                                "alarm_vol int(2),\n" +
                                "auto_night_mode int(2) check (auto_night_mode in (0,1)),\n" +
                                "auto_night_mode_from varchar(30),\n" +
                                "auto_night_mode_to varchar(30));";*/
        try {
            Connection conn=DriverManager.getConnection("jdbc:sqlite:"+FilePaths.database_file+"\\"+DB_NAME);
            Statement stat=conn.createStatement();
            stat.execute(create_table_client);
            //stat.execute(create_table_pref);
            stat.close();
            conn.close();
            SqliteHandler.loadData();
             GenralLogs.glog(this.getClass().getCanonicalName(),"Database Checked", LogLevel.MEDIUM);
        } catch (SQLException ex) {
            GenralLogs.glog(this.getClass().getCanonicalName(),"Error connection database:"+ex.getMessage(), LogLevel.MEDIUM);
        }
        }
      
}
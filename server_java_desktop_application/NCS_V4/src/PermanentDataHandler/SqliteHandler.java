package PermanentDataHandler;

import AlarmHandler.CollectiveBedHandler;
import FrameWork.BedTileFrameWork.BedModel;
import ncs_v4.Files.FilePaths;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteHandler {
    private static Connection conn;
    private static String DB_NAME = "nsc_db.sqlite";
    private static final String TABLE_CLIENT="client";
    private static final String TABLE_PREF="pref";

    public SqliteHandler(){
        try {
            conn= DriverManager.getConnection("jdbc:sqlite:"+ FilePaths.database_file+"\\"+ DB_NAME);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void insertData(BedModel bedModel){
        String query="insert into "+TABLE_CLIENT+" values(?,?,?,?);";
        try {
            conn= DriverManager.getConnection("jdbc:sqlite:"+ FilePaths.database_file+"\\"+ DB_NAME);
            PreparedStatement stmt=conn.prepareStatement(query);
            stmt.setString(1,bedModel.getClient_id());
            stmt.setString(2,bedModel.getRegNo());
            stmt.setInt(3,bedModel.getBed_no());
            stmt.setString(4,bedModel.getPatientName());
            stmt.executeUpdate();
            stmt.close();
            closeDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void upadateData(BedModel bedModel){
        String query="update "+TABLE_CLIENT+" set p_id=?,bedno=?,name=? where c_id=?;";
        try {
            conn= DriverManager.getConnection("jdbc:sqlite:"+ FilePaths.database_file+"\\"+ DB_NAME);
            PreparedStatement stmt=conn.prepareStatement(query);
            stmt.setString(4,bedModel.getClient_id());
            stmt.setString(1,bedModel.getRegNo());
            stmt.setInt(2,bedModel.getBed_no());
            stmt.setString(3,bedModel.getPatientName());
            stmt.executeUpdate();
            stmt.close();
            closeDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static void deleteData(String client_id){
        String query="delete from "+TABLE_CLIENT+" where c_id=?";
        try {
            conn= DriverManager.getConnection("jdbc:sqlite:"+ FilePaths.database_file+"\\"+ DB_NAME);
            PreparedStatement stmt=conn.prepareStatement(query);
            stmt.setString(1,client_id);
            stmt.executeUpdate();
            stmt.close();
            closeDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void loadData(){
        List<BedModel> models=new ArrayList<>();
        String query="select * from "+ TABLE_CLIENT+" ;";
        try {
            conn= DriverManager.getConnection("jdbc:sqlite:"+ FilePaths.database_file+"\\"+ DB_NAME);
            Statement stmt=conn.createStatement();
            ResultSet rs=stmt.executeQuery(query);
                while (rs.next()) {
                    BedModel model = new BedModel();
                    model.setClient_id(rs.getString("c_id"));
                    model.setRegNo(rs.getString("p_id"));
                    model.setPatientName(rs.getString("name"));
                    model.setBed_no(rs.getInt("bedno"));
                    models.add(model);
                }
                CollectiveBedHandler.models = models;
            stmt.close();
            closeDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void closeDatabase(){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




}

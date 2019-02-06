package FrameWork.BedTileFrameWork;

import AlarmHandler.AlarmState;

import javax.swing.*;
import java.awt.*;

public class BedTile extends javax.swing.JPanel {

    private JButton bed;
    private JButton hum;
    private JLabel name;
    private JButton temp;

    public BedTile() {
        initComponents();
    }

    public BedTile(BedModel bedInfo) {
        initComponents();
        this.name.setText(bedInfo.getPatientName());
        if ((bedInfo.getBed_no() + "").length() == 1) {
            this.bed.setText("BedNo:00" + bedInfo.getBed_no());
        } else if ((bedInfo.getBed_no() + "").length() == 2) {
            this.bed.setText("BedNo:0" + bedInfo.getBed_no());
        } else {
            this.bed.setText("BedNo:" + bedInfo.getBed_no());
        }

        if ((bedInfo.getHumidity() + "").length() == 1) {
            this.hum.setText("H:00" + bedInfo.getHumidity());
        } else if ((bedInfo.getHumidity() + "").length() == 2) {
            this.hum.setText("H:0" + bedInfo.getHumidity());
        } else {
            this.hum.setText("H:" + bedInfo.getHumidity());
        }

        if ((bedInfo.getTemperature() + "").length() == 1) {
            this.temp.setText("T:00" + bedInfo.getTemperature());
        } else if ((bedInfo.getTemperature() + "").length() == 2) {
            this.temp.setText("T:0" + bedInfo.getTemperature());
        } else {
            this.temp.setText("T:" + bedInfo.getTemperature());
        }
        setBorder(javax.swing.BorderFactory.createStrokeBorder(new BasicStroke(2.0f), new Color(151, 189, 138)));

        this.changeState(bedInfo.isConnected(), bedInfo.getAlarmstate());
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        bed = new JButton();
        JPanel sensorContainerPanel = new JPanel();
        temp = new JButton();
        hum = new JButton();
        name = new JLabel();
        JButton reset = new JButton();


        setBackground(new java.awt.Color(255, 255, 255));
        //setBorder(javax.swing.BorderFactory.createLineBorder(new Color(255, 152, 0)));
        setBorder(javax.swing.BorderFactory.createStrokeBorder(new BasicStroke(2.0f), new Color(255, 201, 123)));

        bed.setBackground(new java.awt.Color(255, 255, 255));
        bed.setFont(new java.awt.Font("Comic Sans MS", Font.BOLD, 18));
        bed.setForeground(new java.awt.Color(48, 87, 170));
        bed.setText("BedNo:123");
        bed.setOpaque(true);
        name.setFont(new java.awt.Font("Comic Sans MS", Font.BOLD, 14));

        sensorContainerPanel.setBackground(new java.awt.Color(255, 255, 255));

        temp.setText("T:123");
        hum.setText("H:123");

        javax.swing.GroupLayout sensorContainerLayout = new javax.swing.GroupLayout(sensorContainerPanel);
        sensorContainerPanel.setLayout(sensorContainerLayout);
        sensorContainerLayout.setHorizontalGroup(
                sensorContainerLayout.createSequentialGroup().addGap(9)
                        .addComponent(temp).addGap(12)
                        .addComponent(hum).addGap(9)
        );
        sensorContainerLayout.setVerticalGroup(
                sensorContainerLayout.createParallelGroup()
                        .addGroup(sensorContainerLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(sensorContainerLayout.createParallelGroup()
                                        .addComponent(temp)
                                        .addComponent(hum))
                                .addContainerGap()
                        )
        );

        name.setFont(new java.awt.Font("Comic Sans MS", Font.BOLD, 11)); // NOI18N
        name.setText("Patient name");

        reset.setText("Reset");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(bed)
                        .addGroup(layout.createSequentialGroup().addGap(9).addComponent(name).addGap(9).addComponent(reset).addGap(9))
                        .addComponent(sensorContainerPanel)
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGap(5)
                        .addComponent(bed).addGap(5)
                        .addGroup(layout.createParallelGroup().addGap(5).addComponent(name).addGap(5).addComponent(reset)).addGap(5)
                        .addComponent(sensorContainerPanel)

        );
    }

    private void changeState(boolean connected, AlarmState alarmState) {
//        this.bedModel.setConnected(connected);
//        this.bedModel.setAlarmState(alarmState);

        if (connected && alarmState == AlarmState.ALARM_OFF) {
            setBorder(javax.swing.BorderFactory.createStrokeBorder(new BasicStroke(2.0f), Color.green));
            setBackground(new java.awt.Color(185, 211, 177));
            name.setForeground(new java.awt.Color(255,255,255));
        } else if (alarmState == AlarmState.ALARM_ON) {
            setBorder(javax.swing.BorderFactory.createStrokeBorder(new BasicStroke(2.0f), Color.red));
            setBackground(new java.awt.Color(231, 163, 161));
            name.setForeground(new java.awt.Color(255,255,255));
        }else{
            //setBorder(javax.swing.BorderFactory.createStrokeBorder(new BasicStroke(2.0f), Color.green));
            setBackground(new java.awt.Color(255,255,255));
            name.setForeground(new java.awt.Color(0,0,0));
        }
    }

    //}else{
//        setBackground(new java.awt.Color(255,255,255));
//        }
//
//        }
    public void updateTile(BedModel bedModel) {
        this.name.setText(bedModel.getPatientName());
        if ((bedModel.getBed_no() + "").length() == 1) {
            this.bed.setText("BedNo:00" + bedModel.getBed_no());
        } else if ((bedModel.getBed_no() + "").length() == 2) {
            this.bed.setText("BedNo:0" + bedModel.getBed_no());
        } else {
            this.bed.setText("BedNo:" + bedModel.getBed_no());
        }

        if ((bedModel.getHumidity() + "").length() == 1) {
            this.hum.setText("H:00" + bedModel.getHumidity());
        } else if ((bedModel.getHumidity() + "").length() == 2) {
            this.hum.setText("H:0" + bedModel.getHumidity());
        } else {
            this.hum.setText("H:" + bedModel.getHumidity());
        }

        if ((bedModel.getTemperature() + "").length() == 1) {
            this.temp.setText("T:00" + bedModel.getTemperature());
        } else if ((bedModel.getTemperature() + "").length() == 2) {
            this.temp.setText("T:0" + bedModel.getTemperature());
        } else {
            this.temp.setText("T:" + bedModel.getTemperature());
        }

        changeState(bedModel.isConnected(), bedModel.getAlarmstate());
    }

}
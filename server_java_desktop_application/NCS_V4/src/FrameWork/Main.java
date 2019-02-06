package FrameWork;

import java.awt.*;
import javax.swing.*;

import AlarmHandler.CollectiveBedHandler;
import FrameWork.BedTileFrameWork.BedModel;
import FrameWork.BedTileFrameWork.BedTile;

public class Main extends javax.swing.JFrame {
    private static GroupLayout root;
    private static Container frame;

    private static int num_bed=35;
    private static int num_panels=6;

    private static BedTile[] tiles=new BedTile[num_bed];
    private static JPanel[] panels=new JPanel[num_panels];

    public Main() {
        frame=getContentPane();
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        setSize(width, height);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        root = new GroupLayout(getContentPane());
        getContentPane().setLayout(root);

        createPanels();
        setUndecorated(true);
    }

    private void createPanels(){
        FlowLayout[] layouts=new FlowLayout[num_panels];
        for(int i=0;i<num_panels;i++){
            panels[i]=new JPanel();
            layouts[i]=new FlowLayout();
            panels[i].setLayout(layouts[i]);
        }
        panels[0].setBackground(new java.awt.Color(48, 52, 66));


        root.setHorizontalGroup(root.createParallelGroup()
                .addComponent(panels[0])
                .addComponent(panels[1])
                .addComponent(panels[2])
                .addComponent(panels[3])
                .addComponent(panels[4])
                .addComponent(panels[5])
        );
        root.setVerticalGroup(root.createSequentialGroup()
                .addComponent(panels[0])
                .addComponent(panels[1])
                .addComponent(panels[2])
                .addComponent(panels[3])
                .addComponent(panels[4])
                .addComponent(panels[5])
        );

        JLabel ward_name=new JLabel("Ward Name");
        ward_name.setForeground(new java.awt.Color(255, 152, 0));
        ward_name.setFont(new java.awt.Font("Comic Sans MS", Font.BOLD, 30));
        panels[0].add(ward_name);
        addTiles();
    }


    private void addTiles(){
        panels[1].removeAll();
        panels[2].removeAll();
        panels[3].removeAll();
        panels[4].removeAll();
        panels[5].removeAll();

        for(int i=0;i<num_bed;i++){
            if(i< CollectiveBedHandler.models.size()){
                tiles[i]=new BedTile(CollectiveBedHandler.models.get(i));
            }else {
                tiles[i] = new BedTile();
            }
            if(i<7){
                panels[1].add(tiles[i]);
            }else if(i<14){
                panels[2].add(tiles[i]);
            }else if(i<21){
                panels[3].add(tiles[i]);
            }else if(i<28){
                panels[4].add(tiles[i]);
            }else{
                panels[5].add(tiles[i]);
            }
        }
        SwingUtilities.updateComponentTreeUI(frame);
    }

    public static void updateTiles(){
        for(int i=0;i<tiles.length;i++){
            if(i< CollectiveBedHandler.models.size()){
                tiles[i].updateTile(CollectiveBedHandler.models.get(i));
            }else {
                tiles[i].updateTile(new BedModel());
            }
        }
    }

    public static void main(String args[]) {
       java.awt.EventQueue.invokeLater(() -> new Main().setVisible(true));
    }
}

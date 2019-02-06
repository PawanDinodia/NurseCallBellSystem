package ncs_v4;

import AlarmHandler.AlarmControl;
import FrameWork.Main;
import NetworkHandler.NetworkThread;

import java.util.Scanner;

/**
 * Main class and Starting point
 * Workflow:-
 * 1.check file tree
 * 2.Make Frame visible
 * 3.Start Network Thread
 */
public class NCS_V4 {
    public static final Scanner scanner=new Scanner(System.in);
    public static void main(String[] args) {
        AlarmControl control=new AlarmControl();
        System.out.println(""+Runtime.getRuntime().availableProcessors());
       InIt init =new InIt();
       init.init();
       Main main=new Main();
       main.setVisible(true);

       new Thread(new NetworkThread()).start();

    }
    
}
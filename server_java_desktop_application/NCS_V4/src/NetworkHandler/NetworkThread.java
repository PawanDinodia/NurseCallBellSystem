package NetworkHandler;

import AlarmHandler.CollectiveBedHandler;
import FrameWork.BedTileFrameWork.BedModel;
import FrameWork.Main;
import ncs_v4.Logs.GenralLogs;
import ncs_v4.Logs.LogLevel;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class NetworkThread implements Runnable {
    private ObjToJson objToJson;

    @Override
    public void run() {
        final int maxNumClient=10;
        Thread[] clientThreads=new Thread[maxNumClient];
        Socket socket;
        try {
            ServerSocket serverSocket=new ServerSocket(6669);
            GenralLogs.glog(this.getClass().getCanonicalName(),"Server Started",LogLevel.MEDIUM);

            new Timer().scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    for(BedModel model: CollectiveBedHandler.models){
                        model.setConnected(false);
                    }
                    Main.updateTiles();
                }
            },1000,10000);

            new Timer().scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    int i=0;
                    while(i<maxNumClient){
                       clientThreads[i]=null;
                        i++;
                    }
                    GenralLogs.glog(this.getClass().getCanonicalName(), "All connections are resettled", LogLevel.LOW);
                }
            },1000,100000);

            while(!Thread.interrupted()){
               int i,numClient;
               socket = serverSocket.accept();
                if(socket.isConnected()){
                    GenralLogs.glog(this.getClass().getCanonicalName(), "A new client with is connected with ip " + socket.getInetAddress(), LogLevel.LOW);
                }
                i=0;
                numClient=0;
                while(i<maxNumClient){
                    if(clientThreads[i]==null||clientThreads[i].isInterrupted()){
                        clientThreads[i]=new Thread(new ClientReceiveThread(socket));
                        clientThreads[i].start();
                        numClient++;
                        break;
                    }
                    i++;
                }
                System.out.println("No. of thread running:-"+i);
                if(numClient==maxNumClient){
                    PrintStream output=new PrintStream(socket.getOutputStream());
                    output.print("Sorry! server is too busy");
                    output.flush();
                    GenralLogs.glog(this.getClass().getCanonicalName(),"Sorry! server is too busy ",LogLevel.LOW);
                    output.close();
                    socket.close();
                }
           }
        } catch (Exception e) {
            GenralLogs.glog(this.getClass().getCanonicalName(),"Error Starting Server "+e.getMessage(),LogLevel.LOW);
        }
    }
}

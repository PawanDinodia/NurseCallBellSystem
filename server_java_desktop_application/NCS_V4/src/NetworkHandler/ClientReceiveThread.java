package NetworkHandler;

import ncs_v4.Logs.GenralLogs;
import ncs_v4.Logs.LogLevel;

import java.io.*;
import java.net.Socket;

public class ClientReceiveThread implements Runnable {
    private Socket socket = null;

    public Socket getSocket() {
        return socket;
    }

    ClientReceiveThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        Commander commander = new Commander(socket);
        try {
            GenralLogs.glog(this.getClass().getCanonicalName(), "No. of thread running:-" + java.lang.Thread.activeCount(), LogLevel.HIGH);
            while (!Thread.interrupted()) {
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                String str = (String) dis.readUTF();
                commander.getCommand(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
            GenralLogs.glog(this.getClass().getCanonicalName(), "Error connecting with " + socket.getInetAddress() + ":\t" + e.getMessage(), LogLevel.LOW);
        }
    }

}

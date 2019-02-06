package AlarmHandler;


import javax.sound.sampled.*;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;


public class AlarmControl extends JFrame{
    private static Clip clip;

    public AlarmControl(){
        setUndecorated(true);
        setSize(5,0);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setVisible(false);
        URL url = this.getClass().getClassLoader().getResource("harry.wav");
        try {
            clip= AudioSystem.getClip();
            AudioInputStream stream=AudioSystem.getAudioInputStream(url);
            clip.open(stream);
        } catch (LineUnavailableException |IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }
    static void play(){
        clip.loop(-1);
    }
    static void stop(){
        clip.stop();
    }



}

package ncs_v4.Logs;

import java.util.Calendar;

/**
 *  Print Genral logs
 */
public class GenralLogs {
    public static LogLevel getLog_level() {
        return log_level;
    }

    private static final LogLevel log_level=LogLevel.HIGH;
    
public static final String RESET = "\u001B[0m";
public static final String BLACK = "\u001B[30m";
public static final String RED = "\u001B[31m";
private static final String GREEN = "\u001B[32m";
public static final String YELLOW = "\u001B[33m";
private static final String BLUE = "\u001B[34m";
public static final String PURPLE = "\u001B[35m";
public static final String CYAN = "\u001B[36m";
public static final String WHITE = "\u001B[37m";
       
    
    public static void glog(String context,String content,LogLevel ll){
    Calendar cal=Calendar.getInstance();
    if(intLogLevel(log_level)>=intLogLevel(ll)){     
        switch(ll){
            case HIGH:
                 System.out.println(cal.get(Calendar.HOUR)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND)+":"+context+":"+content);
                break;
            case MEDIUM:
                 System.out.println(BLUE+"----"+cal.get(Calendar.HOUR)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND)+":"+context+":"+content);
                break;
            case LOW:
                 System.out.println(RED+"----%%----"+cal.get(Calendar.HOUR)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND)+":"+context+":"+content);
                break;
        }
    }
    }
    
    //this function will convert loglevel in int , suitable for comparision
    private static int intLogLevel(LogLevel ll){
        int level=0;
       switch(ll){
           case HIGH:
               level=3;
               break;
           case MEDIUM:
               level=2;
               break;
           case LOW:
               level=1;
               break;
           case NONE:
               level=0;
               break; 
       }
       return level;
    }
    
}
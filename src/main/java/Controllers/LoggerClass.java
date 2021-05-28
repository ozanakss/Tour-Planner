package Controllers;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;



public class LoggerClass {

	static Logger logger = Logger.getLogger(LoggerClass.class.getName());  

    public static void writeLog(String type, String message){
    	
    	BasicConfigurator.configure();
    	//if log is debug log then writing a debug message othwise info msg
        if(type.equals("debug")){
        	logger.debug(message);
        }

        else if(type.equals("info")){
        	logger.info(message);
        }
    }
}

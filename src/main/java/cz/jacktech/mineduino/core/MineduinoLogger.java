package cz.jacktech.mineduino.core;

import cz.jacktech.mineduino.MineDuinoMod;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by toor on 17.11.14.
 */
public class MineduinoLogger {

    private static Logger logger = LogManager.getLogger(MineDuinoMod.MODID);

    public static void log(Level level, String message) {
        logger.log(level, message);
    }

    public static void info(String msg){
        logger.log(Level.INFO, msg);
    }

    public static void warning(String msg){
        logger.log(Level.WARN, msg);
    }

    public static void error(String msg){
        logger.log(Level.ERROR, msg);
    }

}

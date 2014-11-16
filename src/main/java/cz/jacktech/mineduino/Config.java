package cz.jacktech.mineduino;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;

/**
 * Created by toor on 15.11.14.
 */
public class Config {

    private static final String SERIAL_PORT = "serial_port";

    private static final String DEFAULT_SERIAL_PORT = "/dev/ttyACM0";

    private Configuration conf;

    public Config(File suggestedConfigurationFile){
        conf = new Configuration(suggestedConfigurationFile);
        reload();
    }

    private Property getSerialPortProperty(){
        return conf.get(Configuration.CATEGORY_GENERAL,SERIAL_PORT, DEFAULT_SERIAL_PORT,"Serial port of connected Arduino");
    }

    public String getSerialPort(){
        Property port = getSerialPortProperty();
        return port.getString();
    }

    public void reload(){
        conf.load();
    }

}

package cz.jacktech.mineduino;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;

/**
 * Created by toor on 15.11.14.
 */
public class Config {

    private static final String SERIAL_PORT = "serial_port";
    private Configuration conf;

    public Config(File suggestedConfigurationFile){
        conf = new Configuration(suggestedConfigurationFile);
        reload();
        checkConfig();
    }

    private void checkConfig() {
        if(getSerialPortProperty().isDefault()){
            getSerialPortProperty().set("/dev/ttyACM0");
            conf.save();
        }
    }

    private Property getSerialPortProperty(){
        return conf.get(Configuration.CATEGORY_GENERAL,SERIAL_PORT, 0,"Serial port of connected Arduino");
    }

    public String getSerialPort(){
        Property port = getSerialPortProperty();
        if(port.isDefault()){
            return "/dev/ttyACM0";
        }
        return port.getString();
    }

    public void reload(){
        conf.load();
    }

}

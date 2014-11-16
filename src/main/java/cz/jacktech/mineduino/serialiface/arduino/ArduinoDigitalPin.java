package cz.jacktech.mineduino.serialiface.arduino;

import cz.jacktech.mineduino.serialiface.IncorrectPinModeException;
import cz.jacktech.mineduino.serialiface.SerialManager;

import static cz.jacktech.mineduino.serialiface.SerialManager.*;

/**
 * Created by toor on 12.11.14.
 */
public class ArduinoDigitalPin extends ArduinoPin{

    public boolean status = false;

    public boolean assigned = false;
    public PinMode pinType = PinMode.OUTPUT;
    public int value = 0;

    public ArduinoDigitalPin(int pinNumber, boolean status) {
        super(pinNumber);
        this.status = status;
    }

    public void enable(){
        if(pinType == PinMode.OUTPUT) {
            try {
                SerialManager.getInstance().writePin(this, 1);
            } catch (IncorrectPinModeException e) {
                e.printStackTrace();
            }
            status = true;
        }
    }

    public void disable(){
        if(pinType == PinMode.OUTPUT) {
            try {
                SerialManager.getInstance().writePin(this, 0);
            } catch (IncorrectPinModeException e) {
                e.printStackTrace();
            }
            status = true;
        }
    }

    public void setValue(int value){
        if(pinType == PinMode.ANALOG_OUTPUT){
            try {
                SerialManager.getInstance().writePin(this, value);
            } catch (IncorrectPinModeException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean read(){
        return status;
    }

    public void updateMode(PinMode pinMode){
        SerialManager.getInstance().setupPin(this, pinMode);
    }

    public void reset() {
        updateMode(PinMode.OUTPUT);
        disable();
    }

    public void update(boolean enabled) {
        if(enabled)
            enable();
        else
            disable();
    }
}

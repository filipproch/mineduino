package cz.jacktech.mineduino.serialiface.arduino;

import cz.jacktech.mineduino.serialiface.SerialManager;

/**
 * Created by toor on 16.11.14.
 */
public class ArduinoAnalogPin extends ArduinoPin {

    private int value;

    public ArduinoAnalogPin(int pinNumber, int pinValue) {
        super(pinNumber);
    }

    public void updateValue(int newValue){
        this.value = newValue;
    }

    public void setup() {
        SerialManager.getInstance().setupPin(this, PinMode.ANALOG_INPUT);
    }

    public void reset() {
        SerialManager.getInstance().resetAnalogPin(this);
    }

    public int read(){
        return value;
    }

}

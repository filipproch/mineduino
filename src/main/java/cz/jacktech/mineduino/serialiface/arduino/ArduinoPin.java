package cz.jacktech.mineduino.serialiface.arduino;

/**
 * Created by toor on 15.11.14.
 */
public abstract class ArduinoPin {

    public static final int DIGITAL = 0;
    public static final int ANALOG = 1;

    public int pinNumber;

    protected ArduinoPin(int pinNumber) {
        this.pinNumber = pinNumber;
    }

    public static ArduinoPin create(int pinNumber, int pinValue, boolean analogPin) {
        if(analogPin){
            return new ArduinoAnalogPin(pinNumber, pinValue);
        }else{
            return new ArduinoDigitalPin(pinNumber, pinValue == 1);
        }
    }

    public static enum PinMode{
        INPUT, OUTPUT, ANALOG_OUTPUT, ANALOG_INPUT;
    }

}

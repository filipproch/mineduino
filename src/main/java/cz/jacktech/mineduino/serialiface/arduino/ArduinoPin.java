package cz.jacktech.mineduino.serialiface.arduino;

/**
 * Created by toor on 15.11.14.
 */
public abstract class ArduinoPin {

    public int pinNumber;

    protected ArduinoPin(int pinNumber) {
        this.pinNumber = pinNumber;
    }

    public static enum PinMode{
        INPUT, OUTPUT, ANALOG_OUTPUT, ANALOG_INPUT;
    }

}

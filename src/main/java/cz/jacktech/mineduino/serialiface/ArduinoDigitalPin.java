package cz.jacktech.mineduino.serialiface;

/**
 * Created by toor on 12.11.14.
 */
public class ArduinoDigitalPin {

    public int pinNumber = -1;
    public int status = 0;

    public ArduinoDigitalPin(SerialData input) {
        pinNumber = input.getInt(0);
        status = input.getInt(1);
    }
}

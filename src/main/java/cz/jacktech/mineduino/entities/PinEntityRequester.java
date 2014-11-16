package cz.jacktech.mineduino.entities;

import cz.jacktech.mineduino.entities.tiles.InputTileEntity;
import cz.jacktech.mineduino.serialiface.SerialManager;
import cz.jacktech.mineduino.serialiface.arduino.ArduinoDigitalPin;
import cz.jacktech.mineduino.serialiface.arduino.ArduinoPin;

/**
 * Created by toor on 16.11.14.
 */
public abstract class PinEntityRequester implements IEntityRequester{

    public static final String ARDUINO_PIN = "arduino_pin";

    protected ArduinoPin getPin(ETileEntity entity){
        try {
            System.out.println("EntityValueStore: "+entity.getValueStore());
            return (ArduinoDigitalPin) entity.getValueStore().getObject(ARDUINO_PIN);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void create(ETileEntity entity) {
        System.out.println("creating pin entity requester");
        InputTileEntity inputTileEntity = (InputTileEntity) entity;
        entity.getValueStore().putObject(ARDUINO_PIN, SerialManager.getInstance().getPin(Integer.parseInt(inputTileEntity.getInputName())));
    }

}

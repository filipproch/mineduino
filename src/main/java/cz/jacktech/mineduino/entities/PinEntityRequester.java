package cz.jacktech.mineduino.entities;

import cz.jacktech.mineduino.entities.tiles.InputTileEntity;
import cz.jacktech.mineduino.entities.tiles.OutputTileEntity;
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
            //System.out.println("EntityValueStore: " + entity.getValueStore());
            return (ArduinoDigitalPin) entity.getValueStore().getObject(ARDUINO_PIN);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateGuiClosed(ETileEntity entity) {
        storeArduinoPin(entity);
    }

    @Override
    public void create(ETileEntity entity) {
        storeArduinoPin(entity);
    }

    private void storeArduinoPin(ETileEntity entity){
        try {
            if (entity instanceof InputTileEntity) {
                InputTileEntity inputTileEntity = (InputTileEntity) entity;
                entity.getValueStore().putObject(ARDUINO_PIN, SerialManager.getInstance().getPin(Integer.parseInt(inputTileEntity.getInputName())));
            } else if (entity instanceof OutputTileEntity) {
                OutputTileEntity outputTileEntity = (OutputTileEntity) entity;
                entity.getValueStore().putObject(ARDUINO_PIN, SerialManager.getInstance().getPin(Integer.parseInt(outputTileEntity.getOutputName())));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

package cz.jacktech.mineduino.entities.input;

import cz.jacktech.mineduino.core.MineduinoLogger;
import cz.jacktech.mineduino.entities.ETileEntity;
import cz.jacktech.mineduino.gui.GuiHandler;
import cz.jacktech.mineduino.serialiface.SerialManager;
import cz.jacktech.mineduino.serialiface.arduino.ArduinoDigitalPin;
import cz.jacktech.mineduino.serialiface.arduino.ArduinoPin;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by toor on 17.11.14.
 */
public class DigitalInTileEntity extends ETileEntity {

    public static final String ENTITY_NAME = DigitalInTileEntity.class.getSimpleName();

    private static final String ARDUINO_PIN_NUMBER = "ArduinoPinNumber";
    private static final String CURRENT_STATUS = "CurrentStatus";

    private ArduinoDigitalPin arduinoPin;
    private int arduinoPinNumber = -1;
    private boolean currentStatus = false;

    @Override
    public void writeToNBT(NBTTagCompound nbttag) {
        super.writeToNBT(nbttag);
        nbttag.setInteger(ARDUINO_PIN_NUMBER, arduinoPinNumber);
        nbttag.setBoolean(CURRENT_STATUS, currentStatus);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttag) {
        super.readFromNBT(nbttag);
        setArduinoPinNumber(nbttag.getInteger(ARDUINO_PIN_NUMBER));
        currentStatus = nbttag.getBoolean(CURRENT_STATUS);
    }

    public void setArduinoPin(int pinNumber) {
        if(!getWorldObj().isRemote) {
            arduinoPin = SerialManager.getInstance().getDigitalPin(pinNumber);
            if(arduinoPin != null)
                arduinoPin.updateMode(ArduinoPin.PinMode.INPUT);
        }
    }

    @Override
    public void updateEntity() {
        if ((arduinoPin == null || arduinoPin.pinNumber != arduinoPinNumber) && arduinoPinNumber >= 0)
            setArduinoPin(arduinoPinNumber);

        if(arduinoPin != null && arduinoPin.read() != currentStatus){
            MineduinoLogger.info("inpin changed status > "+arduinoPin.read());
            currentStatus = arduinoPin.read();
            markForUpdate();
        }
    }

    @Override
    public void blockDestroyed() {
        if(arduinoPin != null)
            arduinoPin.reset();
    }

    @Override
    public int openGui() {
        return GuiHandler.GUI_DIGITAL;
    }

    @Override
    public void blockAdded() {
        if(arduinoPin != null)
            arduinoPin.updateMode(ArduinoPin.PinMode.INPUT);
    }

    @Override
    public int isProvidingPower() {
        MineduinoLogger.info("isProvidingPower: "+currentStatus+", ar"+arduinoPin);
        return currentStatus ? 15 : 0;
    }

    public ArduinoDigitalPin getArduinoPin() {
        return arduinoPin;
    }

    public void setArduinoPinNumber(int arduinoPinNumber) {
        this.arduinoPinNumber = arduinoPinNumber;
    }

    public int getArduinoPinNumber() {
        return arduinoPinNumber;
    }
}

package cz.jacktech.mineduino.entities.input;

import cz.jacktech.mineduino.entities.InputTileEntity;
import cz.jacktech.mineduino.gui.GuiHandler;
import cz.jacktech.mineduino.serialiface.SerialManager;
import cz.jacktech.mineduino.serialiface.arduino.ArduinoAnalogPin;
import cz.jacktech.mineduino.serialiface.arduino.ArduinoDigitalPin;
import cz.jacktech.mineduino.serialiface.arduino.ArduinoPin;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by toor on 17.11.14.
 */
public class AnalogInTileEntity extends InputTileEntity {

    public static final String ENTITY_NAME = AnalogInTileEntity.class.getSimpleName();

    private static final String ARDUINO_PIN_NUMBER = "ArduinoPinNumber";
    private static final String CURRENT_VALUE = "CurrentValue";

    private ArduinoAnalogPin arduinoPin;
    private int arduinoPinNumber = -1;
    private int currentValue = 0;

    @Override
    public void writeToNBT(NBTTagCompound nbttag) {
        super.writeToNBT(nbttag);
        nbttag.setInteger(ARDUINO_PIN_NUMBER, arduinoPinNumber);
        nbttag.setInteger(CURRENT_VALUE, currentValue);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttag) {
        super.readFromNBT(nbttag);
        setArduinoPinNumber(nbttag.getInteger(ARDUINO_PIN_NUMBER));
        currentValue = nbttag.getInteger(CURRENT_VALUE);
    }

    public void setArduinoPin(int pinNumber) {
        if(!getWorldObj().isRemote) {
            arduinoPin = SerialManager.getInstance().getAnalogPin(pinNumber);
            if(arduinoPin != null)
                arduinoPin.setup();
        }
    }

    @Override
    public void updateEntity() {
        if ((arduinoPin == null || arduinoPin.pinNumber != arduinoPinNumber) && arduinoPinNumber >= 0)
            setArduinoPin(arduinoPinNumber);

        if(arduinoPin != null && arduinoPin.read() != currentValue){
            currentValue = arduinoPin.read();
            notifyNeighbourBlocks();
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
            arduinoPin.setup();
    }

    @Override
    public int isProvidingPower() {
        return (int) Math.floor(currentValue / 68);
    }

    public ArduinoAnalogPin getArduinoPin() {
        return arduinoPin;
    }

    public void setArduinoPinNumber(int arduinoPinNumber) {
        this.arduinoPinNumber = arduinoPinNumber;
    }

    public int getArduinoPinNumber() {
        return arduinoPinNumber;
    }

    @Override
    public Object getInput() {
        return currentValue;
    }
}

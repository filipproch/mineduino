package cz.jacktech.mineduino.tiles.old;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by toor on 12.11.14.
 */
public abstract class DigitalPinEntity extends TileEntity{

    private static final String ARDUINO_PIN = "ArduinoPin";
    private int arduinoPin = 2;

    @Override
    public void writeToNBT(NBTTagCompound nbttag) {
        super.writeToNBT(nbttag);
        nbttag.setInteger(ARDUINO_PIN, arduinoPin);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttag) {
        super.readFromNBT(nbttag);
        setArduinoPin(nbttag.getInteger(ARDUINO_PIN));
    }

    @Override
    public Packet getDescriptionPacket(){
        NBTTagCompound tag = new NBTTagCompound();
        writeToNBT(tag);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        readFromNBT(pkt.func_148857_g());
    }

    public int getArduinoPin(){
        return arduinoPin;
    }

    public void setArduinoPin(int arduinoPin){
        this.arduinoPin = arduinoPin;
    }

    public void markForUpdate() {
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        markDirty();
    }

}

package cz.jacktech.mineduino.entities;

import cz.jacktech.mineduino.blocks.input.AnalogIn;
import cz.jacktech.mineduino.blocks.input.DigitalIn;
import cz.jacktech.mineduino.blocks.output.DigitalOut;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by toor on 16.11.14.
 */
public abstract class ETileEntity extends TileEntity{

    //protected final IEntityRequester requester;
    protected final EntityValueStore valueStore;

    public ETileEntity() {
        this.valueStore = new EntityValueStore();
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

    public void markForUpdate() {
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        markDirty();
    }

    public abstract void blockDestroyed();

    public abstract int openGui();

    public EntityValueStore getValueStore() {
        return valueStore;
    }

    public abstract void blockAdded();

    public abstract int isProvidingPower();

    public boolean isPowered(){
        return getWorldObj().isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
    }

}

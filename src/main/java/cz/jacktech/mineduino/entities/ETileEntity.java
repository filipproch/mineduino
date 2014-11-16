package cz.jacktech.mineduino.entities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by toor on 16.11.14.
 */
public class ETileEntity extends TileEntity{

    private final IEntityRequester requester;
    private final EntityValueStore valueStore;

    public ETileEntity(IEntityRequester requester) {
        this.requester = requester;
        this.valueStore = new EntityValueStore();
        requester.create(this);
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

    @Override
    public void updateEntity() {
        if(requester != null && !worldObj.isRemote)
            requester.requestUpdate(this);
    }

    public int openGui(){
        if(requester != null)
            return requester.getGui(this);
        return 0;
    }

    public EntityValueStore getValueStore() {
        return valueStore;
    }
}

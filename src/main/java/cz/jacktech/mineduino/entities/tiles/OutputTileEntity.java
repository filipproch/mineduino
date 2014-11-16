package cz.jacktech.mineduino.entities.tiles;

import cz.jacktech.mineduino.entities.ETileEntity;
import cz.jacktech.mineduino.entities.EntityValueStore;
import cz.jacktech.mineduino.entities.IEntityRequester;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by toor on 16.11.14.
 */
public class OutputTileEntity extends ETileEntity {

    public static final String ENTITY_NAME = OutputTileEntity.class.getSimpleName();
    private static final String OUTPUT_NAME = "OutputName";

    private String outputName = "-1";

    public OutputTileEntity(){};

    @Override
    public void writeToNBT(NBTTagCompound nbttag) {
        super.writeToNBT(nbttag);
        nbttag.setString(OUTPUT_NAME, outputName);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttag) {
        super.readFromNBT(nbttag);
        setOutputName(nbttag.getString(OUTPUT_NAME));
    }

    public String getOutputName() {
        return outputName;
    }

    public void setOutputName(String outputName) {
        this.outputName = outputName;
        if(requester != null)
            requester.updateGuiClosed(this);
    }

}

package cz.jacktech.mineduino.tiles;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by toor on 16.11.14.
 */
public class OutputTileEntity extends ETileEntity {

    public static final String ENTITY_NAME = OutputTileEntity.class.getSimpleName();
    private static final String OUTPUT_NAME = "OutputName";

    private String outputName;

    public OutputTileEntity(IEntityRequester requester) {
        super(requester);
    }

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
    }

}

package cz.jacktech.mineduino.entities.tiles;

import cz.jacktech.mineduino.entities.ETileEntity;
import cz.jacktech.mineduino.entities.EntityValueStore;
import cz.jacktech.mineduino.entities.IEntityRequester;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by toor on 15.11.14.
 */
public class InputTileEntity extends ETileEntity {

    public static final String ENTITY_NAME = InputTileEntity.class.getSimpleName();
    private static final String INPUT_NAME = "InputName";

    private String inputName;

    public InputTileEntity(IEntityRequester requester) {
        super(requester);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbttag) {
        super.writeToNBT(nbttag);
        nbttag.setString(INPUT_NAME, inputName);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttag) {
        super.readFromNBT(nbttag);
        setInputName(nbttag.getString(INPUT_NAME));
    }

    public String getInputName() {
        return inputName;
    }

    public void setInputName(String inputName) {
        this.inputName = inputName;
    }

}

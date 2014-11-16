package cz.jacktech.mineduino.blocks.input;

import cz.jacktech.mineduino.MineDuinoMod;
import cz.jacktech.mineduino.blocks.ABlock;
import cz.jacktech.mineduino.entities.EntityValueStore;
import cz.jacktech.mineduino.entities.PinEntityRequester;
import cz.jacktech.mineduino.gui.GuiHandler;
import cz.jacktech.mineduino.serialiface.SerialManager;
import cz.jacktech.mineduino.serialiface.arduino.ArduinoDigitalPin;
import cz.jacktech.mineduino.entities.ETileEntity;
import cz.jacktech.mineduino.entities.IEntityRequester;
import cz.jacktech.mineduino.entities.tiles.InputTileEntity;
import cz.jacktech.mineduino.serialiface.arduino.ArduinoPin;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by toor on 11.11.14.
 */
public class DigitalIn extends ABlock {

    public static final String BLOCK_NAME = "digitalIn";

    public DigitalIn() {
        super(Material.circuits, mDataRequester);
        setBlockName(BLOCK_NAME);
        setBlockTextureName(MineDuinoMod.MODID + ":" + BLOCK_NAME);
        setCreativeTab(MineDuinoMod.tabArduino);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        return new InputTileEntity();
    }

    private static PinEntityRequester mDataRequester = new PinEntityRequester() {

        @Override
        public void requestUpdate(ETileEntity entity) {
        }

        @Override
        public void blockDestroyed(ETileEntity entity) {
            ((ArduinoDigitalPin)getPin(entity)).reset();
        }

        @Override
        public int getGui(ETileEntity entity) {
            return GuiHandler.GUI_DIGITAL;
        }

        @Override
        public int isProvidingPower(ETileEntity entity) {
            return ((ArduinoDigitalPin)getPin(entity)).read() ? 15 : 0;
        }

        @Override
        public boolean canProvidePower() {
            return true;
        }

        @Override
        public void blockAdded(ETileEntity tileEntity) {
            ArduinoDigitalPin pin = (ArduinoDigitalPin) getPin(tileEntity);
            pin.updateMode(ArduinoPin.PinMode.INPUT);
        }

    };

}

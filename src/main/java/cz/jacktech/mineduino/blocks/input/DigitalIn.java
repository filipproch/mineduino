package cz.jacktech.mineduino.blocks.input;

import cz.jacktech.mineduino.MineDuinoMod;
import cz.jacktech.mineduino.blocks.ABlock;
import cz.jacktech.mineduino.gui.GuiHandler;
import cz.jacktech.mineduino.serialiface.SerialManager;
import cz.jacktech.mineduino.tiles.ETileEntity;
import cz.jacktech.mineduino.tiles.IEntityRequester;
import cz.jacktech.mineduino.tiles.InputTileEntity;
import cz.jacktech.mineduino.tiles.old.DigitalInEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
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
        return new InputTileEntity(mDataRequester);
    }

    private static IEntityRequester mDataRequester = new IEntityRequester() {

        @Override
        public void requestUpdate(ETileEntity entity) {

        }

        @Override
        public void blockDestroyed(ETileEntity entity) {

        }

        @Override
        public int getGui(ETileEntity entity) {
            return GuiHandler.GUI_DIGITAL;
        }

        @Override
        public int isProvidingPower(ETileEntity entity) {
            return 0;
        }

        @Override
        public boolean canProvidePower() {
            return true;
        }

    };

}

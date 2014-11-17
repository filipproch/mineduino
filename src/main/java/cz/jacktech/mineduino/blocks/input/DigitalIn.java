package cz.jacktech.mineduino.blocks.input;

import cz.jacktech.mineduino.MineDuinoMod;
import cz.jacktech.mineduino.blocks.ABlock;
import cz.jacktech.mineduino.entities.input.DigitalInTileEntity;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by toor on 11.11.14.
 */
public class DigitalIn extends ABlock {

    public static final String BLOCK_NAME = "digitalIn";

    public DigitalIn() {
        super(Material.circuits);
        setBlockName(BLOCK_NAME);
        setBlockTextureName(MineDuinoMod.MODID + ":" + BLOCK_NAME);
        setCreativeTab(MineDuinoMod.tabArduino);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        return new DigitalInTileEntity();
    }

    @Override
    public boolean canProvidePower() {
        return true;
    }

}

package cz.jacktech.mineduino.blocks.input;

import cz.jacktech.mineduino.MineDuinoMod;
import cz.jacktech.mineduino.entities.input.AnalogInTileEntity;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by toor on 13.11.14.
 */
public class AnalogIn extends BlockContainer {

    public static final String BLOCK_NAME = "analogIn";

    public AnalogIn() {
        super(Material.circuits);
        setBlockName(BLOCK_NAME);
        setBlockTextureName(MineDuinoMod.MODID + ":" + BLOCK_NAME);
        setCreativeTab(MineDuinoMod.tabArduino);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        return new AnalogInTileEntity();
    }

    @Override
    public boolean canProvidePower() {
        return true;
    }

}

package cz.jacktech.mineduino.blocks.output;

import cz.jacktech.mineduino.MineDuinoMod;
import cz.jacktech.mineduino.blocks.ABlock;
import cz.jacktech.mineduino.entities.output.DigitalOutTileEntity;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by toor on 11.11.14.
 */
public class DigitalOut extends ABlock{

    public static final String BLOCK_NAME = "digitalOut";

    //private boolean isBlockPowered = false;

    public DigitalOut() {
        super(Material.circuits);
        setTickRandomly(true);
        setBlockName(BLOCK_NAME);
        setBlockTextureName(MineDuinoMod.MODID + ":" + BLOCK_NAME);
        setCreativeTab(MineDuinoMod.tabArduino);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        try {
            return new DigitalOutTileEntity();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}

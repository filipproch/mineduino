package cz.jacktech.mineduino.blocks.output;

import cz.jacktech.mineduino.MineDuinoMod;
import cz.jacktech.mineduino.blocks.ABlock;
import cz.jacktech.mineduino.entities.output.AnalogOutTileEntity;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by toor on 13.11.14.
 */
public class AnalogOut extends ABlock{

    public static final String BLOCK_NAME = "analogOut";

    public AnalogOut() {
        super(Material.circuits);
        setBlockName(BLOCK_NAME);
        setBlockTextureName(MineDuinoMod.MODID + ":" + BLOCK_NAME);
        setCreativeTab(MineDuinoMod.tabArduino);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        try {
            return new AnalogOutTileEntity();
        }catch (Exception e){}
        return null;
    }

}

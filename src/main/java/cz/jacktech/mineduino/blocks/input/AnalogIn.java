package cz.jacktech.mineduino.blocks.input;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by toor on 13.11.14.
 */
public class AnalogIn extends BlockContainer {
    protected AnalogIn() {
        super(Material.circuits);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        return null;
    }
}

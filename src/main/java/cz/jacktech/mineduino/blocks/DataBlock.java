package cz.jacktech.mineduino.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by toor on 13.11.14.
 */
public abstract class DataBlock extends BlockContainer {

    public static final int DATATYPE_BOOLEAN = 0;
    public static final int DATATYPE_NUMBER = 1;
    public static final int DATATYPE_TEXT = 2;

    protected int dataType;
    protected int dataLength;

    protected DataBlock() {
        super(Material.circuits);
    }

    /*@Override
    public TileEntity createTileEntity(World world, int metadata) {
        return super.createTileEntity(world, metadata);
    }*/
}

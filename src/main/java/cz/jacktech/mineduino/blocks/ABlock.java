package cz.jacktech.mineduino.blocks;

import cz.jacktech.mineduino.MineDuinoMod;
import cz.jacktech.mineduino.core.MineduinoLogger;
import cz.jacktech.mineduino.entities.ETileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Created by toor on 16.11.14.
 */
public abstract class ABlock extends BlockContainer {

    protected ABlock(Material material) {
        super(material);
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        if (world.getBlockMetadata(x, y, z) == 0) {
            super.onBlockAdded(world, x, y, z);
        }

        if(!world.isRemote) {
            ETileEntity eTileEntity = (ETileEntity) world.getTileEntity(x, y, z);
            eTileEntity.blockAdded();
        }

        world.notifyBlocksOfNeighborChange(x, y, z, this);
        world.scheduleBlockUpdate(x, y, z, this, 0);
    }

    @Override
    public int isProvidingWeakPower(IBlockAccess p_149709_1_, int p_149709_2_, int p_149709_3_, int p_149709_4_, int p_149709_5_) {
        return isProvidingPower(p_149709_1_, p_149709_2_, p_149709_3_, p_149709_4_);
    }

    @Override
    public int isProvidingStrongPower(IBlockAccess p_149748_1_, int p_149748_2_, int p_149748_3_, int p_149748_4_, int p_149748_5_) {
        return isProvidingPower(p_149748_1_, p_149748_2_, p_149748_3_, p_149748_4_);
    }

    private int isProvidingPower(IBlockAccess world, int x, int y, int z) {
        ETileEntity tileEntity = (ETileEntity) world.getTileEntity(x, y, z);
        if(!tileEntity.getWorldObj().isRemote)
            return tileEntity.isProvidingPower();
        return 0;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int metadata, float what, float these, float are) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity == null || player.isSneaking()) {
            return false;
        }

        player.openGui(MineDuinoMod.instance, 0, world, x, y, z);
        return true;
    }

    private void blockDestroyed(World world, int x, int y, int z){
        if(!world.isRemote){
            ETileEntity tileEntity = (ETileEntity) world.getTileEntity(x, y, z);
            tileEntity.blockDestroyed();
        }
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int breaker) {
        blockDestroyed(world, x, y, z);
    }
}

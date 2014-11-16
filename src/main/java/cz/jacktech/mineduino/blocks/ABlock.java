package cz.jacktech.mineduino.blocks;

import cz.jacktech.mineduino.MineDuinoMod;
import cz.jacktech.mineduino.tiles.ETileEntity;
import cz.jacktech.mineduino.tiles.IEntityRequester;
import cz.jacktech.mineduino.tiles.old.DigitalInEntity;
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

    private IEntityRequester requester;

    protected ABlock(Material material, IEntityRequester requester) {
        super(material);
        this.requester = requester;
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {

        if (world.getBlockMetadata(x, y, z) == 0) {
            super.onBlockAdded(world, x, y, z);
        }

        world.notifyBlocksOfNeighborChange(x, y, z, this);
        world.scheduleBlockUpdate(x, y, z, this, 0);
    }

    @Override
    public boolean canProvidePower() {
        return requester.canProvidePower();
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
        /*if (tileEntity instanceof DigitalInEntity) {
            return ((DigitalInEntity) tileEntity).isProvidingPower ? 15 : 0;
        }*/

        return requester.isProvidingPower(tileEntity);
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
            requester.blockDestroyed(tileEntity);
            /*if (tileEntity instanceof DigitalInEntity) {
                DigitalInEntity entity = (DigitalInEntity) tileEntity;
                SerialManager.getInstance().resetPin(entity.getArduinoPin());
            }*/
        }
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int breaker) {
        //super.breakBlock(p_breakBlock_1_, p_breakBlock_2_, p_breakBlock_3_, p_breakBlock_4_, p_breakBlock_5_, p_breakBlock_6_);
        blockDestroyed(world, x, y, z);
    }
}

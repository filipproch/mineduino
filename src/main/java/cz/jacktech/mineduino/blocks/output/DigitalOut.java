package cz.jacktech.mineduino.blocks.output;

import cz.jacktech.mineduino.MineDuinoMod;
import cz.jacktech.mineduino.serialiface.SerialManager;
import cz.jacktech.mineduino.tiles.old.DigitalOutEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by toor on 11.11.14.
 */
public class DigitalOut extends BlockContainer{

    public static final String BLOCK_NAME = "digitalOUT";

    private boolean isBlockPowered = false;

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
            return new DigitalOutEntity();
        }catch (Exception e){}
        return null;
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        updatePoweredState(world, x, y, z);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        updatePoweredState(world, x, y, z);
    }

    private void updatePoweredState(World world, int x, int y, int z){
        /*if(!world.isRemote) {
            TileEntity tileEntity = world.getTileEntity(x, y, z);
            //System.out.println("update powered state");
            if (tileEntity != null && tileEntity instanceof DigitalOutEntity) {
                DigitalOutEntity entity = (DigitalOutEntity) tileEntity;
                boolean blockIsPowered = world.isBlockIndirectlyGettingPowered(x, y, z);
                //System.out.println("is block powered : "+blockIsPowered+", "+entity.getArduinoPin());
                if(isBlockPowered != blockIsPowered) {
                    isBlockPowered = blockIsPowered;
                    boolean cmdSuccess = false;
                    if (blockIsPowered && entity.getArduinoPin() != -1) {
                        cmdSuccess = SerialManager.getInstance().enablePin(entity.getArduinoPin());
                    } else {
                        cmdSuccess = SerialManager.getInstance().disablePin(entity.getArduinoPin());
                    }
                    //System.out.println("cmd send " + cmdSuccess);
                }
            }
        }*/
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random random) {
        updatePoweredState(world, x, y, z);
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

}

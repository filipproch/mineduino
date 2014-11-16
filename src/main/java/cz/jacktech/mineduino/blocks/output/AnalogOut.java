package cz.jacktech.mineduino.blocks.output;

import cz.jacktech.mineduino.MineDuinoMod;
import cz.jacktech.mineduino.serialiface.SerialManager;
import cz.jacktech.mineduino.tiles.old.AnalogOutEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by toor on 13.11.14.
 */
public class AnalogOut extends BlockContainer{

    public static final String BLOCK_NAME = "analogOut";

    private boolean isBlockPowered = false;
    private int currentPowerLevel = 0;

    public AnalogOut() {
        super(Material.circuits);
        setTickRandomly(true);
        setBlockName(BLOCK_NAME);
        setBlockTextureName(MineDuinoMod.MODID + ":" + BLOCK_NAME);
        setCreativeTab(MineDuinoMod.tabArduino);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        try {
            return new AnalogOutEntity();
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
            if (tileEntity != null && tileEntity instanceof AnalogOutEntity) {
                AnalogOutEntity entity = (AnalogOutEntity) tileEntity;
                boolean blockIsPowered = world.isBlockIndirectlyGettingPowered(x, y, z);
                int powerLevel = world.getBlockPowerInput(x, y, z);
                //System.out.println("is block powered : "+blockIsPowered+", "+entity.getArduinoPin());
                if(isBlockPowered != blockIsPowered || currentPowerLevel != powerLevel) {
                    isBlockPowered = blockIsPowered;
                    currentPowerLevel = powerLevel;
                    boolean cmdSuccess = false;
                    //System.out.println("power level : "+powerLevel);
                    if (blockIsPowered && entity.getArduinoPin() != -1) {
                        cmdSuccess = SerialManager.getInstance().writeAnalog(entity.getArduinoPin(), powerLevel*17);
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

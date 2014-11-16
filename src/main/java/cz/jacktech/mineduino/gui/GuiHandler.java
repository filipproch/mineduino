package cz.jacktech.mineduino.gui;

import cpw.mods.fml.common.network.IGuiHandler;
import cz.jacktech.mineduino.tiles.ETileEntity;
import cz.jacktech.mineduino.tiles.old.DigitalPinEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by toor on 11.11.14.
 */
public class GuiHandler implements IGuiHandler {

    public static final int GUI_DIGITAL = 1;
    public static final int GUI_ANALOG_INPUT = 2;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if(tileEntity instanceof ETileEntity){
            ETileEntity eTileEntity = ((ETileEntity) tileEntity);
            switch (eTileEntity.openGui()){
                case GUI_DIGITAL:
                    return new GuiEnterDigitalPin(eTileEntity);
                case GUI_ANALOG_INPUT:
                    return null;//todo: return gui analog
            }
        }
        return null;
    }
}

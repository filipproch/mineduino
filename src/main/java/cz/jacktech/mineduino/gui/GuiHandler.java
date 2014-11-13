package cz.jacktech.mineduino.gui;

import cpw.mods.fml.common.network.IGuiHandler;
import cz.jacktech.mineduino.tiles.DigitalOutEntity;
import cz.jacktech.mineduino.tiles.DigitalPinEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by toor on 11.11.14.
 */
public class GuiHandler implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if(tileEntity instanceof DigitalPinEntity){
            return new GuiEnterDigitalPin((DigitalPinEntity) tileEntity);
        }
        return null;
    }
}

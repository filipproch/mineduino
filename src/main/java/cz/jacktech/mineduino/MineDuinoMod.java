package cz.jacktech.mineduino;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cz.jacktech.mineduino.blocks.output.AnalogOut;
import cz.jacktech.mineduino.blocks.input.DigitalIn;
import cz.jacktech.mineduino.blocks.output.DigitalOut;
import cz.jacktech.mineduino.entities.input.DigitalInTileEntity;
import cz.jacktech.mineduino.entities.output.AnalogOutTileEntity;
import cz.jacktech.mineduino.entities.output.DigitalOutTileEntity;
import cz.jacktech.mineduino.gui.GuiHandler;
import cz.jacktech.mineduino.serialiface.SerialManager;
import cz.jacktech.mineduino.synch.ArduinoPinSyncMessage;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * Created by toor on 11.11.14.
 */
@Mod(modid = MineDuinoMod.MODID, version = MineDuinoMod.VERSION)
public class MineDuinoMod {

    public static final String MODID = "mineduino";
    public static final String VERSION = "1.0";
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);

    public static MineDuinoMod instance;
    public static DigitalIn blockDigitalIn;
    public static DigitalOut blockDigitalOut;
    public static AnalogOut blockAnalogOut;

    public Config config;

    public MineDuinoMod() {
        instance = this;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        config = new Config(event.getSuggestedConfigurationFile());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event){
        GameRegistry.registerBlock(blockDigitalIn = new DigitalIn(), DigitalIn.BLOCK_NAME);
        GameRegistry.registerBlock(blockDigitalOut = new DigitalOut(), DigitalOut.BLOCK_NAME);
        GameRegistry.registerBlock(blockAnalogOut = new AnalogOut(), AnalogOut.BLOCK_NAME);
        GameRegistry.registerTileEntity(DigitalInTileEntity.class, DigitalInTileEntity.ENTITY_NAME);
        GameRegistry.registerTileEntity(DigitalOutTileEntity.class, DigitalOutTileEntity.ENTITY_NAME);
        GameRegistry.registerTileEntity(AnalogOutTileEntity.class, AnalogOutTileEntity.ENTITY_NAME);

        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

        INSTANCE.registerMessage(ArduinoPinSyncMessage.Handler.class, ArduinoPinSyncMessage.class, 0, Side.SERVER);
    }

    @Mod.EventHandler
    public void serverStart(FMLServerStartedEvent event){
        System.out.println("server started");
        SerialManager.getInstance().connect();
    }

    @Mod.EventHandler
    public void serverStop(FMLServerStoppingEvent event){
        System.out.println("server stopped");
        SerialManager.getInstance().close();
    }

    public static CreativeTabs tabArduino = new CreativeTabs("tabArduino") {
        @Override
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem() {
            return Item.getItemFromBlock(blockDigitalIn);
        }
    };

}

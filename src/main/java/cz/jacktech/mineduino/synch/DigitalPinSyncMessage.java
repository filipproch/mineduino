package cz.jacktech.mineduino.synch;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cz.jacktech.mineduino.tiles.DigitalInEntity;
import cz.jacktech.mineduino.tiles.DigitalOutEntity;
import cz.jacktech.mineduino.tiles.DigitalPinEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by toor on 11.11.14.
 */
public class DigitalPinSyncMessage implements IMessage {

    public int x,y,z,arduinoPin;

    public DigitalPinSyncMessage() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
        arduinoPin = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeInt(arduinoPin);
    }

    public IMessage setup(DigitalPinEntity tileEntity) {
        this.x = tileEntity.xCoord;
        this.y = tileEntity.yCoord;
        this.z = tileEntity.zCoord;
        this.arduinoPin = tileEntity.getArduinoPin();
        return this;
    }

    public static class Handler implements IMessageHandler<DigitalPinSyncMessage, IMessage> {

        @Override
        public IMessage onMessage(DigitalPinSyncMessage m, MessageContext ctx) {
            TileEntity entity = ctx.getServerHandler().playerEntity.getEntityWorld().getTileEntity(m.x,m.y,m.z);
            if(entity instanceof DigitalPinEntity){
                System.out.println("updating entity");
                DigitalPinEntity digitalPinEntity = (DigitalPinEntity) entity;
                digitalPinEntity.setArduinoPin(m.arduinoPin);
                digitalPinEntity.markForUpdate();
            }
            return null;
        }
    }

}

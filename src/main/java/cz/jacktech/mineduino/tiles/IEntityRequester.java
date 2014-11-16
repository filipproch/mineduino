package cz.jacktech.mineduino.tiles;

/**
 * Created by toor on 16.11.14.
 */
public interface IEntityRequester {

    public void requestUpdate(ETileEntity entity);
    public void blockDestroyed(ETileEntity entity);
    public int getGui(ETileEntity entity);
    public int isProvidingPower(ETileEntity entity);
    public boolean canProvidePower();
}

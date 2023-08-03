package sunsetsatellite.energyapi.template.tiles;


import sunsetsatellite.energyapi.impl.TileEntityEnergyConductor;
import sunsetsatellite.sunsetutils.util.Connection;
import sunsetsatellite.sunsetutils.util.Direction;

public class TileEntityWire extends TileEntityEnergyConductor {

    public TileEntityWire(){
        setCapacity(100);
        setEnergy(0);
        setTransfer(100);
        for (Direction dir : Direction.values()) {
            setConnection(dir, Connection.BOTH);
        }
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
    }
}

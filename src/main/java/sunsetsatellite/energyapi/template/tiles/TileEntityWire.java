package sunsetsatellite.energyapi.template.tiles;

import net.minecraft.src.TileEntity;
import sunsetsatellite.energyapi.EnergyAPI;
import sunsetsatellite.energyapi.api.IEnergySink;
import sunsetsatellite.energyapi.api.IEnergySource;
import sunsetsatellite.energyapi.impl.TileEntityEnergy;
import sunsetsatellite.energyapi.impl.TileEntityEnergyConductor;
import sunsetsatellite.energyapi.impl.TileEntityEnergySink;
import sunsetsatellite.energyapi.util.Connection;
import sunsetsatellite.energyapi.util.Direction;

public class TileEntityWire extends TileEntityEnergyConductor {

    public TileEntityWire(){
        setCapacity(100);
        setEnergy(0);
        setTransfer(100);
        for (Direction dir : Direction.values()) {
            setConnection(dir,Connection.BOTH);
        }
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
    }
}

package sunsetsatellite.energyapi.api;

import sunsetsatellite.energyapi.util.Direction;

public interface IEnergyConnection {
    boolean canConnect(Direction dir);
}

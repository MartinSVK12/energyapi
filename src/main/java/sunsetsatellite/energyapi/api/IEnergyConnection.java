package sunsetsatellite.energyapi.api;

import sunsetsatellite.sunsetutils.util.Connection;
import sunsetsatellite.sunsetutils.util.Direction;

public interface IEnergyConnection {
    boolean canConnect(Direction dir, Connection connection);
}

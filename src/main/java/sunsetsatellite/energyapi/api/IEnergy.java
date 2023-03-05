package sunsetsatellite.energyapi.api;

import sunsetsatellite.energyapi.util.Direction;

public interface IEnergy extends IEnergyConnection {

    int getEnergy();
    int getEnergy(Direction dir);
    int getCapacity();
    int getCapacity(Direction dir);

    void setEnergy(int amount);
    void modifyEnergy(int amount);
    void setCapacity(int amount);
}

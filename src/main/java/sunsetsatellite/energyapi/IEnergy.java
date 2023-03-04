package sunsetsatellite.energyapi;

public interface IEnergy extends IEnergyConnection {

    int getEnergy();
    int getEnergy(Direction dir);
    int getCapacity();
    int getCapacity(Direction dir);

    int getTransferRate();
    int getTransferRate(Direction dir);
}

package sunsetsatellite.energyapi;

public interface IEnergySource extends IEnergy {
    int provide(Direction dir, int amount, boolean test);
}

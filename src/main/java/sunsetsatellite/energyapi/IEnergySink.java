package sunsetsatellite.energyapi;

public interface IEnergySink extends IEnergy {
    int receive(Direction dir, int amount, boolean test);
}

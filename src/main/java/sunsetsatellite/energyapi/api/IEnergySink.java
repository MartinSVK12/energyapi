package sunsetsatellite.energyapi.api;

import net.minecraft.src.ItemStack;
import sunsetsatellite.energyapi.util.Direction;

public interface IEnergySink extends IEnergy {
    int receive(Direction dir, int amount, boolean test);
    int receive(ItemStack stack, int amount, boolean test);
    int getMaxReceive();
    int getMaxReceive(Direction dir);
    void setMaxReceive(int amount);
}

package sunsetsatellite.energyapi.api;

import net.minecraft.src.ItemStack;
import sunsetsatellite.sunsetutils.util.Direction;

public interface IEnergySource extends IEnergy {
    int provide(Direction dir, int amount, boolean test);
    int provide(ItemStack stack, int amount, boolean test);
    int getMaxProvide();
    int getMaxProvide(Direction dir);
    void setMaxProvide(int amount);
}

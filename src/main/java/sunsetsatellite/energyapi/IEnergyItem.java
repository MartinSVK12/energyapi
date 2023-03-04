package sunsetsatellite.energyapi;

import net.minecraft.src.ItemStack;

public interface IEnergyItem {

    int provide(ItemStack stack, int amount, boolean test);
    int receive(ItemStack stack, int amount, boolean test);

    int getEnergy(ItemStack stack);
    int getCapacity(ItemStack stack);
    int getTransferRate(ItemStack stack);
}

package sunsetsatellite.energyapi.interfaces.mixins;

import net.minecraft.src.ItemStack;

public interface IGuiContainer {
    void drawItemStack(ItemStack stack, int x, int y);
}

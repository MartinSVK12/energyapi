package sunsetsatellite.energyapi.interfaces.mixins;

import net.minecraft.src.Container;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.IInventory;

public interface IEntityPlayerMP {
    void displayGuiScreen(GuiScreen guiScreen, Container container, IInventory inventory);

    void updateEnergy(Container container, int energy, int capacity, int maxReceive, int maxProvide);
}

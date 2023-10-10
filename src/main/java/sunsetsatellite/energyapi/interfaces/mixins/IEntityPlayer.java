package sunsetsatellite.energyapi.interfaces.mixins;


import net.minecraft.client.gui.GuiScreen;
import net.minecraft.core.player.inventory.Container;
import net.minecraft.core.player.inventory.IInventory;

public interface IEntityPlayer {
    void displayGuiScreen_energyapi(GuiScreen guiScreen, Container container, IInventory inventory);

    void updateEnergy(Container container, int energy, int capacity, int maxReceive, int maxProvide);
}

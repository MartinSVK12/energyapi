package sunsetsatellite.energyapi.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.core.player.inventory.Container;
import net.minecraft.core.player.inventory.IInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import sunsetsatellite.energyapi.interfaces.mixins.IEntityPlayer;

@Mixin(value = EntityPlayerSP.class, remap = false)
public class EntityPlayerSPMixin implements IEntityPlayer {
    @Shadow
    protected Minecraft mc;
    @Override
    public void displayGuiScreen_energyapi(GuiScreen guiScreen, Container container, IInventory inventory) {
        mc.displayGuiScreen(guiScreen);
    }

    @Override
    public void updateEnergy(Container container, int energy, int capacity, int maxReceive, int maxProvide) {

    }
}

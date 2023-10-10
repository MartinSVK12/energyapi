package sunsetsatellite.energyapi.mixin;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.inventory.Container;
import net.minecraft.core.player.inventory.IInventory;
import org.spongepowered.asm.mixin.Mixin;
import sunsetsatellite.energyapi.interfaces.mixins.IEntityPlayer;

@Mixin(value = EntityPlayer.class, remap = false)
public class EntityPlayerMixin implements IEntityPlayer {
    @Override
    public void displayGuiScreen_energyapi(IInventory inventory) {

    }

    @Override
    public void updateEnergy(Container container, int energy, int capacity, int maxReceive, int maxProvide) {

    }
}

package sunsetsatellite.energyapi.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.core.player.inventory.Container;
import net.minecraft.core.player.inventory.IInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import sunsetsatellite.energyapi.EnergyAPI;
import sunsetsatellite.energyapi.interfaces.mixins.IEntityPlayer;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

@Mixin(value = EntityPlayerSP.class, remap = false)
public class EntityPlayerSPMixin implements IEntityPlayer {
    @Shadow
    protected Minecraft mc;
    @Override
    public void displayGuiScreen_energyapi(IInventory inventory) {
        ArrayList<Class<?>> list = EnergyAPI.nameToGuiMap.get(inventory.getInvName());
        try {
            mc.displayGuiScreen((GuiScreen) list.get(0).getDeclaredConstructors()[0].newInstance(this.mc.thePlayer.inventory,inventory));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateEnergy(Container container, int energy, int capacity, int maxReceive, int maxProvide) {

    }
}

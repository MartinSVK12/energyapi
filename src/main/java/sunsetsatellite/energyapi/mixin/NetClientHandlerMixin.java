package sunsetsatellite.energyapi.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sunsetsatellite.energyapi.EnergyAPI;
import sunsetsatellite.energyapi.interfaces.mixins.INetClientHandler;
import sunsetsatellite.energyapi.mp.packets.PacketUpdateEnergy;
import sunsetsatellite.energyapi.template.containers.ContainerEnergy;
import sunsetsatellite.energyapi.util.Config;

import java.lang.reflect.InvocationTargetException;

@Mixin(
        value= NetClientHandler.class,
        remap = false
)
public class NetClientHandlerMixin extends NetHandler implements INetClientHandler {

    @Shadow private Minecraft mc;

    @Inject(
            method = "handleOpenWindow",
            at = @At("TAIL")
    )
    public void handleOpenWindow(Packet100OpenWindow packet100openwindow, CallbackInfo ci) {
        if(packet100openwindow.inventoryType == Config.getFromConfig("GuiID",7)){
            TileEntity tile;
            try {
                tile = (TileEntity) EnergyAPI.nameToGuiMap.get(packet100openwindow.windowTitle).get(1).getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            try {
                this.mc.displayGuiScreen((GuiScreen) EnergyAPI.nameToGuiMap.get(packet100openwindow.windowTitle).get(0).getDeclaredConstructors()[0].newInstance(this.mc.thePlayer.inventory,tile));
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
            this.mc.thePlayer.craftingInventory.windowId = packet100openwindow.windowId;
        }
    }

    @Override
    public void handleUpdateEnergy(PacketUpdateEnergy packetUpdateEnergy) {
        this.handleInvalidPacket(packetUpdateEnergy);
        if (this.mc.thePlayer.craftingInventory instanceof ContainerEnergy && this.mc.thePlayer.craftingInventory.windowId == packetUpdateEnergy.windowId) {
            ((ContainerEnergy) this.mc.thePlayer.craftingInventory).updateClientEnergy(packetUpdateEnergy.energy, packetUpdateEnergy.capacity, packetUpdateEnergy.maxReceive, packetUpdateEnergy.maxProvide);
            //this.mc.thePlayer.craftingInventory.updateClientProgressBar(packet105updateprogressbar.progressBar, packet105updateprogressbar.progressBarValue);
        }
    }

    @Override
    public boolean isServerHandler() {
        return false;
    }
}

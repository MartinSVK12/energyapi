package sunsetsatellite.energyapi.mixin;


import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.net.handler.NetClientHandler;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.net.handler.NetHandler;
import net.minecraft.core.net.packet.Packet100OpenWindow;
import net.minecraft.core.player.inventory.Container;
import net.minecraft.core.player.inventory.IInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sunsetsatellite.energyapi.EnergyAPI;
import sunsetsatellite.energyapi.interfaces.mixins.IEntityPlayer;
import sunsetsatellite.energyapi.interfaces.mixins.INetClientHandler;
import sunsetsatellite.energyapi.mp.packets.PacketUpdateEnergy;
import sunsetsatellite.energyapi.template.containers.ContainerEnergy;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

@Mixin(
        value= NetClientHandler.class,
        remap = false
)
public class NetClientHandlerMixin extends NetHandler implements INetClientHandler {

    @Shadow
    private Minecraft mc;

    @Inject(
            method = "handleOpenWindow",
            at = @At("TAIL")
    )
    public void handleOpenWindow(Packet100OpenWindow packet100openwindow, CallbackInfo ci) {
        EnergyAPI.LOGGER.info(String.valueOf(packet100openwindow.inventoryType));
        if(packet100openwindow.inventoryType == EnergyAPI.config.getFromConfig("GuiID",10)){
            TileEntity tile;
            try {
                tile = (TileEntity) EnergyAPI.nameToGuiMap.get(packet100openwindow.windowTitle).get(1).getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            ((IEntityPlayer)mc.thePlayer).displayGuiScreen_energyapi((IInventory) tile);
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

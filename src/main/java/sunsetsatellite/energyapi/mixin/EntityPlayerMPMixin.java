package sunsetsatellite.energyapi.mixin;

import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import sunsetsatellite.energyapi.interfaces.mixins.IEntityPlayerMP;
import sunsetsatellite.energyapi.mp.packets.PacketUpdateEnergy;
import sunsetsatellite.energyapi.util.Config;

@Mixin(
        value = EntityPlayerMP.class,
        remap = false
)
public abstract class EntityPlayerMPMixin extends EntityPlayer implements IEntityPlayerMP, ICrafting {

    public EntityPlayerMPMixin(World world) {
        super(world);
    }

    @Shadow protected abstract void getNextWindowId();

    @Shadow public NetServerHandler playerNetServerHandler;

    @Shadow private int currentWindowId;

    @Override
    public void displayGuiScreen_energyapi(GuiScreen guiScreen, Container container, IInventory inventory) {
        this.getNextWindowId();
        this.playerNetServerHandler.sendPacket(new Packet100OpenWindow(this.currentWindowId, Config.getFromConfig("GuiID",7), inventory.getInvName(), inventory.getSizeInventory()));
        this.craftingInventory = container;
        this.craftingInventory.windowId = this.currentWindowId;
        this.craftingInventory.onContainerInit(((EntityPlayerMP)((Object)this)));
    }

    @Override
    public void updateEnergy(Container container, int energy, int capacity, int maxReceive, int maxProvide) {
        this.playerNetServerHandler.sendPacket(new PacketUpdateEnergy(container.windowId,energy,capacity,maxReceive,maxProvide));
    }

}

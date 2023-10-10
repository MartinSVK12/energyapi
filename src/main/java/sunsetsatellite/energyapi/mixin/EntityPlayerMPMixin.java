package sunsetsatellite.energyapi.mixin;


import net.minecraft.client.gui.GuiScreen;
import net.minecraft.core.crafting.ICrafting;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.net.packet.Packet100OpenWindow;
import net.minecraft.core.player.inventory.Container;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.world.World;
import net.minecraft.server.entity.player.EntityPlayerMP;
import net.minecraft.server.net.handler.NetServerHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import sunsetsatellite.energyapi.EnergyAPI;
import sunsetsatellite.energyapi.interfaces.mixins.IEntityPlayer;
import sunsetsatellite.energyapi.mp.packets.PacketUpdateEnergy;

@Mixin(
        value = EntityPlayerMP.class,
        remap = false
)
public abstract class EntityPlayerMPMixin extends EntityPlayer implements IEntityPlayer, ICrafting {

    public EntityPlayerMPMixin(World world) {
        super(world);
    }

    @Shadow
    protected abstract void getNextWindowId();

    @Shadow public NetServerHandler playerNetServerHandler;

    @Shadow private int currentWindowId;

    @Override
    public void displayGuiScreen_energyapi(GuiScreen guiScreen, Container container, IInventory inventory) {
        this.getNextWindowId();
        this.playerNetServerHandler.sendPacket(new Packet100OpenWindow(this.currentWindowId, EnergyAPI.config.getFromConfig("GuiID",10), inventory.getInvName(), inventory.getSizeInventory()));
        this.craftingInventory = container;
        this.craftingInventory.windowId = this.currentWindowId;
        this.craftingInventory.onContainerInit(((EntityPlayerMP)((Object)this)));
    }

    @Override
    public void updateEnergy(Container container, int energy, int capacity, int maxReceive, int maxProvide) {
        this.playerNetServerHandler.sendPacket(new PacketUpdateEnergy(container.windowId,energy,capacity,maxReceive,maxProvide));
    }

}

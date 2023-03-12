package sunsetsatellite.energyapi.template.containers;

import net.minecraft.src.*;
import sunsetsatellite.energyapi.impl.TileEntityEnergyConductor;
import sunsetsatellite.energyapi.interfaces.mixins.IEntityPlayerMP;
import sunsetsatellite.energyapi.template.tiles.TileEntityGenerator;

import java.util.Iterator;

public class ContainerEnergy extends Container {

    public int energy;
    public int capacity;
    public int maxProvide;
    public int maxReceive;

    public TileEntity tile;
    public void updateClientEnergy(int energy, int capacity, int maxReceive, int maxProvide){
        ((TileEntityEnergyConductor)tile).energy = energy;
        ((TileEntityEnergyConductor)tile).capacity = capacity;
        ((TileEntityEnergyConductor)tile).maxProvide = maxProvide;
        ((TileEntityEnergyConductor)tile).maxReceive = maxReceive;
    }

    @Override
    public void quickMoveItems(int i, EntityPlayer entityPlayer, boolean bl, boolean bl2) {

    }

    @Override
    public void updateInventory() {
        super.updateInventory();
        for(int i = 0; i < this.inventorySlots.size(); ++i) {
            ItemStack itemstack = this.inventorySlots.get(i).getStack();
            ItemStack itemstack1 = this.inventoryItemStacks.get(i);
            this.inventoryItemStacks.set(i, itemstack1);
            for (ICrafting crafter : this.crafters) {
                crafter.updateInventorySlot(this, i, itemstack);
            }
        }

        for (ICrafting crafter : this.crafters) {
            ((IEntityPlayerMP) crafter).updateEnergy(this, energy, capacity, maxReceive, maxProvide);
        }

        this.energy = ((TileEntityEnergyConductor)tile).energy;
        this.capacity = ((TileEntityEnergyConductor)tile).capacity;
        this.maxProvide = ((TileEntityEnergyConductor)tile).maxProvide;
        this.maxReceive = ((TileEntityEnergyConductor)tile).maxReceive;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer entityPlayer) {
        return false;
    }
}

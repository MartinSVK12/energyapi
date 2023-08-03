package sunsetsatellite.energyapi.template.containers;


import net.minecraft.core.crafting.ICrafting;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.player.inventory.slot.Slot;
import sunsetsatellite.energyapi.template.tiles.TileEntityMachine;

import java.util.Iterator;

public class ContainerMachine extends ContainerEnergy {

    private int currentCookTime;
    private int maxCookTime;

    public ContainerMachine(IInventory iInventory, TileEntityMachine tileEntity){
        tile = tileEntity;


        this.addSlot(new Slot(tileEntity, 0, 56, 17));
        this.addSlot(new Slot(tileEntity, 1, 56, 53));
        this.addSlot(new Slot(tileEntity, 2, 116, 35));


        for(int j = 0; j < 3; j++)
        {
            for(int i1 = 0; i1 < 9; i1++)
            {
                addSlot(new Slot(iInventory, i1 + j * 9 + 9, 8 + i1 * 18, 84 + j * 18));
            }

        }

        for(int k = 0; k < 9; k++)
        {
            addSlot(new Slot(iInventory, k, 8 + k * 18, 142));
        }
    }

    public void updateInventory() {
        super.updateInventory();
        Iterator var1 = this.crafters.iterator();

        while(var1.hasNext()) {
            Object crafter = var1.next();
            ICrafting icrafting = (ICrafting)crafter;

            if (this.currentCookTime != ((TileEntityMachine)tile).currentCookTime) {
                icrafting.updateCraftingInventoryInfo(this, 1, ((TileEntityMachine)tile).currentCookTime);
            }

            if (this.maxCookTime != ((TileEntityMachine)tile).maxCookTime) {
                icrafting.updateCraftingInventoryInfo(this, 3, ((TileEntityMachine)tile).maxCookTime);
            }
        }

        this.currentCookTime = ((TileEntityMachine)tile).currentCookTime;
        this.maxCookTime = ((TileEntityMachine)tile).maxCookTime;
    }

    public void updateClientProgressBar(int id, int value) {

        if (id == 1) {
            ((TileEntityMachine)tile).currentCookTime = value;
        }

        if (id == 3) {
            ((TileEntityMachine)tile).maxCookTime = value;
        }

    }


    @Override
    public boolean isUsableByPlayer(EntityPlayer entityPlayer1) {
        return true;
    }
}

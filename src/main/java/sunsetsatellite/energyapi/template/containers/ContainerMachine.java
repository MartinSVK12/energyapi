package sunsetsatellite.energyapi.template.containers;

import net.minecraft.src.*;
import sunsetsatellite.energyapi.template.tiles.TileEntityGenerator;
import sunsetsatellite.energyapi.template.tiles.TileEntityMachine;

public class ContainerMachine extends Container {

    TileEntity tile;

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

    @Override
    public void quickMoveItems(int i, EntityPlayer entityPlayer, boolean bl, boolean bl2) {

    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer entityPlayer1) {
        return true;
    }
}

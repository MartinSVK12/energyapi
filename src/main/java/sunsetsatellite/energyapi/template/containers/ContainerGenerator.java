package sunsetsatellite.energyapi.template.containers;

import net.minecraft.src.*;
import sunsetsatellite.energyapi.template.tiles.TileEntityBatteryBox;
import sunsetsatellite.energyapi.template.tiles.TileEntityGenerator;

public class ContainerGenerator extends Container {

    TileEntity tile;

    public ContainerGenerator(IInventory iInventory, TileEntityGenerator tileEntity){
        tile = tileEntity;


        addSlot(new Slot(tileEntity,0, 62 + 18,17 + 2 * 18));
        addSlot(new Slot(tileEntity,1, 62 + 18,17));
        addSlot(new Slot(tileEntity,2, 56,53));


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

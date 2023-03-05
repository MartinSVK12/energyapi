package sunsetsatellite.energyapi.template.blocks;

import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import sunsetsatellite.energyapi.template.gui.GuiBatteryBox;
import sunsetsatellite.energyapi.template.tiles.TileEntityBatteryBox;

public class BlockBatteryBox extends BlockContainerRotatable {
    public BlockBatteryBox(int i, Material material) {
        super(i, material);
    }

    public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer)
    {
        if(world.isMultiplayerAndNotHost)
        {
            return true;
        } else
        {
            TileEntityBatteryBox tile = (TileEntityBatteryBox) world.getBlockTileEntity(i, j, k);
            if(tile != null) {
                Minecraft.getMinecraft().displayGuiScreen(new GuiBatteryBox(entityplayer.inventory, tile));
            }
            return true;
        }
    }

    @Override
    protected TileEntity getBlockEntity() {
        return new TileEntityBatteryBox();
    }
}

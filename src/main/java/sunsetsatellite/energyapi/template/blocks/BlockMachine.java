package sunsetsatellite.energyapi.template.blocks;

import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import sunsetsatellite.energyapi.template.gui.GuiGenerator;
import sunsetsatellite.energyapi.template.gui.GuiMachine;
import sunsetsatellite.energyapi.template.tiles.TileEntityGenerator;
import sunsetsatellite.energyapi.template.tiles.TileEntityMachine;

public class BlockMachine extends BlockContainerRotatable {
    public BlockMachine(int i, Material material) {
        super(i, material);
    }

    public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer)
    {
        if(world.isMultiplayerAndNotHost)
        {
            return true;
        } else
        {
            TileEntityMachine tile = (TileEntityMachine) world.getBlockTileEntity(i, j, k);
            if(tile != null) {
                Minecraft.getMinecraft().displayGuiScreen(new GuiMachine(entityplayer.inventory, tile));
            }
            return true;
        }
    }

    @Override
    protected TileEntity getBlockEntity() {
        return new TileEntityMachine();
    }
}

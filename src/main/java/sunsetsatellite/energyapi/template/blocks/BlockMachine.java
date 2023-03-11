package sunsetsatellite.energyapi.template.blocks;

import net.minecraft.src.*;
import sunsetsatellite.energyapi.EnergyAPI;
import sunsetsatellite.energyapi.template.containers.ContainerMachine;
import sunsetsatellite.energyapi.template.gui.GuiMachine;
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
                EnergyAPI.displayGui(entityplayer,new GuiMachine(entityplayer.inventory, tile),new ContainerMachine(entityplayer.inventory,tile),tile);
            }
            return true;
        }
    }

    public void onBlockRemoval(World world, int i, int j, int k)
    {
        IInventory inv = (IInventory)world.getBlockTileEntity(i, j, k);
        label0:
        for(int l = 0; l < inv.getSizeInventory(); l++)
        {
            ItemStack itemstack = inv.getStackInSlot(l);
            if(itemstack == null)
            {
                continue;
            }
            float f = world.rand.nextFloat() * 0.8F + 0.1F;
            float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
            float f2 = world.rand.nextFloat() * 0.8F + 0.1F;
            do
            {
                if(itemstack.stackSize <= 0)
                {
                    continue label0;
                }
                int i1 = world.rand.nextInt(21) + 10;
                if(i1 > itemstack.stackSize)
                {
                    i1 = itemstack.stackSize;
                }
                itemstack.stackSize -= i1;
                EntityItem entityitem = new EntityItem(world, (float)i + f, (float)j + f1, (float)k + f2, new ItemStack(itemstack.itemID, i1, itemstack.getMetadata(), itemstack.tag));
                float f3 = 0.05F;
                entityitem.motionX = (float)world.rand.nextGaussian() * f3;
                entityitem.motionY = (float)world.rand.nextGaussian() * f3 + 0.2F;
                entityitem.motionZ = (float)world.rand.nextGaussian() * f3;
                world.entityJoinedWorld(entityitem);
            } while(true);
        }
        super.onBlockRemoval(world, i, j, k);
    }

    @Override
    protected TileEntity getBlockEntity() {
        return new TileEntityMachine();
    }
}

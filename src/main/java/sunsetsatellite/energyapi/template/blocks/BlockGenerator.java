package sunsetsatellite.energyapi.template.blocks;


import net.minecraft.client.Minecraft;
import net.minecraft.core.block.BlockTileEntityRotatable;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.world.World;
import sunsetsatellite.energyapi.EnergyAPI;
import sunsetsatellite.energyapi.interfaces.mixins.IEntityPlayer;
import sunsetsatellite.energyapi.template.containers.ContainerBatteryBox;
import sunsetsatellite.energyapi.template.containers.ContainerGenerator;
import sunsetsatellite.energyapi.template.gui.GuiBatteryBox;
import sunsetsatellite.energyapi.template.gui.GuiGenerator;
import sunsetsatellite.energyapi.template.tiles.TileEntityGenerator;

public class BlockGenerator extends BlockTileEntityRotatable {
    public BlockGenerator(String key, int i, Material material) {
        super(key, i, material);
    }

    public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer)
    {
        if(!world.isClientSide)
        {
            TileEntityGenerator tile = (TileEntityGenerator) world.getBlockTileEntity(i, j, k);
            if(tile != null) {
                ((IEntityPlayer)entityplayer).displayGuiScreen_energyapi(new GuiGenerator(entityplayer.inventory, tile),new ContainerGenerator(entityplayer.inventory,tile),tile);
            }
        }
        return true;
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
                EntityItem entityitem = new EntityItem(world, (float)i + f, (float)j + f1, (float)k + f2, new ItemStack(itemstack.itemID, i1, itemstack.getMetadata(), itemstack.getData()));
                float f3 = 0.05F;
                entityitem.xd = (float)world.rand.nextGaussian() * f3;
                entityitem.yd = (float)world.rand.nextGaussian() * f3 + 0.2F;
                entityitem.zd = (float)world.rand.nextGaussian() * f3;
                world.entityJoinedWorld(entityitem);
            } while(true);
        }
        super.onBlockRemoval(world, i, j, k);
    }

    @Override
    protected TileEntity getNewBlockEntity() {
        return new TileEntityGenerator();
    }
}

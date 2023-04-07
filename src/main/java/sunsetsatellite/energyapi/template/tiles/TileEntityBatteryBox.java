package sunsetsatellite.energyapi.template.tiles;

import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import sunsetsatellite.energyapi.EnergyAPI;
import sunsetsatellite.energyapi.api.IEnergySink;
import sunsetsatellite.energyapi.impl.ItemEnergyContainer;
import sunsetsatellite.energyapi.impl.TileEntityEnergyConductor;
import sunsetsatellite.energyapi.impl.TileEntityEnergySink;
import sunsetsatellite.energyapi.template.items.ItemBatteryUnlimited;
import sunsetsatellite.energyapi.template.items.ItemBatteryVoid;
import sunsetsatellite.sunsetutils.util.Connection;
import sunsetsatellite.sunsetutils.util.Direction;

import java.util.ArrayList;

public class TileEntityBatteryBox extends TileEntityEnergyConductor
    implements IInventory {
    public TileEntityBatteryBox(){
        setCapacity(50000);
        setEnergy(0);
        setTransfer(250);
        contents = new ItemStack[2];
        for (Direction dir : Direction.values()) {
            setConnection(dir,Connection.OUTPUT);
        }
        setConnection(Direction.Y_POS,Connection.INPUT);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        /*if(getStackInSlot(0) != null && getStackInSlot(1) != null){
            if(getStackInSlot(0).getItem() instanceof ItemBatteryUnlimited && getStackInSlot(1).getItem() instanceof ItemBatteryVoid){
                if(getEnergy() <= 0){
                    //we do a little trolling :)
                    worldObj.createExplosion(null,xCoord,yCoord,zCoord,4.0f);
                    Minecraft.getMinecraft().displayGuiScreen(null);
                }
            }
        }*/
        if(getStackInSlot(1) != null && getStackInSlot(1).getItem() instanceof ItemEnergyContainer){
            ItemStack stack = getStackInSlot(1);
            ItemEnergyContainer item = (ItemEnergyContainer) getStackInSlot(1).getItem();
            provide(stack,getMaxProvide(),false);
        }
        if(getStackInSlot(0) != null && getStackInSlot(0).getItem() instanceof ItemEnergyContainer) {
            ItemStack stack = getStackInSlot(0);
            ItemEnergyContainer item = (ItemEnergyContainer) getStackInSlot(0).getItem();
            receive(stack,getMaxReceive(),false);
        }
    }

    public int getSizeInventory()
    {
        return contents.length;
    }

    public ItemStack getStackInSlot(int i)
    {
        return contents[i];
    }

    public ItemStack decrStackSize(int i, int j)
    {
        if(contents[i] != null)
        {
            if(contents[i].stackSize <= j)
            {
                ItemStack itemstack = contents[i];
                contents[i] = null;
                onInventoryChanged();
                return itemstack;
            }
            ItemStack itemstack1 = contents[i].splitStack(j);
            if(contents[i].stackSize == 0)
            {
                contents[i] = null;
            }
            onInventoryChanged();
            return itemstack1;
        } else
        {
            return null;
        }
    }


    public void setInventorySlotContents(int i, ItemStack itemstack)
    {
        contents[i] = itemstack;
        if(itemstack != null && itemstack.stackSize > getInventoryStackLimit())
        {
            itemstack.stackSize = getInventoryStackLimit();
        }
        onInventoryChanged();

    }

    public void onInventoryChanged() {
        super.onInventoryChanged();
    }

    public String getInvName()
    {
        return "Battery Box";
    }

    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readFromNBT(nbttagcompound);
        NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
        contents = new ItemStack[getSizeInventory()];
        for(int i = 0; i < nbttaglist.tagCount(); i++)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 0xff;
            if(j < contents.length)
            {
                contents[j] = new ItemStack(nbttagcompound1);
            }
        }
    }

    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);
        NBTTagList nbttaglist = new NBTTagList();
        for(int i = 0; i < contents.length; i++)
        {
            if(contents[i] != null)
            {

                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                contents[i].writeToNBT(nbttagcompound1);
                nbttaglist.setTag(nbttagcompound1);
            }
        }
        nbttagcompound.setTag("Items", nbttaglist);
    }

    public int getInventoryStackLimit()
    {
        return 64;
    }

    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        if(worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this)
        {
            return false;
        }
        return entityplayer.getDistanceSq((double)xCoord + 0.5D, (double)yCoord + 0.5D, (double)zCoord + 0.5D) <= 64D;
    }

    private ItemStack[] contents;
}

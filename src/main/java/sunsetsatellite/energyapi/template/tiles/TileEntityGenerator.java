package sunsetsatellite.energyapi.template.tiles;

import net.minecraft.src.*;
import sunsetsatellite.energyapi.EnergyAPI;
import sunsetsatellite.energyapi.api.IEnergySink;
import sunsetsatellite.energyapi.api.LookupFuelEnergy;
import sunsetsatellite.energyapi.impl.*;
import sunsetsatellite.energyapi.util.Connection;
import sunsetsatellite.energyapi.util.Direction;

public class TileEntityGenerator extends TileEntityEnergyConductor
    implements IInventory {
    public TileEntityGenerator(){
        setCapacity(10000);
        setEnergy(0);
        setTransfer(250);
        contents = new ItemStack[3];
        for (Direction dir : Direction.values()) {
            setConnection(dir,Connection.OUTPUT);
        }
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (this.currentBurnTime > 0) {
            --this.currentBurnTime;
            modifyEnergy(getEnergyYieldForItem(currentFuel));
        }

        if (this.currentBurnTime == 0) {
            this.maxBurnTime = this.currentBurnTime = this.getBurnTimeFromItem(this.contents[2]) / 5;
            if (this.currentBurnTime > 0) {
                currentFuel = this.contents[2];
                onInventoryChanged();
                if (this.contents[2] != null) {
                    --this.contents[2].stackSize;
                    if (this.contents[2].stackSize == 0) {
                        this.contents[2] = null;
                    }
                }
            } else {
                currentFuel = null;
            }
        }


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
        this.currentBurnTime = nbttagcompound.getInteger("BurnTime");
        this.maxBurnTime = nbttagcompound.getInteger("MaxBurnTime");
        currentFuel = new ItemStack(nbttagcompound.getCompoundTag("CurrentFuel"));
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
        NBTTagCompound fuel = new NBTTagCompound();
        if(currentFuel != null){
            currentFuel.writeToNBT(fuel);
        }
        nbttagcompound.setTag("Items", nbttaglist);
        nbttagcompound.setCompoundTag("CurrentFuel",fuel);
        nbttagcompound.setInteger("BurnTime", (short)this.currentBurnTime);
        nbttagcompound.setInteger("MaxBurnTime", (short)this.maxBurnTime);
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

    private int getBurnTimeFromItem(ItemStack itemStack) {
        return itemStack == null ? 0 : LookupFuelFurnace.fuelFurnace().getFuelYield(itemStack.getItem().itemID);
    }

    private int getEnergyYieldForItem(ItemStack itemStack) {
        return itemStack == null ? 0 : LookupFuelEnergy.fuelEnergy().getEnergyYield(itemStack.getItem().itemID);
    }

    public int getBurnTimeRemainingScaled(int i) {
        return this.maxBurnTime == 0 ? 0 : this.currentBurnTime * i / this.maxBurnTime;
    }

    public boolean isBurning() {
        return this.currentBurnTime > 0;
    }

    private ItemStack[] contents;
    public int maxBurnTime = 0;
    public int currentBurnTime = 0;
    public ItemStack currentFuel;
}

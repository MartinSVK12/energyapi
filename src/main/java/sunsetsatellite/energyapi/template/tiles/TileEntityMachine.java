package sunsetsatellite.energyapi.template.tiles;

import net.minecraft.src.*;
import sunsetsatellite.energyapi.impl.ItemEnergyContainer;
import sunsetsatellite.energyapi.impl.TileEntityEnergyConductor;
import sunsetsatellite.energyapi.util.Connection;
import sunsetsatellite.energyapi.util.Direction;

public class TileEntityMachine extends TileEntityEnergyConductor
    implements IInventory {
    public TileEntityMachine(){
        setCapacity(3000);
        setEnergy(0);
        setTransfer(250);
        contents = new ItemStack[3];
        for (Direction dir : Direction.values()) {
            setConnection(dir,Connection.INPUT);
        }
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if(getStackInSlot(1) != null && getStackInSlot(1).getItem() instanceof ItemEnergyContainer) {
            ItemStack stack = getStackInSlot(1);
            ItemEnergyContainer item = (ItemEnergyContainer) getStackInSlot(1).getItem();
            receive(stack,getMaxReceive(),false);
        }

        if (this.enoughEnergy() && this.canSmelt()) {
            modifyEnergy(-usage);
        }

        if (this.enoughEnergy() && this.canSmelt()) {
            ++this.currentCookTime;
            if (this.currentCookTime == this.maxCookTime) {
                this.currentCookTime = 0;
                this.smeltItem();
                onInventoryChanged();
            }
        } else {
            this.currentCookTime = 0;
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
        return "Energy Machine";
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
        this.currentCookTime = nbttagcompound.getInteger("CookTime");
        this.maxCookTime = nbttagcompound.getInteger("MaxCookTime");
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
        nbttagcompound.setInteger("CookTime", (short)this.currentCookTime);
        nbttagcompound.setInteger("MaxCookTime", (short)this.maxCookTime);
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


    public int getCookProgressScaled(int i) {
        return this.maxCookTime == 0 ? 0 : this.currentCookTime * i / this.maxCookTime;
    }

    private boolean canSmelt() {
        if (this.contents[0] == null) {
            return false;
        } else {
            ItemStack itemstack = RecipesFurnace.smelting().getSmeltingResult(this.contents[0].getItem().itemID);
            if (itemstack == null) {
                return false;
            } else if (this.contents[2] == null) {
                return true;
            } else if (!this.contents[2].isItemEqual(itemstack)) {
                return false;
            } else if (this.contents[2].stackSize < this.getInventoryStackLimit() && this.contents[2].stackSize < this.contents[2].getMaxStackSize()) {
                return true;
            } else {
                return this.contents[2].stackSize < itemstack.getMaxStackSize();
            }
        }
    }

    public void smeltItem() {
        if (this.canSmelt()) {
            ItemStack itemstack = RecipesFurnace.smelting().getSmeltingResult(this.contents[0].getItem().itemID);
            if (this.contents[2] == null) {
                this.contents[2] = itemstack.copy();
            } else if (this.contents[2].itemID == itemstack.itemID) {
                ++this.contents[2].stackSize;
            }

            --this.contents[0].stackSize;
            if (this.contents[0].stackSize <= 0) {
                this.contents[0] = null;
            }

        }
    }

    public boolean enoughEnergy() {
        return this.energy >= usage;
    }

    private ItemStack[] contents;
    public int maxCookTime = 200;
    public int currentCookTime = 0;
    public int usage = 5;
}

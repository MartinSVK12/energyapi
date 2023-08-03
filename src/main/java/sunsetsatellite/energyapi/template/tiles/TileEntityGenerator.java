package sunsetsatellite.energyapi.template.tiles;


import com.mojang.nbt.CompoundTag;
import com.mojang.nbt.ListTag;
import net.minecraft.core.crafting.LookupFuelFurnace;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.IInventory;
import sunsetsatellite.energyapi.api.LookupFuelEnergy;
import sunsetsatellite.energyapi.impl.ItemEnergyContainer;
import sunsetsatellite.energyapi.impl.TileEntityEnergyConductor;
import sunsetsatellite.sunsetutils.util.Connection;
import sunsetsatellite.sunsetutils.util.Direction;

public class TileEntityGenerator extends TileEntityEnergyConductor
    implements IInventory {
    public TileEntityGenerator(){
        setCapacity(10000);
        setEnergy(0);
        setTransfer(250);
        contents = new ItemStack[3];
        for (Direction dir : Direction.values()) {
            setConnection(dir, Connection.OUTPUT);
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
            onInventoryChanged();
        }
        if(getStackInSlot(0) != null && getStackInSlot(0).getItem() instanceof ItemEnergyContainer) {
            ItemStack stack = getStackInSlot(0);
            ItemEnergyContainer item = (ItemEnergyContainer) getStackInSlot(0).getItem();
            receive(stack,getMaxReceive(),false);
            onInventoryChanged();
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
        return "Generator";
    }

    public void readFromNBT(CompoundTag CompoundTag)
    {
        super.readFromNBT(CompoundTag);
        ListTag ListTag = CompoundTag.getList("Items");
        contents = new ItemStack[getSizeInventory()];
        for(int i = 0; i < ListTag.tagCount(); i++)
        {
            CompoundTag CompoundTag1 = (CompoundTag)ListTag.tagAt(i);
            int j = CompoundTag1.getByte("Slot") & 0xff;
            if(j < contents.length)
            {
                contents[j] = ItemStack.readItemStackFromNbt(CompoundTag1);
            }
        }
        this.currentBurnTime = CompoundTag.getInteger("BurnTime");
        this.maxBurnTime = CompoundTag.getInteger("MaxBurnTime");
        currentFuel = ItemStack.readItemStackFromNbt(CompoundTag.getCompound("CurrentFuel"));
    }


    public void writeToNBT(CompoundTag CompoundTag)
    {
        super.writeToNBT(CompoundTag);
        ListTag ListTag = new ListTag();
        for(int i = 0; i < contents.length; i++)
        {
            if(contents[i] != null)
            {

                CompoundTag CompoundTag1 = new CompoundTag();
                CompoundTag1.putByte("Slot", (byte)i);
                contents[i].writeToNBT(CompoundTag1);
                ListTag.addTag(CompoundTag1);
            }
        }
        CompoundTag fuel = new CompoundTag();
        if(currentFuel != null){
            currentFuel.writeToNBT(fuel);
        }
        CompoundTag.put("Items", ListTag);
        CompoundTag.putCompound("CurrentFuel",fuel);
        CompoundTag.putInt("BurnTime", (short)this.currentBurnTime);
        CompoundTag.putInt("MaxBurnTime", (short)this.maxBurnTime);
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
        return entityplayer.distanceToSqr((double)xCoord + 0.5D, (double)yCoord + 0.5D, (double)zCoord + 0.5D) <= 64D;
    }

    private int getBurnTimeFromItem(ItemStack itemStack) {
        return itemStack == null ? 0 : LookupFuelFurnace.instance.getFuelYield(itemStack.getItem().id);
    }

    private int getEnergyYieldForItem(ItemStack itemStack) {
        return itemStack == null ? 0 : LookupFuelEnergy.fuelEnergy().getEnergyYield(itemStack.getItem().id);
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

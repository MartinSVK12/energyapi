package sunsetsatellite.energyapi.impl;

import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;
import sunsetsatellite.energyapi.api.IEnergy;
import sunsetsatellite.energyapi.util.Direction;

public class TileEntityEnergy extends TileEntity implements IEnergy {
    public int energy = 0;
    public int capacity = 0;

    @Override
    public int getEnergy() {
        return energy;
    }

    @Override
    public int getEnergy(Direction dir) {
        if(dir.getTileEntity(worldObj,this) instanceof IEnergy){
            return ((IEnergy)dir.getTileEntity(worldObj,this)).getEnergy();
        }
        return 0;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {
        nbttagcompound.setInteger("energy",energy);
        nbttagcompound.setInteger("capacity",capacity);
        super.writeToNBT(nbttagcompound);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        energy = nbttagcompound.getInteger("energy");
        capacity = nbttagcompound.getInteger("capacity");
        super.readFromNBT(nbttagcompound);
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public int getCapacity(Direction dir) {
        if(dir.getTileEntity(worldObj,this) instanceof IEnergy){
            return ((IEnergy)dir.getTileEntity(worldObj,this)).getCapacity();
        }
        return 0;
    }

    @Override
    public void setEnergy(int amount) {
        energy = amount;
        if (this.energy > this.capacity) {
            this.energy = this.capacity;
        } else if (this.energy < 0) {
            this.energy = 0;
        }
    }

    @Override
    public void modifyEnergy(int amount) {
        energy += amount;
        if (this.energy > this.capacity) {
            this.energy = this.capacity;
        } else if (this.energy < 0) {
            this.energy = 0;
        }
    }

    @Override
    public void setCapacity(int amount) {
        capacity = amount;
    }

    @Override
    public boolean canConnect(Direction dir) {
        return false;
    }
}

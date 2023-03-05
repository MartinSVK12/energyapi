package sunsetsatellite.energyapi.impl;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import sunsetsatellite.energyapi.api.IEnergySink;
import sunsetsatellite.energyapi.api.IEnergySource;
import sunsetsatellite.energyapi.util.Direction;

public class TileEntityEnergyConductor extends TileEntityEnergy implements IEnergySink, IEnergySource {
    public int maxReceive = 0;
    public int maxProvide = 0;

    @Override
    public int receive(Direction dir, int amount, boolean test) {
        if(canConnect(dir)){
            int received = Math.min(this.capacity - this.energy, Math.min(this.maxReceive, amount));
            if(!test){
                energy += received;
            }
            return received;
        }
        return 0;
    }

    @Override
    public int getMaxReceive() {
        return maxReceive;
    }

    @Override
    public int getMaxReceive(Direction dir) {
        if(dir.getTileEntity(worldObj,this) instanceof IEnergySink){
            return ((IEnergySink)dir.getTileEntity(worldObj,this)).getMaxReceive();
        }
        return 0;
    }

    @Override
    public void setMaxReceive(int amount) {
        maxReceive = amount;
    }

    @Override
    public int provide(Direction dir, int amount, boolean test) {
        if(canConnect(dir)){
            int provided = Math.min(this.energy, Math.min(this.maxProvide, amount));
            if(!test){
                energy -= provided;
            }
            return provided;
        }
        return 0;
    }

    @Override
    public int provide(ItemStack stack, int amount, boolean test){
        if(stack.getItem() instanceof ItemEnergyContainer){
            int provided = Math.min(this.energy, Math.min(this.maxProvide, amount));
            int received = ((ItemEnergyContainer) stack.getItem()).receive(stack,amount,true);
            int actual = Math.min(provided,received);
            if(!test){
                energy -= actual;
                ((ItemEnergyContainer) stack.getItem()).receive(stack,actual,false);
            }
            return actual;
        }
        return 0;
    }

    @Override
    public int receive(ItemStack stack, int amount, boolean test){
        if(stack.getItem() instanceof ItemEnergyContainer){
            int received = Math.min(this.capacity - this.energy, Math.min(this.maxReceive, amount));
            int provided = ((ItemEnergyContainer) stack.getItem()).provide(stack,amount,true);
            int actual = Math.min(provided,received);
            if(!test){
                energy += actual;
                ((ItemEnergyContainer) stack.getItem()).provide(stack,actual,false);
            }
            return actual;
        }
        return 0;
    }

    @Override
    public int getMaxProvide() {
        return maxProvide;
    }

    @Override
    public int getMaxProvide(Direction dir) {
        if(dir.getTileEntity(worldObj,this) instanceof IEnergySource){
            return ((IEnergySource)dir.getTileEntity(worldObj,this)).getMaxProvide();
        }
        return 0;
    }

    @Override
    public void setMaxProvide(int amount) {
        maxProvide = amount;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {
        nbttagcompound.setInteger("maxReceive",maxReceive);
        nbttagcompound.setInteger("maxProvide",maxProvide);
        super.writeToNBT(nbttagcompound);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        maxReceive = nbttagcompound.getInteger("maxReceive");
        maxProvide = nbttagcompound.getInteger("maxProvide");
        super.readFromNBT(nbttagcompound);
    }

    public void setTransfer(int amount){
        maxProvide = amount;
        maxReceive = amount;
    }
}

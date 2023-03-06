package sunsetsatellite.energyapi.impl;

import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import sunsetsatellite.energyapi.api.IEnergySource;
import sunsetsatellite.energyapi.util.Connection;
import sunsetsatellite.energyapi.util.Direction;

public class TileEntityEnergySource extends TileEntityEnergy implements IEnergySource {

    public int maxProvide = 0;
    @Override
    public int provide(Direction dir, int amount, boolean test) {
        if(canConnect(dir, Connection.OUTPUT)){
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
        nbttagcompound.setInteger("maxProvide",maxProvide);
        super.writeToNBT(nbttagcompound);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        maxProvide = nbttagcompound.getInteger("maxProvide");
        super.readFromNBT(nbttagcompound);
    }
}

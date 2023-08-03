package sunsetsatellite.energyapi.impl;


import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import sunsetsatellite.energyapi.api.IEnergyItem;

public class ItemEnergyContainer extends Item implements IEnergyItem {
    public ItemEnergyContainer(int i) {
        super(i);
    }

    public int baseCapacity = 0;
    public int baseProvide = 0;
    public int baseReceive = 0;

    @Override
    public int provide(ItemStack stack, int amount, boolean test) {
        int provided = Math.min(getEnergy(stack), Math.min(getMaxProvide(stack), amount));
        if(!test){
            modifyEnergy(stack,-provided);
        }
        return provided;
    }

    @Override
    public int receive(ItemStack stack, int amount, boolean test) {
        int received = Math.min(getCapacity(stack) - getEnergy(stack), Math.min(getMaxReceive(stack), amount));
        if(!test){
            modifyEnergy(stack,received);
        }
        return received;
    }

    @Override
    public int getEnergy(ItemStack stack) {
        return stack.tag.getInteger("energy");
    }

    @Override
    public int getCapacity(ItemStack stack) {
        if(!stack.tag.containsKey("capacity")){
            stack.tag.putInt("capacity",baseCapacity);
            return baseCapacity;
        }
        return stack.tag.getInteger("capacity");
    }

    @Override
    public int getMaxReceive(ItemStack stack) {
        if(!stack.tag.containsKey("maxReceive")){
            stack.tag.putInt("maxReceive",baseReceive);
            return baseReceive;

        }
        return stack.tag.getInteger("maxReceive");
    }

    @Override
    public int getMaxProvide(ItemStack stack) {
        if(!stack.tag.containsKey("maxProvide")){
            stack.tag.putInt("maxProvide",baseProvide);
            return baseProvide;
        }
        return stack.tag.getInteger("maxProvide");
    }

    @Override
    public void modifyEnergy(ItemStack stack, int amount) {
        stack.tag.putInt("energy",getEnergy(stack)+amount);
        if(stack.tag.getInteger("energy") > getCapacity(stack)){
            stack.tag.putInt("energy",getCapacity(stack));
        }
        if(stack.tag.getInteger("energy") < 0){
            stack.tag.putInt("energy",0);
        }
    }
}

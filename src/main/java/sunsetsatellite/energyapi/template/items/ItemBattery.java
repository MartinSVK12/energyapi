package sunsetsatellite.energyapi.template.items;

import net.minecraft.src.ItemStack;
import net.minecraft.src.StringTranslate;
import net.minecraft.src.command.ChatColor;
import sunsetsatellite.energyapi.EnergyAPI;
import sunsetsatellite.energyapi.impl.ItemEnergyContainer;
import sunsetsatellite.energyapi.util.ICustomDescription;

public class ItemBattery extends ItemEnergyContainer implements ICustomDescription {
    public ItemBattery(int i) {
        super(i);
        baseCapacity = 1000;
        baseProvide = 10;
        baseReceive = 10;
    }

    @Override
    public int getIconIndex(ItemStack itemstack) {
        float energy = getEnergy(itemstack);
        float capacity = getCapacity(itemstack);
        int mapped = (int) EnergyAPI.map(energy / capacity,0,1,0,6);
        setIconCoord(EnergyAPI.batteryTex[mapped][0],EnergyAPI.batteryTex[mapped][1]);
        return super.getIconIndex(itemstack);
    }

    @Override
    public String getDescription(ItemStack stack) {
        StringBuilder text = new StringBuilder();
        StringTranslate trans = StringTranslate.getInstance();
        ItemEnergyContainer item = (ItemEnergyContainer) stack.getItem();
        return text.append(ChatColor.white).append(trans.translateKey("energyapi.energy")).append(": ").append(ChatColor.lightGray).append(item.getEnergy(stack)).append(" ").append(trans.translateKey("energyapi.suffix")).append("/").append(item.getCapacity(stack)).append(" ").append(trans.translateKey("energyapi.suffix")).append("\n").append(ChatColor.white).append("Max Transfer: ").append(ChatColor.lightGray).append(item.getMaxReceive(stack)).append(" ").append(trans.translateKey("energyapi.suffix")).append(" IN ").append("| ").append(item.getMaxProvide(stack)).append(" ").append(trans.translateKey("energyapi.suffix")).append(" OUT").toString();

    }
}

package sunsetsatellite.energyapi.template.items;


import net.minecraft.core.item.ItemStack;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.net.command.TextFormatting;
import sunsetsatellite.energyapi.EnergyAPI;
import sunsetsatellite.energyapi.impl.ItemEnergyContainer;
import sunsetsatellite.sunsetutils.util.ICustomDescription;

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
        I18n trans = I18n.getInstance();
        ItemEnergyContainer item = (ItemEnergyContainer) stack.getItem();
        return text.append(TextFormatting.WHITE).append(EnergyAPI.ENERGY_NAME).append(": ").append(TextFormatting.LIGHT_GRAY).append(item.getEnergy(stack)).append(" ").append(EnergyAPI.ENERGY_SUFFIX).append("/").append(item.getCapacity(stack)).append(" ").append(EnergyAPI.ENERGY_SUFFIX).append("\n").append(TextFormatting.WHITE).append("Max Transfer: ").append(TextFormatting.LIGHT_GRAY).append(item.getMaxReceive(stack)).append(" ").append(EnergyAPI.ENERGY_SUFFIX).append(" IN ").append("| ").append(item.getMaxProvide(stack)).append(" ").append(EnergyAPI.ENERGY_SUFFIX).append(" OUT").toString();

    }
}

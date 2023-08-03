package sunsetsatellite.energyapi.template.items;


import net.minecraft.core.item.ItemStack;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.net.command.TextFormatting;
import sunsetsatellite.energyapi.EnergyAPI;
import sunsetsatellite.energyapi.impl.ItemEnergyContainer;
import sunsetsatellite.sunsetutils.util.ICustomDescription;

public class ItemBatteryUnlimited extends ItemEnergyContainer implements ICustomDescription {
    public ItemBatteryUnlimited(int i) {
        super(i);
        baseCapacity = Integer.MAX_VALUE;
        baseProvide = Integer.MAX_VALUE;
        baseReceive = Integer.MAX_VALUE;
    }

    @Override
    public int getEnergy(ItemStack stack) {
        return Integer.MAX_VALUE;
    }

    @Override
    public String getDescription(ItemStack stack) {
        StringBuilder text = new StringBuilder();
        I18n trans = I18n.getInstance();
        ItemEnergyContainer item = (ItemEnergyContainer) stack.getItem();
        return text.append(TextFormatting.WHITE).append(EnergyAPI.ENERGY_NAME).append(": ").append(TextFormatting.MAGENTA).append("Infinite").append(TextFormatting.LIGHT_GRAY).append(" ").append(EnergyAPI.ENERGY_SUFFIX).append("\n").append(TextFormatting.WHITE).append("Max Transfer: ").append(TextFormatting.GRAY).append("No").append(TextFormatting.LIGHT_GRAY).append(" ").append(EnergyAPI.ENERGY_SUFFIX).append(" IN ").append("| ").append(TextFormatting.MAGENTA).append("Yes").append(TextFormatting.LIGHT_GRAY).append(" ").append(EnergyAPI.ENERGY_SUFFIX).append(" OUT").toString();
    }
}

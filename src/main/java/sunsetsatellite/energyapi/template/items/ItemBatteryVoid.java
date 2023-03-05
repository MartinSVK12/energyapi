package sunsetsatellite.energyapi.template.items;

import net.minecraft.src.ItemStack;
import net.minecraft.src.StringTranslate;
import net.minecraft.src.command.ChatColor;
import sunsetsatellite.energyapi.impl.ItemEnergyContainer;
import sunsetsatellite.energyapi.util.ICustomDescription;

public class ItemBatteryVoid extends ItemEnergyContainer implements ICustomDescription {
    public ItemBatteryVoid(int i) {
        super(i);
        baseCapacity = Integer.MAX_VALUE;
        baseProvide = Integer.MAX_VALUE;
        baseReceive = Integer.MAX_VALUE;
    }

    @Override
    public int getEnergy(ItemStack stack) {
        return 0;
    }

    @Override
    public String getDescription(ItemStack stack) {
        StringBuilder text = new StringBuilder();
        StringTranslate trans = StringTranslate.getInstance();
        ItemEnergyContainer item = (ItemEnergyContainer) stack.getItem();
        return text.append(ChatColor.white).append(trans.translateKey("energyapi.energy")).append(": ").append(ChatColor.gray).append("No").append(ChatColor.lightGray).append(" ").append(trans.translateKey("energyapi.suffix")).append("\n").append(ChatColor.white).append("Max Transfer: ").append(ChatColor.magenta).append("Yes").append(ChatColor.lightGray).append(" ").append(trans.translateKey("energyapi.suffix")).append(" IN ").append("| ").append(ChatColor.gray).append("No").append(ChatColor.lightGray).append(" ").append(trans.translateKey("energyapi.suffix")).append(" OUT").toString();
    }
}

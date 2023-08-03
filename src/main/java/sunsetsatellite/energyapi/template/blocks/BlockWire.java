package sunsetsatellite.energyapi.template.blocks;


import net.minecraft.core.block.BlockTileEntity;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.net.command.TextFormatting;
import sunsetsatellite.energyapi.EnergyAPI;
import sunsetsatellite.energyapi.template.tiles.TileEntityWire;
import sunsetsatellite.sunsetutils.util.ICustomDescription;

public class BlockWire extends BlockTileEntity implements ICustomDescription {
    public BlockWire(String key, int i, Material material) {
        super(key, i, material);
    }

    @Override
    protected TileEntity getNewBlockEntity() {
        return new TileEntityWire();
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public String getDescription(ItemStack stack) {
        StringBuilder text = new StringBuilder();
        I18n trans = I18n.getInstance();
        return text.append(TextFormatting.WHITE).append("Max Transfer: ").append(TextFormatting.LIGHT_GRAY).append(100).append(" ").append(EnergyAPI.ENERGY_SUFFIX).append(" IN ").append("| ").append(100).append(" ").append(EnergyAPI.ENERGY_SUFFIX).append(" OUT").toString();
    }
}

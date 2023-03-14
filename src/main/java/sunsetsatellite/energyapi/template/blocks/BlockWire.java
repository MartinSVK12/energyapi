package sunsetsatellite.energyapi.template.blocks;

import net.minecraft.src.*;
import net.minecraft.src.command.ChatColor;
import sunsetsatellite.energyapi.template.tiles.TileEntityWire;
import sunsetsatellite.energyapi.util.Config;
import sunsetsatellite.energyapi.util.ICustomDescription;

public class BlockWire extends BlockContainer implements ICustomDescription {
    public BlockWire(int i, Material material) {
        super(i, material);
    }

    @Override
    protected TileEntity getBlockEntity() {
        return new TileEntityWire();
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer)
    {
        return false;
    }

    public int getRenderType() {
        return 31;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public String getDescription(ItemStack stack) {
        StringBuilder text = new StringBuilder();
        StringTranslate trans = StringTranslate.getInstance();
        return text.append(ChatColor.white).append("Max Transfer: ").append(ChatColor.lightGray).append(100).append(" ").append(Config.getFromConfig("energySuffix","E")).append(" IN ").append("| ").append(100).append(" ").append(Config.getFromConfig("energySuffix","E")).append(" OUT").toString();
    }
}

package sunsetsatellite.energyapi.template.gui;

import net.minecraft.src.GuiContainer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.StringTranslate;
import net.minecraft.src.TileEntity;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import sunsetsatellite.energyapi.EnergyAPI;
import sunsetsatellite.energyapi.template.containers.ContainerBatteryBox;
import sunsetsatellite.energyapi.template.tiles.TileEntityBatteryBox;

public class GuiBatteryBox extends GuiContainer {

    String name = "Battery Box";
    TileEntityBatteryBox tile;
    public GuiBatteryBox(InventoryPlayer inventoryPlayer, TileEntityBatteryBox tile) {
        super(new ContainerBatteryBox(inventoryPlayer,tile));
        this.tile = tile;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f)
    {
        int i = mc.renderEngine.getTexture("assets/energyapi/gui/batterybox.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(i);
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;
        drawTexturedModalRect(j, k, 0, 0, xSize, ySize);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        int color;
        //1 (red, empty) -> 0.65 (green, full)
        double color_mapped = EnergyAPI.map((float)tile.energy/(float)tile.capacity,0,1,1,0.65);
        double x_mapped = EnergyAPI.map((float)tile.energy/(float)tile.capacity, 0,1,0,15);
        Color c = new Color();
        c.fromHSB((float) color_mapped,1.0F,1.0F);
        color = c.getAlpha() << 24 | c.getRed() << 16 | c.getBlue() << 8 | c.getGreen();
        drawRectBetter(x+80,y+40, (int) x_mapped,7,color);
        GL11.glEnable(3553);
    }

    @Override
    public void drawScreen(int x, int y, float renderPartialTicks) {
        int i = (width - xSize) / 2;
        int j = (height - ySize) / 2;
        super.drawScreen(x, y, renderPartialTicks);
        StringTranslate trans = StringTranslate.getInstance();
        StringBuilder text = new StringBuilder();
        if(x > i+80 && x < i+94){
            if(y > j+40 && y < j+46){
                text.append(trans.translateKey("energyapi.energy")).append(": ").append(tile.energy).append(" ").append(trans.translateKey("energyapi.suffix")).append("/").append(tile.capacity).append(" ").append(trans.translateKey("energyapi.suffix"));
                this.drawTooltip(text.toString(),x,y,8,-8,true);
            }
        }
    }

    protected void drawGuiContainerForegroundLayer()
    {
        super.drawGuiContainerForegroundLayer();
        fontRenderer.drawString(name, 64, 6, 0xFF404040);
    }


    public void initGui()
    {
        super.initGui();
    }
}

package sunsetsatellite.energyapi;


import net.fabricmc.api.ModInitializer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.sound.block.BlockSounds;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.player.inventory.Container;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.server.entity.player.EntityPlayerMP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sunsetsatellite.energyapi.interfaces.mixins.IEntityPlayerMP;
import sunsetsatellite.energyapi.mixin.PacketAccessor;
import sunsetsatellite.energyapi.mp.packets.PacketUpdateEnergy;
import sunsetsatellite.energyapi.template.blocks.BlockBatteryBox;
import sunsetsatellite.energyapi.template.blocks.BlockGenerator;
import sunsetsatellite.energyapi.template.blocks.BlockMachine;
import sunsetsatellite.energyapi.template.blocks.BlockWire;
import sunsetsatellite.energyapi.template.gui.GuiBatteryBox;
import sunsetsatellite.energyapi.template.gui.GuiGenerator;
import sunsetsatellite.energyapi.template.gui.GuiMachine;
import sunsetsatellite.energyapi.template.items.ItemBattery;
import sunsetsatellite.energyapi.template.items.ItemBatteryUnlimited;
import sunsetsatellite.energyapi.template.items.ItemBatteryVoid;
import sunsetsatellite.energyapi.template.tiles.TileEntityBatteryBox;
import sunsetsatellite.energyapi.template.tiles.TileEntityGenerator;
import sunsetsatellite.energyapi.template.tiles.TileEntityMachine;
import sunsetsatellite.energyapi.template.tiles.TileEntityWire;
import sunsetsatellite.sunsetutils.util.Config;
import turniplabs.halplibe.HalpLibe;
import turniplabs.halplibe.helper.BlockHelper;
import turniplabs.halplibe.helper.EntityHelper;
import turniplabs.halplibe.helper.ItemHelper;
import turniplabs.halplibe.helper.TextureHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EnergyAPI implements ModInitializer {
    public static final String MOD_ID = "energyapi";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final Config config = new Config(MOD_ID,mapOf(new String[]{"enableTemplateGenerator","enableTemplateMachine","enableTemplateWire","enableTemplateBatteryBox","enableTemplateBattery","GuiID","PacketUpdateEnergyID","energyName","energySuffix","energyUsageTicks","energyUsageSeconds"},new String[]{"0","0","0","0","0","7","109","Energy","E","E/t","E/s"}),new Class[]{EnergyAPI.class});
    public static final String ENERGY_NAME = config.getFromConfig("energyName","Energy");
    public static final String ENERGY_SUFFIX = config.getFromConfig("energySuffix","E");

    public static HashMap<String, ArrayList<Class<?>>> nameToGuiMap = new HashMap<>();

    public static Block batteryBox;
    public static Block generator;
    public static Block machine;
    public static Block wire;
    public static Item battery;
    public static Item batteryUnlimited;
    public static Item batteryVoid;

    public static int[][] batteryTex = new int[7][];
    public static int[] wireTex = TextureHelper.registerBlockTexture(MOD_ID,"wireItem.png");

    @Override
    public void onInitialize() {
        LOGGER.info("EnergyAPI initialized.");
    }

    public EnergyAPI(){
        PacketAccessor.callAddIdClassMapping(EnergyAPI.config.getFromConfig("PacketUpdateEnergyID",109),true,false, PacketUpdateEnergy.class);
        if(EnergyAPI.config.getFromConfig("enableTemplateBatteryBox",1) == 1){
            batteryBox = BlockHelper.createBlock(MOD_ID,new BlockBatteryBox(HalpLibe.addModId(MOD_ID,"batteryBox"),1000, Material.metal),"machineside.png","batterybox.png", BlockSounds.METAL,1,1,0);
            EntityHelper.createTileEntity(TileEntityBatteryBox.class,"Battery Box");
            addToNameGuiMap("Battery Box", GuiBatteryBox.class,TileEntityBatteryBox.class);
        }
        if(EnergyAPI.config.getFromConfig("enableTemplateGenerator",1) == 1){
            generator = BlockHelper.createBlock(MOD_ID,new BlockGenerator(HalpLibe.addModId(MOD_ID,"generator"),1001, Material.metal),"machineside.png","generator.png",BlockSounds.METAL,1,1,0);
            EntityHelper.createTileEntity(TileEntityGenerator.class,"Generator");
            addToNameGuiMap("Generator", GuiGenerator.class, TileEntityGenerator.class);
        }
        if(EnergyAPI.config.getFromConfig("enableTemplateMachine",1) == 1){
            machine = BlockHelper.createBlock(MOD_ID,new BlockMachine(HalpLibe.addModId(MOD_ID,"machine"),1003, Material.metal),"machineside.png","machine.png",BlockSounds.METAL,1,1,0);
            EntityHelper.createTileEntity(TileEntityMachine.class,"Energy Machine");
            addToNameGuiMap("Energy Machine", GuiMachine.class,TileEntityMachine.class);
        }
        if(EnergyAPI.config.getFromConfig("enableTemplateBattery",1) == 1){
            int[] tex = TextureHelper.registerItemTexture(MOD_ID,"battery0.png");
            batteryTex[0] = tex;
            tex = TextureHelper.registerItemTexture(MOD_ID,"battery1.png");
            batteryTex[1] = tex;
            tex = TextureHelper.registerItemTexture(MOD_ID,"battery2.png");
            batteryTex[2] = tex;
            tex = TextureHelper.registerItemTexture(MOD_ID,"battery3.png");
            batteryTex[3] = tex;
            tex = TextureHelper.registerItemTexture(MOD_ID,"battery4.png");
            batteryTex[4] = tex;
            tex = TextureHelper.registerItemTexture(MOD_ID,"battery5.png");
            batteryTex[5] = tex;
            tex = TextureHelper.registerItemTexture(MOD_ID,"battery6.png");
            batteryTex[6] = tex;

            battery = ItemHelper.createItem(MOD_ID,new ItemBattery(17000),"battery","battery0.png").setMaxStackSize(1);
            batteryUnlimited = ItemHelper.createItem(MOD_ID,new ItemBatteryUnlimited(17001),"batteryUnlimited","batteryUnlimited.png").setMaxStackSize(1);
            batteryVoid = ItemHelper.createItem(MOD_ID,new ItemBatteryVoid(17002),"batteryVoid","batteryVoid.png").setMaxStackSize(1);
        }
        if(EnergyAPI.config.getFromConfig("enableTemplateWire",1) == 1){
            wire = BlockHelper.createBlock(MOD_ID,new BlockWire(HalpLibe.addModId(MOD_ID,"wire"),1002, Material.cloth),"wire.png", BlockSounds.METAL,1,1,0);
            Item.itemsList[wire.id].setIconCoord(wireTex[0],wireTex[1]);
            EntityHelper.createTileEntity(TileEntityWire.class,"Wire");
        }
    }

    public static double map(double valueCoord1,
                             double startCoord1, double endCoord1,
                             double startCoord2, double endCoord2) {

        final double EPSILON = 1e-12;
        if (Math.abs(endCoord1 - startCoord1) < EPSILON) {
            throw new ArithmeticException("Division by 0");
        }

        double ratio = (endCoord2 - startCoord2) / (endCoord1 - startCoord1);
        return ratio * (valueCoord1 - startCoord1) + startCoord2;
    }

    public static void displayGui(EntityPlayer entityplayer, GuiScreen guiScreen, Container container, IInventory tile) {
        if(entityplayer instanceof EntityPlayerMP) {
            ((IEntityPlayerMP)entityplayer).displayGuiScreen_energyapi(guiScreen,container,tile);
        } else {
            Minecraft.getMinecraft(Minecraft.class).displayGuiScreen(guiScreen);
        }
    }

    public static <K,V> Map<K,V> mapOf(K[] keys, V[] values){
        if(keys.length != values.length){
            throw new IllegalArgumentException("Arrays differ in size!");
        }
        HashMap<K,V> map = new HashMap<>();
        for (int i = 0; i < keys.length; i++) {
            map.put(keys[i],values[i]);
        }
        return map;
    }


    public static void addToNameGuiMap(String name, Class<? extends Gui> guiClass, Class<? extends TileEntity> tileEntityClass){
        ArrayList<Class<?>> list = new ArrayList<>();
        list.add(guiClass);
        list.add(tileEntityClass);
        nameToGuiMap.put(name,list);
    }

}

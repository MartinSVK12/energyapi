package sunsetsatellite.energyapi.api;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.LookupFuelFurnace;

import java.util.HashMap;
import java.util.Map;

public class LookupFuelEnergy {
    private static final LookupFuelEnergy fuelBase = new LookupFuelEnergy();
    protected Map<Integer, Integer> fuelList = new HashMap();

    public static LookupFuelEnergy fuelEnergy() {
        return fuelBase;
    }

    protected LookupFuelEnergy() {
        this.addFuelEntry(Block.planksOak.blockID, 3);
        this.addFuelEntry(Block.planksOakPainted.blockID, 3);
        this.addFuelEntry(Block.stairsPlanksOak.blockID, 3);
        this.addFuelEntry(Block.slabPlanksOak.blockID, 1);
        this.addFuelEntry(Block.slabPlanksOakPainted.blockID, 1);
        this.addFuelEntry(Block.fencePlanksOak.blockID, 3);
        this.addFuelEntry(Block.fencePlanksOakPainted.blockID, 3);
        this.addFuelEntry(Block.fencegatePlanksOak.blockID, 3);
        this.addFuelEntry(Block.fencegatePlanksOakPainted.blockID, 3);
        this.addFuelEntry(Block.logOak.blockID, 3);
        this.addFuelEntry(Block.logBirch.blockID, 3);
        this.addFuelEntry(Block.logPine.blockID, 3);
        this.addFuelEntry(Block.logCherry.blockID, 3);
        this.addFuelEntry(Block.logOakMossy.blockID, 3);
        this.addFuelEntry(Block.logEucalyptus.blockID, 3);
        this.addFuelEntry(Block.saplingOak.blockID, 1);
        this.addFuelEntry(Block.saplingPine.blockID, 1);
        this.addFuelEntry(Block.saplingBirch.blockID, 1);
        this.addFuelEntry(Block.saplingCherry.blockID, 1);
        this.addFuelEntry(Block.saplingEucalyptus.blockID, 1);
        this.addFuelEntry(Block.saplingShrub.blockID, 1);
        this.addFuelEntry(Block.saplingOakRetro.blockID, 1);
        this.addFuelEntry(Block.deadbush.blockID, 1);
        this.addFuelEntry(Block.spinifex.blockID, 1);
        this.addFuelEntry(Block.blockCoal.blockID, 64);
        this.addFuelEntry(Block.blockCharcoal.blockID, 64);
        this.addFuelEntry(Block.blockNetherCoal.blockID, 128);
        this.addFuelEntry(Item.stick.itemID, 1);
        this.addFuelEntry(Item.toolPickaxeWood.itemID, 5);
        this.addFuelEntry(Item.toolSwordWood.itemID, 5);
        this.addFuelEntry(Item.toolAxeWood.itemID, 5);
        this.addFuelEntry(Item.toolShovelWood.itemID, 5);
        this.addFuelEntry(Item.toolHoeWood.itemID, 5);
        this.addFuelEntry(Item.toolBow.itemID, 3);
        this.addFuelEntry(Item.toolFishingrod.itemID, 3);
        this.addFuelEntry(Item.boat.itemID, 3);
        this.addFuelEntry(Item.doorOak.itemID, 3);
        this.addFuelEntry(Item.sign.itemID, 3);
        this.addFuelEntry(Item.bowl.itemID, 3);
        this.addFuelEntry(Item.coal.itemID, 8);
        this.addFuelEntry(Item.nethercoal.itemID, 16);
    }

    public void addFuelEntry(int id, int energyYield) {
        this.fuelList.put(id, energyYield);
    }

    public int getEnergyYield(int id) {
        return this.fuelList.get(id) == null ? 0 : (Integer)this.fuelList.get(id);
    }

    public Map<Integer, Integer> getFuelList() {
        return this.fuelList;
    }

}

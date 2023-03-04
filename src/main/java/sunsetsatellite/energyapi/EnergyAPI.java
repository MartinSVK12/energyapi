package sunsetsatellite.energyapi;

import net.fabricmc.api.ModInitializer;
import net.minecraft.src.Vec3D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;


public class EnergyAPI implements ModInitializer {
    public static final String MOD_ID = "energyapi";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("EnergyAPI initialized.");
    }

    public EnergyAPI(){
        Config.init();
    }


}

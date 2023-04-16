package Tavi007.Materia.init;

import Tavi007.Materia.Materia;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class StartupCommon {

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        Materia.LOGGER.info("setup method registered.");
    }

}

package Tavi007.Materia.init;

import Tavi007.Materia.Materia;
import Tavi007.Materia.capabilities.level.LevelDataCapability;
import Tavi007.Materia.capabilities.materia.collection.handler.MateriaCollectionHandlerCapability;
import Tavi007.Materia.network.PacketManager;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class StartupCommon {

    @SubscribeEvent
    public static void onRegisterCapabilitiesEvent(RegisterCapabilitiesEvent event) {
        LevelDataCapability.register(event);
        MateriaCollectionHandlerCapability.register(event);
        Materia.LOGGER.info("capabilities registered.");
    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        registerNetworking();
        Materia.LOGGER.info("setup method registered.");
    }

    private static void registerNetworking() {
        PacketManager.init();
    }
}

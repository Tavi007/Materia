package Tavi007.Materia.registry;

import Tavi007.Materia.capabilities.ForgeCapabilityAccessor;
import Tavi007.Materia.capabilities.MateriaLevelDataCapability;
import Tavi007.Materia.capabilities.materia.collection.handler.MateriaCollectionHandlerCapability;
import Tavi007.Materia.common.Constants;
import Tavi007.Materia.common.capabilities.CapabilitiesAccessors;
import Tavi007.Materia.common.util.ResourceLocationHelper;
import Tavi007.Materia.network.ForgePacketManager;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class StartupCommon {

    @SubscribeEvent
    public static void onRegisterCapabilitiesEvent(RegisterCapabilitiesEvent event) {
        MateriaLevelDataCapability.register(event);

        CapabilitiesAccessors.init(
            ForgeCapabilityAccessor::getMateriaLevelData,
            ForgeCapabilityAccessor::getMateriaToolContainer
        );
        MateriaCollectionHandlerCapability.register(event);
        Constants.LOGGER.info("capabilities registered.");
    }



    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        ForgePacketManager.init();

        ResourceLocationHelper.init(
            ForgeRegistries.ITEMS::getKey,
            ForgeRegistries.ENTITY_TYPES::getKey);
        Constants.LOGGER.info("setup method registered.");
    }
}

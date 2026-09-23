package Tavi007.Materia.init;

import Tavi007.Materia.capabilities.MateriaLevelDataCapability;
import Tavi007.Materia.capabilities.MateriaLevelDataSerializer;
import Tavi007.Materia.capabilities.materia.collection.handler.MateriaCollectionHandlerCapability;
import Tavi007.Materia.common.Constants;
import Tavi007.Materia.common.capabilities.CapabilitiesAccessors;
import Tavi007.Materia.common.data.capabilities.MateriaLevelData;
import Tavi007.Materia.network.PacketManager;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class StartupCommon {

    @SubscribeEvent
    public static void onRegisterCapabilitiesEvent(RegisterCapabilitiesEvent event) {
        MateriaLevelDataCapability.register(event);
        CapabilitiesAccessors.setMateriaLevelDataAccessor((stack) -> {
            if (stack == null) {
                return new MateriaLevelData();
            }
            return stack.getCapability(MateriaLevelDataCapability.CAPABILITY, null).orElse(new MateriaLevelDataSerializer()).getData();
        });



        MateriaCollectionHandlerCapability.register(event);
        Constants.LOGGER.info("capabilities registered.");
    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        registerNetworking();
        Constants.LOGGER.info("setup method registered.");
    }

    private static void registerNetworking() {
        PacketManager.init();
    }
}

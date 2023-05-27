package Tavi007.Materia.init;

import Tavi007.Materia.capabilities.level.LevelDataCapability;
import Tavi007.Materia.capabilities.magic.MagicDataCapability;
import Tavi007.Materia.capabilities.materia.collection.handler.MateriaCollectionHandlerCapability;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class StartupCommon {

    @SubscribeEvent
    public static void onCommonSetup(RegisterCapabilitiesEvent event) {
        // capabilities
        LevelDataCapability.register(event);
        MagicDataCapability.register(event);
        MateriaCollectionHandlerCapability.register(event);
    }
}

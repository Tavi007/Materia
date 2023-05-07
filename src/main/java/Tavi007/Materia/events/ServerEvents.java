package Tavi007.Materia.events;

import Tavi007.Materia.Materia;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = Materia.MOD_ID, bus = Bus.FORGE)
public class ServerEvents {

    @SubscribeEvent
    public static void addReloadListenerEvent(AddReloadListenerEvent event) {
        event.addListener(Materia.MATERIA_EFFECT_CONFIGURATION_MANGER);
        Materia.LOGGER.info("ReloadListener for effect configuration registered.");
    }
}

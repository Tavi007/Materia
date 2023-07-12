package Tavi007.Materia.init;

import java.util.ArrayList;
import java.util.List;

import Tavi007.Materia.Materia;
import Tavi007.Materia.network.Packet;
import Tavi007.Materia.recipes.effects.MateriaEffectConfigurationManager;
import Tavi007.Materia.recipes.effects.MateriaEffectRecipeManager;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = Materia.MOD_ID, bus = Bus.FORGE)
public class ReloadListenerList {

    public static MateriaEffectConfigurationManager MATERIA_EFFECT_CONFIGURATION_MANGER = new MateriaEffectConfigurationManager();
    public static MateriaEffectRecipeManager MATERIA_EFFECT_RECIPE_MANGER = new MateriaEffectRecipeManager();

    @SubscribeEvent
    public static void addReloadListenerEvent(AddReloadListenerEvent event) {
        event.addListener(ReloadListenerList.MATERIA_EFFECT_CONFIGURATION_MANGER);
        event.addListener(ReloadListenerList.MATERIA_EFFECT_RECIPE_MANGER);
        Materia.LOGGER.info("ReloadListener registered.");
    }

    public static List<Packet> getSyncPackets() {
        List<Packet> packets = new ArrayList<>();
        packets.add(MATERIA_EFFECT_CONFIGURATION_MANGER.getSyncPacket());
        packets.add(MATERIA_EFFECT_RECIPE_MANGER.getSyncPacket());
        return packets;
    }
}

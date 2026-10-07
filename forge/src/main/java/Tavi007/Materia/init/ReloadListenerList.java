package Tavi007.Materia.init;

import Tavi007.Materia.common.Constants;
import Tavi007.Materia.common.data.datapack.LevelUpDataReloadListener;
import Tavi007.Materia.common.data.datapack.MobDataReloadListener;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Bus.FORGE)
public class ReloadListenerList {

    private static final LevelUpDataReloadListener LEVEL_UP_DATA_LISTENER = new LevelUpDataReloadListener();
    private static final MobDataReloadListener MOB_DATA_LISTENER = new MobDataReloadListener();

    @SubscribeEvent
    public static void addReloadListenerEvent(AddReloadListenerEvent event) {
        event.addListener(LEVEL_UP_DATA_LISTENER);
        event.addListener(MOB_DATA_LISTENER);
        Constants.LOGGER.info("ReloadListener registered.");
    }

//    public static List<Packet> getSyncPackets() {
//        List<Packet> packets = new ArrayList<>();
//        packets.add(MATERIA_EFFECT_CONFIGURATION_MANGER.getSyncPacket());
//        packets.add(MATERIA_EFFECT_RECIPE_MANGER.getSyncPacket());
//        return packets;
//    }
}

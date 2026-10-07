package Tavi007.Materia.events;


import Tavi007.Materia.common.Constants;
import Tavi007.Materia.common.items.IMateriaTool;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Bus.FORGE)
public class PlayerEvents {

    @SubscribeEvent
    public static void onAnvilChange(AnvilUpdateEvent event) {
        if (event.getLeft() != null && event.getLeft().getItem() instanceof IMateriaTool
            && event.getRight() != null && !event.getRight().isEnchanted()) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void adjustBreakSpeed(PlayerEvent.BreakSpeed event) {
        ItemStack stack = event.getEntity().getMainHandItem();
        if (stack.getItem() instanceof IMateriaTool) {
            float factor = 1.0f; // TODO: get factor from a HelperMethod. It will calculate one depending on Efficiency UP materia
            event.setNewSpeed(event.getOriginalSpeed() * factor);
        }
    }

    @SubscribeEvent
    public static void playerLoggedIn(PlayerLoggedInEvent event) {
//        Player player = event.getEntity();
//        if (!player.level().isClientSide && player instanceof ServerPlayer) {
//            List<AbstractPacket> packets = ReloadListenerList.getSyncPackets();
//            packets.forEach(packet -> ModNetwork.sendToClient(packet, player));
//        }
    }
}

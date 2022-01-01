package Tavi007.Materia.events;

import Tavi007.Materia.Materia;
import Tavi007.Materia.items.IMateriaTool;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = Materia.MOD_ID, bus = Bus.FORGE)
public class PlayerEvents {

	@SubscribeEvent
	public static void onAnvilChange(AnvilUpdateEvent event) {
		if(event.getLeft() != null && event.getLeft().getItem() instanceof IMateriaTool
		&& event.getRight() != null && !event.getRight().isEnchanted()) {
			event.setCanceled(true);
		}
	}
}

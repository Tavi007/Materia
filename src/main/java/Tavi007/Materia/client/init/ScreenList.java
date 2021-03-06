package Tavi007.Materia.client.init;

import Tavi007.Materia.client.gui.EquippingStationScreen;
import Tavi007.Materia.init.ContainerTypeList;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ScreenList {
	@SubscribeEvent
	public static void onClientSetupEvent(FMLClientSetupEvent event) {
		ScreenManager.registerFactory(ContainerTypeList.EQUIPPING_STATION.get(), EquippingStationScreen::new);
	}
}

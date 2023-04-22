package Tavi007.Materia.client.init;

import Tavi007.Materia.client.gui.EquippingStationScreen;
import Tavi007.Materia.client.gui.MateriaIncubatorScreen;
import Tavi007.Materia.init.ContainerTypeList;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ScreenList {

    @SubscribeEvent
    public static void onClientSetupEvent(FMLClientSetupEvent event) {
        MenuScreens.register(ContainerTypeList.EQUIPPING_STATION.get(), EquippingStationScreen::new);
        MenuScreens.register(ContainerTypeList.MATERIA_INCUBATOR.get(), MateriaIncubatorScreen::new);
    }
}

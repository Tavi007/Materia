package Tavi007.Materia.client.init;

import Tavi007.Materia.Materia;
import Tavi007.Materia.client.gui.EquippingStationScreen;
import Tavi007.Materia.client.gui.MateriaIncubatorScreen;
import Tavi007.Materia.init.MenuList;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Materia.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ScreenList {

    @SubscribeEvent
    public static void onClientSetupEvent(FMLClientSetupEvent event) {
        MenuScreens.register(MenuList.EQUIPPING_STATION.get(), EquippingStationScreen::new);
        MenuScreens.register(MenuList.MATERIA_INCUBATOR.get(), MateriaIncubatorScreen::new);
    }

}

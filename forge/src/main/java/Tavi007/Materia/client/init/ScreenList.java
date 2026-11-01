package Tavi007.Materia.client.init;

import Tavi007.Materia.client.gui.screens.MateriaIncubatorScreen;
import Tavi007.Materia.client.gui.screens.ParaMagicExtractorScreen;
import Tavi007.Materia.common.Constants;
import Tavi007.Materia.common.init.ModMenus;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ScreenList {

    @SubscribeEvent
    public static void onClientSetupEvent(FMLClientSetupEvent event) {
//        MenuScreens.register(MenuList.EQUIPPING_STATION.get(), EquippingStationScreen::new);
        MenuScreens.register(ModMenus.MATERIA_INCUBATOR.get(), MateriaIncubatorScreen::new);
        MenuScreens.register(ModMenus.PARA_MAGIC_EXTRACTOR.get(), ParaMagicExtractorScreen::new);
    }

}

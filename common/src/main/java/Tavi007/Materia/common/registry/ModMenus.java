package Tavi007.Materia.common.registry;

import Tavi007.Materia.common.Constants;
import Tavi007.Materia.common.registry.registrar.MenuRegistrar;
import Tavi007.Materia.common.inventory.menus.EquippingStationMenu;
import Tavi007.Materia.common.inventory.menus.MateriaIncubatorMenu;
import Tavi007.Materia.common.inventory.menus.ParaMagicExtractorMenu;
import com.google.common.base.Supplier;
import net.minecraft.world.inventory.MenuType;


public class ModMenus {
    public static Supplier<MenuType<EquippingStationMenu>> EQUIPPING_STATION;
    public static Supplier<MenuType<MateriaIncubatorMenu>> MATERIA_INCUBATOR;
    public static Supplier<MenuType<ParaMagicExtractorMenu>> PARA_MAGIC_EXTRACTOR;

    public static void register(MenuRegistrar registrar) {
        EQUIPPING_STATION = registrar.register(
                Constants.EQUIPPING_STATION,
                EquippingStationMenu::new
        );

        MATERIA_INCUBATOR = registrar.register(
                Constants.MATERIA_INCUBATOR,
                MateriaIncubatorMenu::new
        );

        PARA_MAGIC_EXTRACTOR = registrar.register(
                Constants.PARA_MAGIC_EXTRACTOR,
                ParaMagicExtractorMenu::new
        );
    }
}

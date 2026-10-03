package Tavi007.Materia.common.init;

import Tavi007.Materia.common.Constants;
import Tavi007.Materia.common.init.registrar.MenuRegistrar;
import Tavi007.Materia.common.inventory.menus.EquippingStationMenu;
import Tavi007.Materia.common.inventory.menus.MateriaIncubatorMenu;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;

import java.awt.*;

public class ModMenus {
    public static MenuType<EquippingStationMenu> EQUIPPING_STATION;
    public static MenuType<MateriaIncubatorMenu> MATERIA_INCUBATOR;

    public static void register(MenuRegistrar registrar) {
        EQUIPPING_STATION = registrar.register(
                Constants.EQUIPPING_STATION,
                EquippingStationMenu::new
        );

        MATERIA_INCUBATOR = registrar.register(
                Constants.MATERIA_INCUBATOR,
                MateriaIncubatorMenu::new
        );
    }
}

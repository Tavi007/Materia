package Tavi007.Materia.common.init;

import Tavi007.Materia.common.Constants;
import Tavi007.Materia.common.init.registrar.MenuRegistrar;
import Tavi007.Materia.common.inventory.menus.EquippingStationMenu;
import Tavi007.Materia.common.inventory.menus.MateriaIncubatorMenu;
import com.google.common.base.Supplier;
import net.minecraft.world.inventory.MenuType;


public class ModMenus {
    public static Supplier<MenuType<EquippingStationMenu>> EQUIPPING_STATION;
    public static Supplier<MenuType<MateriaIncubatorMenu>> MATERIA_INCUBATOR;
}

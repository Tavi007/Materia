package Tavi007.Materia.init;

import Tavi007.Materia.Materia;
import Tavi007.Materia.inventory.menu.EquippingStationMenu;
import Tavi007.Materia.inventory.menu.MateriaIncubatorMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MenuList {

    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Materia.MOD_ID);

    public static final RegistryObject<MenuType<EquippingStationMenu>> EQUIPPING_STATION = MENU_TYPES
        .register("equipping_station_menu", () -> IForgeMenuType.create(EquippingStationMenu::new));

    public static final RegistryObject<MenuType<MateriaIncubatorMenu>> MATERIA_INCUBATOR = MENU_TYPES
        .register("materia_incubator_menu", () -> IForgeMenuType.create(MateriaIncubatorMenu::new));
}

package Tavi007.Materia.registry;

import Tavi007.Materia.common.Constants;
import Tavi007.Materia.common.registry.ModMenus;
import Tavi007.Materia.registry.registrar.ForgeMenuRegistrar;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class MenuList {

    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Constants.MOD_ID);

    static {
        ForgeMenuRegistrar registrar = new ForgeMenuRegistrar();
        ModMenus.register(registrar);
    }
//    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Constants.MOD_ID);
//
////    public static final RegistryObject<MenuType<EquippingStationMenu>> EQUIPPING_STATION = MENU_TYPES
////        .register("equipping_station_menu", () -> IForgeMenuType.create(EquippingStationMenu::new));
////
//
//    public static final RegistryObject<MenuType<MateriaIncubatorMenu>> MATERIA_INCUBATOR = MENU_TYPES
//        .register("materia_incubator_menu", () -> IForgeMenuType.create(MateriaIncubatorMenu::new));
//

}

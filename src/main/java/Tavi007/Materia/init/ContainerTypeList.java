package Tavi007.Materia.init;

import Tavi007.Materia.Materia;
import Tavi007.Materia.inventory.container.EquippingStationContainer;
import Tavi007.Materia.inventory.container.MateriaIncubatorContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ContainerTypeList {

    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Materia.MOD_ID);

    public static final RegistryObject<MenuType<EquippingStationContainer>> EQUIPPING_STATION = MENU_TYPES
        .register("equipping_station_container", () -> IForgeMenuType.create(EquippingStationContainer::new));

    public static final RegistryObject<MenuType<MateriaIncubatorContainer>> MATERIA_INCUBATOR = MENU_TYPES
        .register("materia_incubator_container", () -> IForgeMenuType.create(MateriaIncubatorContainer::new));
}

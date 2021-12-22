package Tavi007.Materia.init;

import Tavi007.Materia.Materia;
import Tavi007.Materia.inventory.container.EquippingStationContainer;
import Tavi007.Materia.inventory.container.MateriaHibernatorContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ContainerTypeList {
	public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, Materia.MOD_ID);

	public static final RegistryObject<ContainerType<EquippingStationContainer>> EQUIPPING_STATION = CONTAINER_TYPES
			.register("equipping_station_container", () -> IForgeContainerType.create(EquippingStationContainer::new));
	
	public static final RegistryObject<ContainerType<MateriaHibernatorContainer>> MATERIA_HIBERNATOR = CONTAINER_TYPES
			.register("materia_hibernator_container", () -> IForgeContainerType.create(MateriaHibernatorContainer::new));
}

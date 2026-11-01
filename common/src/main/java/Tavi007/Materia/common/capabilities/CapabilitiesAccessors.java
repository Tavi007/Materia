package Tavi007.Materia.common.capabilities;

import Tavi007.Materia.common.data.capability.MateriaLevelData;
import Tavi007.Materia.common.inventory.containers.MateriaToolContainer;
import net.minecraft.world.item.ItemStack;

import java.util.function.Function;

public class CapabilitiesAccessors {

    private static Function<ItemStack, MateriaLevelData> materiaLevelDataAccessor;
    private static Function<ItemStack, MateriaToolContainer> materiaToolContainerAccessor;

    public static void init(
            Function<ItemStack, MateriaLevelData> materiaLevelDataAccessor,
            Function<ItemStack, MateriaToolContainer> materiaToolContainerAccessor
    ) {
        CapabilitiesAccessors.materiaLevelDataAccessor = materiaLevelDataAccessor;
        CapabilitiesAccessors.materiaToolContainerAccessor = materiaToolContainerAccessor;
    }

    public static MateriaLevelData getMateriaLevelData(ItemStack stack) {
        return materiaLevelDataAccessor.apply(stack);
    }

    public static MateriaToolContainer getMateriaToolContainerAccessor(ItemStack stack) {
        return materiaToolContainerAccessor.apply(stack);
    }

}

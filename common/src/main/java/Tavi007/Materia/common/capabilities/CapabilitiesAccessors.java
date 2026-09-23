package Tavi007.Materia.common.capabilities;

import Tavi007.Materia.common.data.capabilities.MateriaLevelData;
import net.minecraft.world.item.ItemStack;

import java.util.function.Function;

public class CapabilitiesAccessors {

    private static Function<ItemStack, MateriaLevelData> materiaLevelDataAccessor;


    public static void setMateriaLevelDataAccessor(Function<ItemStack, MateriaLevelData> accessor) {
        materiaLevelDataAccessor = accessor;
    }

    public static MateriaLevelData getMateriaLevelData(ItemStack stack) {
        return materiaLevelDataAccessor.apply(stack);
    }

}

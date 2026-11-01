package Tavi007.Materia.capabilities;

import Tavi007.Materia.common.data.capability.MateriaLevelData;
import Tavi007.Materia.common.inventory.containers.MateriaToolContainer;
import net.minecraft.world.item.ItemStack;

public class ForgeCapabilityAccessor {
    private ForgeCapabilityAccessor(){}

    public static MateriaLevelData getMateriaLevelData(ItemStack stack) {
        if (stack == null) {
            return new MateriaLevelData();
        }
        return stack.getCapability(MateriaLevelDataCapability.CAPABILITY, null).orElse(new MateriaLevelDataSerializer()).getData();
    }

    public static MateriaToolContainer getMateriaToolContainer(ItemStack stack) {
        if (stack == null) {
            return null;
        }
        return null;
    }

}


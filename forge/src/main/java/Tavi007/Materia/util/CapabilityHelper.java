package Tavi007.Materia.util;

import java.util.Collections;
import java.util.List;

import Tavi007.Materia.capabilities.MateriaLevelDataCapability;
import Tavi007.Materia.capabilities.MateriaLevelDataSerializer;
import Tavi007.Materia.capabilities.materia.collection.handler.MateriaCollectionHandler;
import Tavi007.Materia.capabilities.materia.collection.handler.MateriaCollectionHandlerCapability;
import Tavi007.Materia.common.data.capabilities.MateriaLevelData;
import Tavi007.Materia.data.pojo.effects.AbstractMateriaEffect;
import net.minecraft.world.item.ItemStack;

public class CapabilityHelper {

    /**
     * Returns the level data {@link MateriaLevelData} of the {@link ItemStack}.
     *
     * @param stack
     *            An ItemStack (A MateriaItem).
     * @return the LevelData, containing the current level, current ap and the array for leveling up.
     */
    public static MateriaLevelData getLevelData(ItemStack stack) {
        return stack.getCapability(MateriaLevelDataCapability.CAPABILITY, null).orElse(new MateriaLevelDataSerializer()).getData();
    }

    /**
     * Returns the materia collection {@link MateriaCollection} of the {@link ItemStack}.
     *
     * @param stack
     *            An ItemStack.
     * @return the MateriaCollection, containing the materiaStacks, current ap and the array for leveling up.
     */
    public static MateriaCollectionHandler getMateriaCollectionHandler(ItemStack stack) {
        return stack.getCapability(MateriaCollectionHandlerCapability.CAPABILITY, null).orElse(new MateriaCollectionHandler());
    }

    public static List<AbstractMateriaEffect> getCurrentlySelectedEffect(ItemStack stack) {
        MateriaCollectionHandler collectionHandler = getMateriaCollectionHandler(stack);
        return collectionHandler.getSelectedEffects();
    }

    public static List<ItemStack> getCurrentlySelectedMateriaStacks(ItemStack stack) {
        MateriaCollectionHandler collectionHandler = getMateriaCollectionHandler(stack);
        return collectionHandler.getSelectedMateriaStacks();
    }
}

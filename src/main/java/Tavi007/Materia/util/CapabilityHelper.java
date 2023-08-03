package Tavi007.Materia.util;

import java.util.Collections;
import java.util.List;

import Tavi007.Materia.capabilities.level.LevelData;
import Tavi007.Materia.capabilities.level.LevelDataCapability;
import Tavi007.Materia.capabilities.magic.MagicData;
import Tavi007.Materia.capabilities.magic.MagicDataCapability;
import Tavi007.Materia.capabilities.materia.collection.handler.MateriaCollectionHandler;
import Tavi007.Materia.capabilities.materia.collection.handler.MateriaCollectionHandlerCapability;
import Tavi007.Materia.effect.configurations.AbstractMateriaEffectConfiguration;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class CapabilityHelper {

    /**
     * Returns the level data {@link LevelData} of the {@link ItemStack}.
     * 
     * @param stack
     *            An ItemStack (A MateriaItem).
     * @return the LevelData, containing the current level, current ap and the array for leveling up.
     */
    public static LevelData getLevelData(ItemStack stack) {
        return stack.getCapability(LevelDataCapability.CAPABILITY, null).orElse(new LevelData(Collections.emptyList()));
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

    public static List<AbstractMateriaEffectConfiguration> getCurrentlySelectedEffect(ItemStack stack) {
        MateriaCollectionHandler collectionHandler = getMateriaCollectionHandler(stack);
        return collectionHandler.getSelectedEffectConfigurations();
    }

    public static List<ItemStack> getCurrentlySelectedMateriaStacks(ItemStack stack) {
        MateriaCollectionHandler collectionHandler = getMateriaCollectionHandler(stack);
        return collectionHandler.getSelectedMateriaStacks();
    }

    /**
     * Returns the magic data {@link MagicData} of the {@link PlayerEntity}.
     * 
     * @param entity
     *            An PlayerEntity.
     * @return the MagicData, containing the mana and spell index.
     */
    public static MagicData getManaData(Player entity) {
        return entity.getCapability(MagicDataCapability.CAPABILITY, null).orElse(new MagicData());
    }
}

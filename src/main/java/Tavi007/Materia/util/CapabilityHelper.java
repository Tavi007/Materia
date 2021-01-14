package Tavi007.Materia.util;

import java.util.ArrayList;

import Tavi007.Materia.capabilities.level.LevelData;
import Tavi007.Materia.capabilities.level.LevelDataCapability;
import Tavi007.Materia.capabilities.toolslots.MateriaCollection;
import Tavi007.Materia.capabilities.toolslots.MateriaCollectionCapability;
import Tavi007.Materia.effects.MateriaEffect;
import net.minecraft.item.ItemStack;

public class CapabilityHelper {
	
	/**
	 * Returns the level data {@link LevelData} of the {@link ItemStack}.
	 * @param stack An ItemStack.
	 * @return the LevelData, containing the current level, current ap and the array for leveling up.
	 */
	public static LevelData getLevelData(ItemStack stack) {
		LevelData levelData = (LevelData) stack.getCapability(LevelDataCapability.LevelData_Capability, null).orElse(new LevelData());
		return levelData;
	}

	/**
	 * Returns the materia collection {@link MateriaCollection} of the {@link ItemStack}.
	 * @param stack An ItemStack.
	 * @return the MateriaCollection, containing the materiaStacks, current ap and the array for leveling up.
	 */
	public static MateriaCollection getMateriaCollection(ItemStack stack) {
		MateriaCollection collection = (MateriaCollection) stack.getCapability(MateriaCollectionCapability.MateriaCollection_Capability, null).orElse(new MateriaCollection());
		return collection;
	}

	/**
	 * Returns the materia collection {@link MateriaCollection} of the {@link ItemStack}.
	 * @param stack An ItemStack.
	 * @return the MateriaCollection, containing the materiaStacks, current ap and the array for leveling up.
	 */
	public static ArrayList<MateriaEffect> getEffects(ItemStack stack) {
		MateriaCollection collection = getMateriaCollection(stack);
		return collection.getEffects();
	}
}

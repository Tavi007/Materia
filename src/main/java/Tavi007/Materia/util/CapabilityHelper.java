package Tavi007.Materia.util;

import Tavi007.Materia.capabilities.level.LevelData;
import Tavi007.Materia.capabilities.level.LevelDataCapability;
import Tavi007.Materia.capabilities.magic.MagicData;
import Tavi007.Materia.capabilities.magic.MagicDataCapability;
import Tavi007.Materia.capabilities.toolslots.MateriaCollection;
import Tavi007.Materia.capabilities.toolslots.MateriaCollectionCapability;
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
        return stack.getCapability(LevelDataCapability.CAPABILITY, null).orElse(new LevelData());
    }

    /**
     * Returns the materia collection {@link MateriaCollection} of the {@link ItemStack}.
     * 
     * @param stack
     *            An ItemStack.
     * @return the MateriaCollection, containing the materiaStacks, current ap and the array for leveling up.
     */
    public static MateriaCollection getMateriaCollection(ItemStack stack) {
        return stack.getCapability(MateriaCollectionCapability.CAPABILITY, null).orElse(new MateriaCollection());
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

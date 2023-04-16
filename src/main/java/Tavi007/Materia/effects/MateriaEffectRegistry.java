package Tavi007.Materia.effects;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import net.minecraft.world.item.ItemStack;

public class MateriaEffectRegistry {

    private static HashSet<MateriaEffect> effectRegistry = new HashSet<MateriaEffect>();

    public static MateriaEffect register(MateriaEffect effect) {
        effectRegistry.add(effect);
        return effect;
    }

    public static List<MateriaEffect> computeEffects(List<ItemStack> itemStacks) {
        List<ItemStack> stacksWithouEmpty = new ArrayList<ItemStack>();
        for (ItemStack stack : itemStacks) {
            if (!stack.isEmpty()) {
                stacksWithouEmpty.add(stack);
            }
        }

        List<MateriaEffect> ret = new ArrayList<MateriaEffect>();
        if (!stacksWithouEmpty.isEmpty()) {
            boolean foundEffect = false;
            for (MateriaEffect effect : effectRegistry) {
                MateriaEffect copy = effect.initializeCopy(stacksWithouEmpty);
                if (copy != null) {
                    ret.add(copy);
                    foundEffect = true;
                    break;
                }
            }

            if (!foundEffect) {
                for (int i = 0; i < stacksWithouEmpty.size(); i++) {
                    ret.addAll(computeEffects(stacksWithouEmpty.subList(i, i + 1)));
                }
            }
        }
        return ret;
    }

}

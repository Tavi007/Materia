package Tavi007.Materia.effects;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import net.minecraft.item.ItemStack;

public class MateriaEffectRegistry {

    private static HashSet<MateriaEffect> effectRegistry = new HashSet<MateriaEffect>();

    public static MateriaEffect register(MateriaEffect effect) {
        effectRegistry.add(effect);
        return effect;
    }

    public static List<MateriaEffect> getEffect(List<ItemStack> itemStacks) {
        List<ItemStack> stackWithouEmpty = new ArrayList<ItemStack>();
        for (ItemStack stack : itemStacks) {
            if (!stack.isEmpty()) {
                stackWithouEmpty.add(stack);
            }
        }

        List<MateriaEffect> ret = new ArrayList<MateriaEffect>();
        if (!stackWithouEmpty.isEmpty()) {
            boolean foundEffect = false;
            for (MateriaEffect effect : effectRegistry) {
                MateriaEffect copy = effect.initializeCopy(stackWithouEmpty);
                if (copy != null) {
                    ret.add(copy);
                    foundEffect = true;
                    break;
                }
            }

            if (!foundEffect) {
                for (int i = 0; i < stackWithouEmpty.size(); i++) {
                    ret.addAll(getEffect(stackWithouEmpty.subList(i, i + 1)));
                }
            }
        }
        return ret;
    }

}

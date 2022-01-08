package Tavi007.Materia.util;

import java.util.ArrayList;

import Tavi007.Materia.capabilities.toolslots.MateriaCollection;
import Tavi007.Materia.effects.MateriaEffect;
import Tavi007.Materia.effects.MateriaEffectRegistry;
import Tavi007.Materia.items.IMateriaTool;
import net.minecraft.item.ItemStack;

public class MateriaEffectHelper {

    public static void computeEffectList(ItemStack stack) {
        MateriaCollection collection = CapabilityHelper.getMateriaCollection(stack);

        // check if stacks are empty
        boolean isEmpty = true;
        for (int i = 0; i < collection.getSlots(); i++) {
            if (!collection.getStackInSlot(i).isEmpty()) {
                isEmpty = false;
            }
        }
        if (isEmpty)
            return;

        // compute effects
        ArrayList<MateriaEffect> effectList = new ArrayList<MateriaEffect>();
        IMateriaTool tool = (IMateriaTool) stack.getItem();
        effectList.addAll(computeEffects(0, collection, tool.getTopCollectionSizes()));
        effectList.addAll(computeEffects(4, collection, tool.getBotCollectionSizes()));
        collection.setEffects(effectList);
        collection.markCleaned();
    }

    private static ArrayList<MateriaEffect> computeEffects(int startStackCounter, MateriaCollection collection, int[] collectionSize) {
        ArrayList<MateriaEffect> effectList = new ArrayList<MateriaEffect>();

        for (int i = 0; i < collectionSize.length; i++) {
            ArrayList<ItemStack> stackList = new ArrayList<ItemStack>();
            boolean hasMateria = false;
            for (int j = 0; j < collectionSize[i]; j++) {
                ItemStack stack = collection.getStackInSlot(startStackCounter + j);
                stackList.add(stack);
                if (!stack.isEmpty()) {
                    hasMateria = true;
                }
            }
            if (hasMateria) {
                effectList.addAll(MateriaEffectRegistry.computeEffects(stackList));
            }
            startStackCounter += collectionSize[i];
        }
        return effectList;
    }
}

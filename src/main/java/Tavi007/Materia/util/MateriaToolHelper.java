package Tavi007.Materia.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import javax.annotation.Nullable;

import Tavi007.Materia.capabilities.materia.collection.handler.MateriaCollectionHandler;
import Tavi007.Materia.items.IMateriaTool;
import Tavi007.Materia.recipes.effects.configuration.AbstractMateriaEffectConfiguration;
import Tavi007.Materia.recipes.effects.configuration.MiningConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class MateriaToolHelper {

    public static List<List<Integer>> fromCollectionSizesToIdMappings(List<Integer> sizes, int counterStart) {
        List<List<Integer>> idMappings = new ArrayList<>();
        int counter = counterStart;
        for (Integer size : sizes) {
            idMappings.add(IntStream.range(counter, counter + size).boxed().toList());
            counter++;
        }
        return idMappings;
    }

    public static List<Integer> fromIdMappingToCollectionSizes(List<List<Integer>> idMappings) {
        List<Integer> sizes = new ArrayList<>();
        for (List<Integer> idMapping : idMappings) {
            sizes.add(idMapping.size());
        }
        return sizes;
    }

    public static int getNoCollectionSlot(List<Integer> slotList) {
        int counter = 0;
        for (Integer i : slotList) {
            counter += i;
        }
        return counter;
    }

    public static boolean isCollectionSizesValid(List<Integer> slotList) {
        int counter = 0;
        for (Integer i : slotList) {
            if (i < 0) {
                return false;
            }
            counter += i;
        }
        if (counter > 4) {
            return false;
        }
        return true;
    }

    public static CompoundTag getShareTag(ItemStack stack) {
        CompoundTag nbt = stack.getTag();
        MateriaCollectionHandler collectionHandler = CapabilityHelper.getMateriaCollectionHandler(stack);
        nbt.put("materia_collection_handler", collectionHandler.serializeNBT());
        return nbt;
    }

    public static void readShareTag(ItemStack stack, @Nullable CompoundTag nbt) {
        stack.setTag(nbt);
        MateriaCollectionHandler collectionHandler = CapabilityHelper.getMateriaCollectionHandler(stack);
        collectionHandler.deserializeNBT((CompoundTag) nbt.get("materia_collection_handler"));
    }

    public static void mineBlocksAndApplyRecipe(Level worldIn, BlockPos startPos, Vec3 viewVector, ItemStack stack) {
        if (!(stack.getItem() instanceof IMateriaTool)) {
            return;
        }

        List<ItemStack> selectedMateriaStacks = CapabilityHelper.getCurrentlySelectedMateriaStacks(stack);
        List<AbstractMateriaEffectConfiguration> effects = CapabilityHelper.getCurrentlySelectedEffect(stack);
        for (AbstractMateriaEffectConfiguration effect : effects) {
            if (effect instanceof MiningConfiguration miningEffect) {
                miningEffect.mineBlocks(worldIn, startPos, viewVector, selectedMateriaStacks);
            }
        }
    }
}

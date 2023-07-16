package Tavi007.Materia.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import javax.annotation.Nullable;

import Tavi007.Materia.capabilities.materia.collection.handler.MateriaCollectionHandler;
import Tavi007.Materia.recipes.effects.configuration.MiningConfiguration;
import Tavi007.Materia.recipes.effects.configuration.RecipeConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

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

    public static List<ItemStack> mineBlocks(Level worldIn, BlockPos startPos, MiningConfiguration configuration) {
        List<BlockPos> posList = new ArrayList<BlockPos>();
        List<ItemStack> itemstackList = new ArrayList<ItemStack>();
        Block sourceBlock = worldIn.getBlockState(startPos).getBlock();

        // TODO compute max values depending on line of sight
        int maxDx = 0;
        int maxDy = 0;
        int maxDz = 0;

        for (int dx = -maxDx; dx < maxDx + 1; dx++) {
            for (int dy = -maxDy; dy < maxDy + 1; dy++) {
                for (int dz = -maxDz; dz < maxDz + 1; dz++) {
                    BlockPos pos = new BlockPos(startPos.getX() + dx, startPos.getY() + dy, startPos.getZ() + dz);
                    Block block = worldIn.getBlockState(pos).getBlock();
                    posList.add(pos);

                    if (worldIn instanceof ServerLevel) {
                        if (!worldIn.isEmptyBlock(pos) && block == sourceBlock) {
                            itemstackList.addAll(Block.getDrops(worldIn.getBlockState(pos), (ServerLevel) worldIn, pos, null));
                            worldIn.destroyBlock(pos, false);
                        }
                    }
                }
            }
        }

        return itemstackList;
    }

    public static List<ItemStack> applyRecipe(List<ItemStack> stackIn, RecipeConfiguration configuration) {
        List<ItemStack> stackOut = new ArrayList<ItemStack>();

        return stackOut;
    }
}

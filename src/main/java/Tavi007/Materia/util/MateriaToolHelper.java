package Tavi007.Materia.util;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import Tavi007.Materia.capabilities.toolslots.MateriaCollectionHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class MateriaToolHelper {

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

    private static String asString(int[] array) {
        String ret = "";
        for (int i = 0; i < array.length; i++) {
            switch (array[i]) {
            case (1):
                ret = ret + "O";
                break;
            case (2):
                ret = ret + "O-O";
                break;
            case (3):
                ret = ret + "O-O-O";
                break;
            case (4):
                ret = ret + "O-O-O-O";
                break;
            }
            ret = ret + " ";
        }

        return ret;
    }

    public static List<ItemStack> mineBlocks(Level worldIn, BlockPos startPos, int maxAreaLevel) {
        List<BlockPos> posList = new ArrayList<BlockPos>();
        List<ItemStack> itemstackList = new ArrayList<ItemStack>();
        Block sourceBlock = worldIn.getBlockState(startPos).getBlock();

        for (int dx = -maxAreaLevel; dx < maxAreaLevel + 1; dx++) {
            for (int dy = -maxAreaLevel; dy < maxAreaLevel + 1; dy++) {
                for (int dz = -maxAreaLevel; dz < maxAreaLevel + 1; dz++) {
                    BlockPos pos_ = new BlockPos(startPos.getX() + dx, startPos.getY() + dy, startPos.getZ() + dz);
                    Block block = worldIn.getBlockState(pos_).getBlock();
                    posList.add(pos_);

                    if (worldIn instanceof ServerLevel) {
                        if (!worldIn.isEmptyBlock(pos_) && block == sourceBlock) {
                            itemstackList.addAll(Block.getDrops(worldIn.getBlockState(pos_), (ServerLevel) worldIn, pos_, null));
                            worldIn.destroyBlock(pos_, false);
                        }
                    }
                }
            }
        }

        return itemstackList;
    }
}

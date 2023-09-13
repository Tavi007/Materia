package Tavi007.Materia.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import javax.annotation.Nullable;

import Tavi007.Materia.Materia;
import Tavi007.Materia.capabilities.materia.collection.handler.MateriaCollectionHandler;
import Tavi007.Materia.data.pojo.effects.AbstractMateriaEffect;
import Tavi007.Materia.data.pojo.effects.MiningEffect;
import Tavi007.Materia.data.pojo.effects.MorphItemEffect;
import Tavi007.Materia.items.IMateriaTool;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
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

    public static void mineBlocksAndApplyRecipe(ServerLevel worldIn, BlockPos startPos, Vec3 viewVector, Direction direction, ItemStack toolStack) {
        if (!(toolStack.getItem() instanceof IMateriaTool)) {
            return;
        }
        List<AbstractMateriaEffect> effects = CapabilityHelper.getCurrentlySelectedEffect(toolStack);

        MiningEffect miningEffect = null;
        MorphItemEffect recipeEffect = null;
        for (AbstractMateriaEffect effect : effects) {
            if (effect instanceof MiningEffect) {
                miningEffect = (MiningEffect) effect;
            } else if (effect instanceof MorphItemEffect) {
                recipeEffect = (MorphItemEffect) effect;
            }
        }

        Map<BlockPos, List<ItemStack>> blockPosToStacksMap = new HashMap<>();
        if (miningEffect != null) {
            blockPosToStacksMap = mineBlocks(worldIn, startPos, viewVector, direction, miningEffect);
        } else {
            blockPosToStacksMap.put(startPos, Block.getDrops(worldIn.getBlockState(startPos), worldIn, startPos, null));
        }

        if (recipeEffect != null) {
            for (BlockPos blockPos : blockPosToStacksMap.keySet()) {
                recipeEffect.applyRecipe(blockPosToStacksMap.get(blockPos))
                    .forEach(stack -> Block.popResource(worldIn, blockPos, stack));
            }
        } else {
            blockPosToStacksMap.forEach((blockPos, stacks) -> {
                stacks.forEach(stack -> Block.popResource(worldIn, blockPos, stack));
            });
        }
    }

    private static Map<BlockPos, List<ItemStack>> mineBlocks(Level worldIn, BlockPos startPos, Vec3 viewVector, Direction direction,
            MiningEffect miningEffect) {
        Map<BlockPos, List<ItemStack>> blockPosToStacksMap = new HashMap<>();
        if (worldIn instanceof ServerLevel serverLevel) {

            // define local coordinate system depending on the view vector
            Vec3 vRange = viewVector.normalize();
            Vec3 vWidth;
            if (vRange.x() == 0 && vRange.z() == 0) {
                if (Direction.NORTH.equals(direction) || Direction.SOUTH.equals(direction)) {
                    vWidth = new Vec3(0, 0, 1);
                } else {
                    vWidth = new Vec3(1, 0, 0);
                }
            } else {
                vWidth = new Vec3(0, 1, 0).cross(vRange).normalize();
            }
            Vec3 vHeight = vRange.cross(vWidth).normalize();

            Materia.LOGGER.debug("CoordinateSystem when mining: ");
            Materia.LOGGER.debug("vRange: {}", vRange);
            Materia.LOGGER.debug("vHeight: {}", vHeight);
            Materia.LOGGER.debug("vWidth: {}", vWidth);

            // center of new coordinate system + areaConfiguration defines a cuboid to be mined
            int rangeLevel = miningEffect.getRange();
            int heightLevel = miningEffect.getHeight();
            int widthLevel = miningEffect.getWidth();
            for (int dvRange = 0; dvRange <= rangeLevel * 2; dvRange++) {
                for (int dvHeight = -heightLevel; dvHeight <= heightLevel; dvHeight++) {
                    for (int dvWidth = -widthLevel; dvWidth <= widthLevel; dvWidth++) {

                        long dx = Math.round(vRange.x() * dvRange + vHeight.x() * dvHeight + vWidth.x() * dvWidth);
                        long dy = Math.round(vRange.y() * dvRange + vHeight.y() * dvHeight + vWidth.y() * dvWidth);
                        long dz = Math.round(vRange.z() * dvRange + vHeight.z() * dvHeight + vWidth.z() * dvWidth);

                        BlockPos pos = new BlockPos(startPos.getX() + dx, startPos.getY() + dy, startPos.getZ() + dz);
                        if (!serverLevel.isEmptyBlock(pos) && !blockPosToStacksMap.containsKey(pos)) {
                            blockPosToStacksMap.put(pos, Block.getDrops(worldIn.getBlockState(pos), serverLevel, pos, null));
                            serverLevel.destroyBlock(pos, false);
                        }
                    }
                }
            }
        }
        return blockPosToStacksMap;
    }
}

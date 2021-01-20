package Tavi007.Materia.util;

import java.util.ArrayList;
import java.util.List;

import Tavi007.Materia.effects.IMateriaEffectArea;
import Tavi007.Materia.effects.MateriaEffect;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class MateriaToolHelper {

	public static int getNoCollectionSlot(int[] in) {
		int counter = 0;
		for (int i=0; i<in.length; i++) {
			counter += in[i];
		}
		return counter;
	}

	public static boolean isCollectionSizesValid(int[] in) {
		int counter = 0;
		for (int i=0; i<in.length; i++) {
			if(in[i]<0) {
				return false;
			}
			counter += in[i];
		}
		if (counter>4) {
			return false;
		}
		return true;
	}
	
	public static int getMaxAreaLevel(ItemStack stack) {
		ArrayList<MateriaEffect> effectList = CapabilityHelper.getMateriaCollection(stack).getEffects();
		int[] maxAreaLevel = {0};
		effectList.forEach(effect -> {
			if(effect instanceof IMateriaEffectArea) {
				maxAreaLevel[0] = Math.max(maxAreaLevel[0] , ((IMateriaEffectArea) effect).getAreaLevel());
			}
		});
		return maxAreaLevel[0];
	}

	public static List<ItemStack> mineBlocks(World worldIn, BlockPos startPos, int maxAreaLevel) {
		List<BlockPos> posList = new ArrayList<BlockPos>();
		List<ItemStack> itemstackList = new ArrayList<ItemStack>();
		Block sourceBlock = worldIn.getBlockState(startPos).getBlock();

		for (int dx=-maxAreaLevel; dx<maxAreaLevel+1; dx++) {
			for (int dy=-maxAreaLevel; dy<maxAreaLevel+1; dy++) {
				for (int dz=-maxAreaLevel; dz<maxAreaLevel+1; dz++) {
					BlockPos pos_ = new BlockPos(startPos.getX()+dx, startPos.getY()+dy, startPos.getZ()+dz);
					Block block = worldIn.getBlockState(pos_).getBlock();
					posList.add(pos_);

					if(worldIn instanceof ServerWorld) {
						if(!worldIn.isAirBlock(pos_) && block == sourceBlock) {
							itemstackList.addAll(Block.getDrops(worldIn.getBlockState(pos_), (ServerWorld) worldIn, pos_, null));
							worldIn.destroyBlock(pos_, false);
						}
					}
				}	
			}
		}

		return itemstackList;
	}
}

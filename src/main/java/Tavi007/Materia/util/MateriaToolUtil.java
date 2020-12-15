package Tavi007.Materia.util;

import java.util.ArrayList;
import java.util.List;

import Tavi007.Materia.effects.IMateriaEffectArea;
import Tavi007.Materia.effects.MateriaEffect;
import Tavi007.Materia.items.IMateriaTool;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class MateriaToolUtil {
	
	public static int getMaxAreaLevel(IMateriaTool tool) {
		ArrayList<MateriaEffect> effectList = tool.getMateriaEffectList();
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

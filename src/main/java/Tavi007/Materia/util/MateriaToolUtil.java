package Tavi007.Materia.util;

import java.util.ArrayList;
import java.util.List;

import Tavi007.Materia.effects.IMateriaEffectArea;
import Tavi007.Materia.effects.MateriaEffect;
import Tavi007.Materia.items.BaseMateria;
import Tavi007.Materia.items.IMateriaTool;
import Tavi007.Materia.items.MateriaToolSlotCollection;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class MateriaToolUtil {

	////////////////
	// getFomTool //
	////////////////
	public static ArrayList<BaseMateria> getMateriaFromTool(IMateriaTool tool) {
		ArrayList<BaseMateria> materiaList = new ArrayList<BaseMateria>();
		materiaList.addAll(getMateriaFromSlots(tool.getTopSlots()));
		materiaList.addAll(getMateriaFromSlots(tool.getBotSlots()));
		return materiaList;
	}
	public static ArrayList<ItemStack> getMateriaStacksFromTool(IMateriaTool tool) {
		ArrayList<ItemStack> materiaStackList = new ArrayList<ItemStack>();
		materiaStackList.addAll(getMateriaStacksFromSlots(tool.getTopSlots()));
		materiaStackList.addAll(getMateriaStacksFromSlots(tool.getBotSlots()));
		return materiaStackList;
	}
	public static ArrayList<MateriaEffect> getEffectsFromTool(IMateriaTool tool) {
		ArrayList<MateriaEffect> effectList = new ArrayList<MateriaEffect>();
		effectList.addAll(getEffectsFromSlots(tool.getTopSlots()));
		effectList.addAll(getEffectsFromSlots(tool.getBotSlots()));
		return effectList;
	}

	/////////////////
	// getFomSlots //
	/////////////////
	public static ArrayList<BaseMateria> getMateriaFromSlots(MateriaToolSlotCollection[] slots) {
		ArrayList<BaseMateria> materiaList = new ArrayList<BaseMateria>();
		for(int i=0; i<slots.length; i++) {
			materiaList.addAll(slots[i].getMateriaList());
		}
		return materiaList;
	}
	public static ArrayList<ItemStack> getMateriaStacksFromSlots(MateriaToolSlotCollection[] slots) {
		ArrayList<ItemStack> materiaStackList = new ArrayList<ItemStack>();
		for(int i=0; i<slots.length; i++) {
			materiaStackList.addAll(slots[i].getMateriaStackList());
		}
		return materiaStackList;
	}
	public static ArrayList<MateriaEffect> getEffectsFromSlots(MateriaToolSlotCollection[] slots) {
		ArrayList<MateriaEffect> effectList = new ArrayList<MateriaEffect>();
		for(int i=0; i<slots.length; i++) {
			effectList.addAll(slots[i].effectList);
		}
		return effectList;
	}
	

	
	///////////////////////////////
	// Util functions for Events //
	///////////////////////////////
	public static void addAP(IMateriaTool tool, int amount) {
		addAP(tool.getTopSlots(), amount);
		addAP(tool.getBotSlots(), amount);
	}
	public static void addAP(MateriaToolSlotCollection[] slots, int amount) {
		for(int i=0; i<slots.length; i++) {
			slots[i].addAP(amount);
		}
	}
	
	
	
	
	
	public static int getMaxAreaLevel(IMateriaTool tool) {
		ArrayList<MateriaEffect> effectList = getEffectsFromTool(tool);
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

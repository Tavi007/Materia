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

	///////////////
	// setOnTool //
	///////////////
	public static void setMateriaStack(IMateriaTool tool, ItemStack stack, int id) {
		if (id>=0 && id<4) {
			setMateriaStack(tool.getTopSlots(), stack, id);
		}
		else if (id>=4 && id<8) {
			setMateriaStack(tool.getBotSlots(), stack, id-4);
		}
	}

	////////////////
	// setOnSlots //
	////////////////
	public static void setMateriaStack(MateriaToolSlotCollection[] slots, ItemStack stack, int id) {
		int counter = 0;
		for (int i=0; i<slots.length; i++) {
			if(id < counter+slots[i].getNoSlots()) {
				slots[i].setMateriaStack(stack, id-counter);
				return;
			}
			counter += slots[i].getNoSlots();
		}
	}



	/////////////////
	// getFromTool //
	/////////////////
	public static ArrayList<BaseMateria> getMateria(IMateriaTool tool) {
		ArrayList<BaseMateria> materiaList = new ArrayList<BaseMateria>();
		materiaList.addAll(getMateria(tool.getTopSlots()));
		materiaList.addAll(getMateria(tool.getBotSlots()));
		return materiaList;
	}
	public static ArrayList<ItemStack> getMateriaStacks(IMateriaTool tool) {
		ArrayList<ItemStack> materiaStackList = new ArrayList<ItemStack>();
		materiaStackList.addAll(getMateriaStacks(tool.getTopSlots()));
		materiaStackList.addAll(getMateriaStacks(tool.getBotSlots()));
		return materiaStackList;
	}
	public static ArrayList<MateriaEffect> getEffects(IMateriaTool tool) {
		ArrayList<MateriaEffect> effectList = new ArrayList<MateriaEffect>();
		effectList.addAll(getEffects(tool.getTopSlots()));
		effectList.addAll(getEffects(tool.getBotSlots()));
		return effectList;
	}

	//////////////////
	// getFromSlots //
	//////////////////
	public static ArrayList<BaseMateria> getMateria(MateriaToolSlotCollection[] slots) {
		ArrayList<BaseMateria> materiaList = new ArrayList<BaseMateria>();
		for(int i=0; i<slots.length; i++) {
			materiaList.addAll(slots[i].getMateriaList());
		}
		return materiaList;
	}
	public static ArrayList<ItemStack> getMateriaStacks(MateriaToolSlotCollection[] slots) {
		ArrayList<ItemStack> materiaStackList = new ArrayList<ItemStack>();
		for(int i=0; i<slots.length; i++) {
			materiaStackList.addAll(slots[i].getMateriaStackList());
		}
		return materiaStackList;
	}
	public static ArrayList<MateriaEffect> getEffects(MateriaToolSlotCollection[] slots) {
		ArrayList<MateriaEffect> effectList = new ArrayList<MateriaEffect>();
		for(int i=0; i<slots.length; i++) {
			effectList.addAll(slots[i].effectList);
		}
		return effectList;
	}
	public static int getNumberOfSlots(MateriaToolSlotCollection[] slots) {
		int ret = 0;
		for(int i=0; i<slots.length; i++) {
			ret += slots[i].getNoSlots();
		}
		return ret;
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
		ArrayList<MateriaEffect> effectList = getEffects(tool);
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

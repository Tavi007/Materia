package Tavi007.Materia.util;

import java.util.ArrayList;
import java.util.List;

import Tavi007.Materia.effects.IMateriaEffectArea;
import Tavi007.Materia.effects.MateriaEffect;
import Tavi007.Materia.items.IMateriaTool;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class MateriaToolHelper {

	public static int getNoCollectionSlot(int[] array) {
		int counter = 0;
		for (int i=0; i<array.length; i++) {
			counter += array[i];
		}
		return counter;
	}

	public static boolean isCollectionSizesValid(int[] array) {
		int counter = 0;
		for (int i=0; i<array.length; i++) {
			if(array[i]<0) {
				return false;
			}
			counter += array[i];
		}
		if (counter>4) {
			return false;
		}
		return true;
	}

	public static void addToolTip(ItemStack stack, List<ITextComponent> tooltip) {
		tooltip.add(new StringTextComponent(""  + TextFormatting.GRAY + "Materia Slots:" + TextFormatting.RESET));
		tooltip.add(new StringTextComponent(" " + TextFormatting.GRAY + asString( ((IMateriaTool) stack.getItem()).getTopCollectionSizes() ) + TextFormatting.RESET));
		tooltip.add(new StringTextComponent(" " + TextFormatting.GRAY + asString( ((IMateriaTool) stack.getItem()).getBotCollectionSizes() )  + TextFormatting.RESET));
		tooltip.add(new StringTextComponent(""  + TextFormatting.GRAY + "Materia Effects:" + TextFormatting.RESET));
		addMateriaEffectToTooltip(stack, tooltip);
	}
	
	public static void addMateriaEffectToTooltip(ItemStack stack, List<ITextComponent> tooltip) {
		Item item = stack.getItem();
		if(item instanceof IMateriaTool) {
			IMateriaTool materiaTool = (IMateriaTool) item;
			ArrayList<MateriaEffect> effects = CapabilityHelper.getEffects(stack);
			effects.forEach(effect -> {
				tooltip.add(new StringTextComponent(materiaTool.getEffectTooltip(effect)));
			});
		} 
	}

	private static String asString(int[] array) {
		String ret = "";
		for(int i=0; i<array.length; i++) {
			switch(array[i]) {
			case(1):
				ret = ret + "O";
			break;
			case(2):
				ret = ret + "O-O";
			break;
			case(3):
				ret = ret + "O-O-O";
			break;
			case(4):
				ret = ret + "O-O-O-O";
			break;
			}
			ret = ret + " ";
		}

		return ret;
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

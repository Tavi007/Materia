package Tavi007.Materia.items;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import Tavi007.Materia.effects.MateriaEffect;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class MateriaPickaxe extends PickaxeItem implements IMateriaTool {

	ArrayList<MateriaEffect> effectList;
	
	
	public MateriaPickaxe(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
		super(tier, attackDamageIn, attackSpeedIn, builder);
		
		//this need to be changed, once the crafting/equipping is working
		this.effectList = new ArrayList<MateriaEffect>();
	}

	@Override
	public ITextComponent getName() {
		return new StringTextComponent("Materia Pickaxe");
	}
	
	@Override
	public ArrayList<MateriaEffect> getMateriaEffectList() {
		return this.effectList;
	}

	@Override
	public void setMateriaEffectList(ArrayList<MateriaEffect> list) {
		this.effectList = list;
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new StringTextComponent("hi"));
		effectList.forEach( effect ->{
			tooltip.add(effect.getToolTip());
		});
	}
	
	@Override
   public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
		int maxAreaLevel = getMaxAreaLevel();
		
		//the plan:
		//generate drops (but keep in mind, which level they correspond to)
		//apply recipe effects
		//destroy mined blocks
		//decides which additional block should be mined
		Vector3d lookVec = entityLiving.getLookVec();
		List<BlockPos> posList = new ArrayList<BlockPos>();
		
		Block sourceBlock = worldIn.getBlockState(pos).getBlock();
		for (int dx=-maxAreaLevel; dx<maxAreaLevel+1; dx++) {
			for (int dy=-maxAreaLevel; dy<maxAreaLevel+1; dy++) {
				for (int dz=-maxAreaLevel; dz<maxAreaLevel+1; dz++) {
					BlockPos pos_ = new BlockPos(pos.getX()+dx, pos.getY()+dy, pos.getZ()+dz);
					Block block = worldIn.getBlockState(pos_).getBlock();
					posList.add(pos_);
					
					if(worldIn instanceof ServerWorld) {
						if(!worldIn.isAirBlock(pos_) && block == sourceBlock) {
							List<ItemStack> itemstackList = Block.getDrops(worldIn.getBlockState(pos_), (ServerWorld) worldIn, pos_, null);
							worldIn.destroyBlock(pos_, false);
						}
					}
				}	
			}
		}
		
		return true;
   }
	
	private int getMaxAreaLevel() {
		return 1;
	}
}

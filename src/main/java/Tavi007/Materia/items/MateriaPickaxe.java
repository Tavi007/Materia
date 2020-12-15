package Tavi007.Materia.items;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import Tavi007.Materia.Materia;
import Tavi007.Materia.effects.IMateriaEffectArea;
import Tavi007.Materia.effects.MateriaEffect;
import Tavi007.Materia.effects.MateriaEffectFire;
import Tavi007.Materia.util.MateriaToolUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
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
		effectList = new ArrayList<MateriaEffect>();
		effectList.add(new MateriaEffectFire(new FireMateria(new Item.Properties().group(Materia.MATERIA_GROUP).maxStackSize(1))));
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
		effectList.forEach( effect ->{
			effect.addPickaxeToolTip(tooltip);
		});
	}
	
	@Override
   public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
		int maxAreaLevel = MateriaToolUtil.getMaxAreaLevel(this);
		
		//the plan:
		//generate drops (but keep in mind, which level they correspond to)
		//apply recipe effects
		//destroy mined blocks
		//decides which additional block should be mined
		Vector3d lookVec = entityLiving.getLookVec();
		List<BlockPos> posList = new ArrayList<BlockPos>();

		//move to util class later
		MateriaToolUtil.mineBlocks(worldIn, pos, maxAreaLevel);
		
		return true;
   }
}

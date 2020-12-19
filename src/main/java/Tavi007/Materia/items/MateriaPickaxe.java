package Tavi007.Materia.items;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import Tavi007.Materia.util.MateriaToolUtil;
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

public class MateriaPickaxe extends PickaxeItem implements IMateriaTool {

	private MateriaToolSlotCollection[] topSlots = {new MateriaToolSlotCollection(1), new MateriaToolSlotCollection(3)};
	private MateriaToolSlotCollection[] botSlots = {new MateriaToolSlotCollection(2)};
	
	
	public MateriaPickaxe(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
		super(tier, attackDamageIn, attackSpeedIn, builder);
	}

	@Override
	public ITextComponent getName() {
		return new StringTextComponent("Materia Pickaxe");
	}
	
	@Override
	public MateriaToolSlotCollection[] getTopSlots() {
		return topSlots;
	}

	@Override
	public MateriaToolSlotCollection[] getBotSlots() {
		return botSlots;
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		MateriaToolUtil.getEffectsFromTool(this).forEach( effect ->{
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

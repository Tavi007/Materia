package Tavi007.Materia.items;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import Tavi007.Materia.capabilities.toolslots.MateriaCollection;
import Tavi007.Materia.effects.MateriaEffect;
import Tavi007.Materia.util.CapabilityHelper;
import Tavi007.Materia.util.MateriaEffectHelper;
import Tavi007.Materia.util.MateriaToolHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

//use this as base class for all the different materia pickaxes
public class MateriaPickaxe extends PickaxeItem implements IMateriaTool {

    // change these later
    private final int[] topCollectionSizes;
    private final int[] botCollectionSizes;

    public MateriaPickaxe(Tier tier, int attackDamageIn, float attackSpeedIn, Properties builder, int[] topCollectionSizes, int[] botCollectionSizes) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
        if (MateriaToolHelper.isCollectionSizesValid(topCollectionSizes)) {
            this.topCollectionSizes = topCollectionSizes;
        } else {
            this.topCollectionSizes = new int[] { 0 }; // might need to change this to 1
        }
        if (MateriaToolHelper.isCollectionSizesValid(botCollectionSizes)) {
            this.botCollectionSizes = botCollectionSizes;
        } else {
            this.botCollectionSizes = new int[] { 0 };
        }
    }

    @Override
    public int[] getTopCollectionSizes() {
        return topCollectionSizes;
    }

    @Override
    public int[] getBotCollectionSizes() {
        return botCollectionSizes;
    }

    // @Override
    // public void addInformation(ItemStack stack, @Nullable Level worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
    // MateriaToolHelper.addToolTip(stack, tooltip);
    // }

    @Override
    public boolean mineBlock(ItemStack stack, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        int maxAreaLevel = MateriaToolHelper.getMaxAreaLevel(stack);

        // the plan:
        // generate drops (but keep in mind, which level they correspond to)
        // apply recipe effects
        // destroy mined blocks
        // decides which additional block should be mined
        List<BlockPos> posList = new ArrayList<BlockPos>();

        // move to util class later
        MateriaToolHelper.mineBlocks(worldIn, pos, maxAreaLevel);

        return true;
    }

    @Override
    @Nullable
    public CompoundTag getShareTag(ItemStack stack) {
        CompoundTag nbt = stack.getTag();
        // MateriaCollection collection = CapabilityHelper.getMateriaCollection(stack);
        // nbt.put("collection", collection.serializeNBT());
        return nbt;
    }

    @Override
    @Nullable
    public void readShareTag(ItemStack stack, @Nullable CompoundTag nbt) {
        stack.setTag(nbt);
        MateriaCollection collection = CapabilityHelper.getMateriaCollection(stack);
        collection.deserializeNBT((CompoundTag) nbt.get("collection"));
        MateriaEffectHelper.computeEffectList(stack);
    }

    @Override
    public String getEffectTooltip(MateriaEffect effect) {
        return effect.getPickaxeTooltip();
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }
}

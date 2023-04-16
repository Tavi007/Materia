package Tavi007.Materia.items;

import javax.annotation.Nullable;

import Tavi007.Materia.effects.MateriaEffect;
import Tavi007.Materia.util.MateriaEffectHelper;
import Tavi007.Materia.util.MateriaToolHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class MateriaSword extends SwordItem implements IMateriaTool {

    // change these later
    private final int[] topCollectionSizes;
    private final int[] botCollectionSizes;

    public MateriaSword(Tier tier, int attackDamageIn, float attackSpeedIn, Properties builder, int[] topCollectionSizes, int[] botCollectionSizes) {
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
    @Nullable
    public CompoundTag getShareTag(ItemStack stack) {
        return stack.getTag();
    }

    @Override
    @Nullable
    public void readShareTag(ItemStack stack, @Nullable CompoundTag nbt) {
        stack.setTag(nbt);
        MateriaEffectHelper.computeEffectList(stack);
    }

    @Override
    public String getEffectTooltip(MateriaEffect effect) {
        return effect.getSwordTooltip();
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }
}

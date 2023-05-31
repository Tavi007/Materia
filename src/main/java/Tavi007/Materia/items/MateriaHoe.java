package Tavi007.Materia.items;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import Tavi007.Materia.recipes.effects.configuration.AbstractMateriaEffectConfiguration;
import Tavi007.Materia.recipes.effects.configuration.RecipeConfiguration;
import Tavi007.Materia.recipes.effects.configuration.StatConfiguration;
import Tavi007.Materia.util.MateriaToolHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;

public class MateriaHoe extends HoeItem implements IMateriaTool {

    // change these later
    private final List<Integer> topCollectionSizes;
    private final List<Integer> botCollectionSizes;

    public MateriaHoe(Tier tier, int attackDamageIn, float attackSpeedIn, Properties builder, List<Integer> topCollectionSizes,
            List<Integer> botCollectionSizes) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
        if (MateriaToolHelper.isCollectionSizesValid(topCollectionSizes)) {
            this.topCollectionSizes = topCollectionSizes;
        } else {
            this.topCollectionSizes = Arrays.asList(0); // might need to change this to 1
        }
        if (MateriaToolHelper.isCollectionSizesValid(botCollectionSizes)) {
            this.botCollectionSizes = botCollectionSizes;
        } else {
            this.botCollectionSizes = Arrays.asList(0);
        }
    }

    @Override
    public List<Integer> getTopCollectionSizes() {
        return topCollectionSizes;
    }

    @Override
    public List<Integer> getBotCollectionSizes() {
        return botCollectionSizes;
    }

    @Override
    public List<List<Integer>> getTopSlotIdMappings() {
        return MateriaToolHelper.fromCollectionSizesToIdMappings(topCollectionSizes, 0);
    }

    @Override
    public List<List<Integer>> getBotSlotIdMappings() {
        return MateriaToolHelper.fromCollectionSizesToIdMappings(botCollectionSizes, 4);
    }

    @Override
    @Nullable
    public CompoundTag getShareTag(ItemStack stack) {
        return MateriaToolHelper.getShareTag(stack);
    }

    @Override
    @Nullable
    public void readShareTag(ItemStack stack, @Nullable CompoundTag nbt) {
        MateriaToolHelper.readShareTag(stack, nbt);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public String getDescriptionIdSuffix() {
        return "tool";
    }

    @Override
    public boolean canConfigurationBeApplied(AbstractMateriaEffectConfiguration configuration) {
        return configuration instanceof RecipeConfiguration
            || configuration instanceof StatConfiguration;
    }
}

package Tavi007.Materia.items;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import Tavi007.Materia.recipes.effects.configuration.AbstractMateriaEffectConfiguration;
import Tavi007.Materia.recipes.effects.configuration.RecipeConfiguration;
import Tavi007.Materia.recipes.effects.configuration.SpellConfiguration;
import Tavi007.Materia.recipes.effects.configuration.StatConfiguration;
import Tavi007.Materia.util.MateriaToolHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;

public class MateriaWand extends TieredItem implements IMateriaTool {

    // change these later
    private final List<Integer> topCollectionSizes;
    private final List<Integer> botCollectionSizes;

    public MateriaWand(Tier tier, Properties properties, List<Integer> topCollectionSizes, List<Integer> botCollectionSizes) {
        super(tier, properties);
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
        return "wand";
    }

    @Override
    public boolean canConfigurationBeApplied(AbstractMateriaEffectConfiguration configuration) {
        return configuration instanceof SpellConfiguration
            || configuration instanceof RecipeConfiguration
            || configuration instanceof StatConfiguration;
    }
}

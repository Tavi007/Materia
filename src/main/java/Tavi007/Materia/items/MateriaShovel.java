package Tavi007.Materia.items;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import Tavi007.Materia.effects.configurations.AbstractMateriaEffectConfiguration;
import Tavi007.Materia.effects.configurations.MiningConfiguration;
import Tavi007.Materia.effects.configurations.RecipeConfiguration;
import Tavi007.Materia.effects.configurations.StatConfiguration;
import Tavi007.Materia.util.MateriaToolHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class MateriaShovel extends ShovelItem implements IMateriaTool {

    // change these later
    private final List<Integer> topCollectionSizes;
    private final List<Integer> botCollectionSizes;

    public MateriaShovel(Tier tier, int attackDamageIn, float attackSpeedIn, Properties builder, List<Integer> topCollectionSizes,
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
            || configuration instanceof StatConfiguration
            || configuration instanceof MiningConfiguration;
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (worldIn instanceof ServerLevel serverWorld) {
            MateriaToolHelper.mineBlocksAndApplyRecipe(serverWorld, pos, entityLiving.getViewVector(0), stack);
        }
        return true;
    }
}

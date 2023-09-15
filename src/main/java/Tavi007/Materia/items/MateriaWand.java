package Tavi007.Materia.items;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import Tavi007.ElementalCombat.api.AttackDataAPI;
import Tavi007.ElementalCombat.capabilities.attack.AttackLayer;
import Tavi007.Materia.Materia;
import Tavi007.Materia.capabilities.materia.collection.handler.MateriaCollectionHandler;
import Tavi007.Materia.data.pojo.effects.SpellEffect;
import Tavi007.Materia.data.pojo.effects.configurations.AbstractMateriaEffectConfiguration;
import Tavi007.Materia.data.pojo.effects.configurations.MorphItemConfiguration;
import Tavi007.Materia.data.pojo.effects.configurations.SpellConfiguration;
import Tavi007.Materia.data.pojo.effects.configurations.StatConfiguration;
import Tavi007.Materia.entities.SpellProjectileEntity;
import Tavi007.Materia.init.EntityTypeList;
import Tavi007.Materia.util.CapabilityHelper;
import Tavi007.Materia.util.MateriaToolHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

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
            || configuration instanceof MorphItemConfiguration
            || configuration instanceof StatConfiguration;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionhand) {
        ItemStack stack = player.getItemInHand(interactionhand);
        Vec3 shootDirection = player.getViewVector(0);
        MateriaCollectionHandler collectionHandler = CapabilityHelper.getMateriaCollectionHandler(stack);
        collectionHandler.getSelectedEffects()
            .stream()
            .filter(effect -> effect instanceof SpellEffect)
            .map(effect -> (SpellEffect) effect)
            .forEach(effect -> effect.getSpellEntityEffects()
                .stream()
                .forEach(entityEffect -> {
                    SpellProjectileEntity entity = new SpellProjectileEntity(
                        EntityTypeList.SPELL_PROJECTILE.get(),
                        player.level,
                        player,
                        shootDirection,
                        entityEffect,
                        effect.getMessageId());
                    player.level.addFreshEntity(entity);
                    AttackDataAPI.putLayer(entity, new AttackLayer("magic", entityEffect.getElement()), new ResourceLocation(Materia.MOD_ID, "spell"));

                    // TODO use spell delay here or create some sort of caching logic somewhere
                }));

        // TODO use cooldown to prevent spell spamming

        return InteractionResultHolder.pass(stack);
    }
}

package Tavi007.Materia.items;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import Tavi007.ElementalCombat.api.AttackDataAPI;
import Tavi007.ElementalCombat.capabilities.attack.AttackLayer;
import Tavi007.Materia.Materia;
import Tavi007.Materia.capabilities.materia.collection.handler.MateriaCollectionHandler;
import Tavi007.Materia.data.pojo.configurations.AbstractMateriaEffectConfiguration;
import Tavi007.Materia.data.pojo.configurations.MorphItemConfiguration;
import Tavi007.Materia.data.pojo.configurations.SpellConfiguration;
import Tavi007.Materia.data.pojo.configurations.StatConfiguration;
import Tavi007.Materia.entities.SpellProjectileEntity;
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
        MateriaCollectionHandler collectionHandler = CapabilityHelper.getMateriaCollectionHandler(stack);
        List<ItemStack> selectedMateriaStacks = collectionHandler.getSelectedMateriaStacks();

        collectionHandler.getSelectedEffectConfigurations()
            .stream()
            .filter(configuration -> configuration instanceof SpellConfiguration)
            .map(configuration -> (SpellConfiguration) configuration)
            .forEach(configuration -> configuration.getEntityConfigurations()
                .stream()
                .filter(entityConfiguration -> entityConfiguration.isSpawnable(selectedMateriaStacks))
                .forEach(entityConfiguration -> {
                    SpellProjectileEntity entity = new SpellProjectileEntity(
                        player.level,
                        player.getX(),
                        player.getY(),
                        player.getZ(),
                        entityConfiguration.getDamage(selectedMateriaStacks),
                        entityConfiguration.getMessageId(),
                        entityConfiguration.getTexture());
                    player.level.addFreshEntity(entity);
                    AttackDataAPI.putLayer(entity, new AttackLayer("magic", entityConfiguration.getElement()), new ResourceLocation(Materia.MOD_ID, "spell"));

                    // TODO use spell delay here or create some sort of caching logic somewhere
                }));

        // TODO use cooldown to prevent spell spamming

        return InteractionResultHolder.pass(stack);
    }
}

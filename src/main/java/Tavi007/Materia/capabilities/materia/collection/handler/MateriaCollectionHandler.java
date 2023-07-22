package Tavi007.Materia.capabilities.materia.collection.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import Tavi007.Materia.init.ReloadListenerList;
import Tavi007.Materia.items.IMateriaTool;
import Tavi007.Materia.items.MateriaItem;
import Tavi007.Materia.recipes.effects.MateriaEffectRecipePojo;
import Tavi007.Materia.recipes.effects.configuration.AbstractMateriaEffectConfiguration;
import Tavi007.Materia.util.CapabilityHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class MateriaCollectionHandler extends ItemStackHandler {

    public final static int MAX_ITEM_SLOTS = 8;
    private List<CollectionToEffectRecipeMapper> mappers;
    private int selectedMapperIndex;

    public MateriaCollectionHandler() {
        super(MAX_ITEM_SLOTS);
        mappers = Collections.emptyList();
        selectedMapperIndex = 0;
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        return stack.getItem() instanceof MateriaItem;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.put("stacks", super.serializeNBT());
        ListTag listTag = new ListTag();
        if (mappers != null) {
            mappers.forEach(mapper -> listTag.add(mapper.serializeNBT()));
        }
        nbt.put("mappers", listTag);
        nbt.putInt("selected_mapper", selectedMapperIndex);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        super.deserializeNBT(nbt.getCompound("stacks"));
        mappers = new ArrayList<>();
        ListTag listTag = (ListTag) nbt.get("mappers");
        if (listTag != null) {
            listTag.forEach(tag -> {
                CollectionToEffectRecipeMapper mapper = new CollectionToEffectRecipeMapper();
                mapper.deserializeNBT((CompoundTag) tag);
                mappers.add(mapper);
            });
        }
        selectedMapperIndex = nbt.getInt("selected_mapper");
    }

    public void computeEffects(ItemStack toolStack) {
        Item tool = toolStack.getItem();
        if (tool instanceof IMateriaTool materiaTool) {
            mappers = computeCollectionToEffectRecipeMapper(materiaTool.getTopSlotIdMappings(), materiaTool);
            mappers.addAll(computeCollectionToEffectRecipeMapper(materiaTool.getBotSlotIdMappings(), materiaTool));
        }
    }

    private List<CollectionToEffectRecipeMapper> computeCollectionToEffectRecipeMapper(List<List<Integer>> slotIdMappings, IMateriaTool materiaTool) {
        List<CollectionToEffectRecipeMapper> mappers = new ArrayList<>();
        for (List<Integer> slotIndexList : slotIdMappings) {
            CollectionToEffectRecipeMapper mapper = new CollectionToEffectRecipeMapper(slotIndexList, computeEffectConfigurations(slotIndexList, materiaTool));
            if (mapper.hasEffectConfigurations()) {
                mappers.add(mapper);
            }
        }
        return mappers;
    }

    private List<AbstractMateriaEffectConfiguration> computeEffectConfigurations(List<Integer> slotIndexList, IMateriaTool materiaTool) {
        List<ItemStack> stacks = new ArrayList<>();
        for (Integer index : slotIndexList) {
            ItemStack stack = getStackInSlot(index);
            if (!stack.isEmpty()) {
                stacks.add(stack);
            }
        }

        List<AbstractMateriaEffectConfiguration> configurations = new ArrayList<>();
        List<ResourceLocation> effectRecipes = ReloadListenerList.MATERIA_EFFECT_RECIPE_MANGER.getEffects(stacks);
        for (ResourceLocation effectRecipe : effectRecipes) {
            MateriaEffectRecipePojo recipePojo = ReloadListenerList.MATERIA_EFFECT_RECIPE_MANGER.getRecipePojo(effectRecipe);
            if (recipePojo != null) {
                for (ResourceLocation effect : recipePojo.getOutput()) {
                    AbstractMateriaEffectConfiguration configuration = ReloadListenerList.MATERIA_EFFECT_CONFIGURATION_MANGER.getConfiguration(effect);
                    if (configuration != null && materiaTool.canConfigurationBeApplied(configuration)) {
                        configurations.add(configuration);
                    }
                }
            }
        }
        return configurations;
    }

    public int getSelectedConfigurationsIndex() {
        return this.selectedMapperIndex;
    }

    public void setSelectedConfigurationsIndex(int index) {
        this.selectedMapperIndex = index;
    }

    public List<AbstractMateriaEffectConfiguration> getSelectedEffectConfigurations() {
        return getEffectConfigurations(selectedMapperIndex);
    }

    public List<AbstractMateriaEffectConfiguration> getEffectConfigurations(int index) {
        if (mappers.isEmpty() || index < 0 || index >= mappers.size()) {
            return Collections.emptyList();
        }
        return mappers.get(index).getEffectConfigurations();
    }

    public List<ItemStack> getSelectedMateriaStacks() {
        return getMateriaStacks(selectedMapperIndex);
    }

    public List<ItemStack> getMateriaStacks(int index) {
        if (mappers.isEmpty() || index < 0 || index >= mappers.size()) {
            return Collections.emptyList();
        }
        List<ItemStack> stacksOfCollection = new ArrayList<>();
        mappers.get(index).getSlotIndexList().forEach(slotIndex -> stacksOfCollection.add(getStackInSlot(slotIndex)));
        return stacksOfCollection;
    }

    public List<CollectionToEffectRecipeMapper> getCollectionToEffectRecipeMapper() {
        return Collections.unmodifiableList(mappers);
    }

    public void addAp(int ap) {
        for (ItemStack stack : stacks) {
            if (!stack.isEmpty() && isItemValid(0, stack)) {
                int[] apToNextLevel = ((MateriaItem) stack.getItem()).getApToNextLevel();
                CapabilityHelper.getLevelData(stack).addAP(ap, apToNextLevel);
            }
        }
    }
}

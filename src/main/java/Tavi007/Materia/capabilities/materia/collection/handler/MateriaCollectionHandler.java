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
import net.minecraftforge.registries.ForgeRegistries;

public class MateriaCollectionHandler extends ItemStackHandler {

    private final static int maxItemStackSlots = 8;
    private List<CollectionToEffectRecipeMapper> mappers;
    private int selectedMapperIndex;

    public MateriaCollectionHandler() {
        super(maxItemStackSlots);
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
            mappers = computeCollectionToEffectRecipeMapper(materiaTool.getTopSlotIdMappings());
            mappers.addAll(computeCollectionToEffectRecipeMapper(materiaTool.getBotSlotIdMappings()));
        }
    }

    private List<CollectionToEffectRecipeMapper> computeCollectionToEffectRecipeMapper(List<List<Integer>> slotIdMappings) {
        List<CollectionToEffectRecipeMapper> mappers = new ArrayList<>();
        for (List<Integer> slotIndexList : slotIdMappings) {
            CollectionToEffectRecipeMapper mapper = new CollectionToEffectRecipeMapper(slotIndexList, computeEffects(slotIndexList));
            mappers.add(mapper);
        }
        return mappers;
    }

    private List<ResourceLocation> computeEffects(List<Integer> slotIndexList) {
        List<ResourceLocation> itemLocations = new ArrayList<>();
        for (Integer index : slotIndexList) {
            ItemStack stack = getStackInSlot(index);
            if (!stack.isEmpty()) {
                itemLocations.add(ForgeRegistries.ITEMS.getKey(stack.getItem()));
            }
        }
        return ReloadListenerList.MATERIA_EFFECT_RECIPE_MANGER.getEffects(itemLocations);
    }

    public List<AbstractMateriaEffectConfiguration> getSelectedEffectConfigurations() {
        if (mappers.isEmpty()) {
            return Collections.emptyList();
        }

        if (selectedMapperIndex < 0 || selectedMapperIndex >= mappers.size()) {
            selectedMapperIndex = 0;
        }

        // TODO: maybe optimize here?
        List<AbstractMateriaEffectConfiguration> configurations = new ArrayList<>();
        List<ResourceLocation> effectRecipes = mappers.get(selectedMapperIndex).getEffectRecipes();
        for (ResourceLocation effectRecipe : effectRecipes) {
            MateriaEffectRecipePojo recipePojo = ReloadListenerList.MATERIA_EFFECT_RECIPE_MANGER.getRecipePojo(effectRecipe);
            for (ResourceLocation effect : recipePojo.getOutput()) {
                AbstractMateriaEffectConfiguration configuration = ReloadListenerList.MATERIA_EFFECT_CONFIGURATION_MANGER.getConfiguration(effect);
                if (configuration != null) {
                    configurations.add(configuration);
                }
            }
        }
        return configurations;
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

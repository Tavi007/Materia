package Tavi007.Materia.capabilities.materia.collection.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import Tavi007.Materia.capabilities.level.LevelData;
import Tavi007.Materia.data.pojo.MateriaEffectRecipe;
import Tavi007.Materia.data.pojo.effects.AbstractMateriaEffect;
import Tavi007.Materia.init.ReloadListenerList;
import Tavi007.Materia.items.IMateriaTool;
import Tavi007.Materia.items.MateriaItem;
import Tavi007.Materia.util.CapabilityHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.item.ItemEntity;
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
            CollectionToEffectRecipeMapper mapper = new CollectionToEffectRecipeMapper(slotIndexList, computeEffects(slotIndexList, materiaTool));
            if (mapper.hasEffectConfigurations()) {
                mappers.add(mapper);
            }
        }
        return mappers;
    }

    private List<AbstractMateriaEffect> computeEffects(List<Integer> slotIndexList, IMateriaTool materiaTool) {
        List<ItemStack> stacksOfSlots = getStacks(slotIndexList);

        List<AbstractMateriaEffect> effects = new ArrayList<>();
        List<ResourceLocation> effectRecipes = ReloadListenerList.MATERIA_EFFECT_RECIPE_MANGER.getEffects(stacksOfSlots);
        for (ResourceLocation effectRecipe : effectRecipes) {
            MateriaEffectRecipe recipePojo = ReloadListenerList.MATERIA_EFFECT_RECIPE_MANGER.getRecipePojo(effectRecipe);
            if (recipePojo != null) {
                for (ResourceLocation effectName : recipePojo.getOutput()) {
                    AbstractMateriaEffect effect = ReloadListenerList.MATERIA_EFFECT_CONFIGURATION_MANGER.getEffect(effectName, materiaTool, stacksOfSlots);
                    if (effect != null) {
                        effects.add(effect);
                    }
                }
            }
        }
        return effects;
    }

    private List<ItemStack> getStacks(List<Integer> slotIndexList) {
        List<ItemStack> stacks = new ArrayList<>();
        for (Integer index : slotIndexList) {
            ItemStack stack = getStackInSlot(index);
            if (!stack.isEmpty()) {
                stacks.add(stack);
            }
        }
        return stacks;
    }

    public int getSelectedConfigurationsIndex() {
        return this.selectedMapperIndex;
    }

    public void setSelectedConfigurationsIndex(int index) {
        this.selectedMapperIndex = index;
    }

    public List<AbstractMateriaEffect> getSelectedEffects() {
        return getEffects(selectedMapperIndex);
    }

    public List<AbstractMateriaEffect> getEffects(int index) {
        if (mappers.isEmpty() || index < 0 || index >= mappers.size()) {
            return Collections.emptyList();
        }
        return mappers.get(index).getEffects();
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

    public void addAp(ItemStack tool, int ap, ServerLevel level, BlockPos pos) {
        boolean levelUpHappened = false;
        for (ItemStack stack : stacks) {
            if (!stack.isEmpty() && isItemValid(0, stack)) {
                LevelData levelData = CapabilityHelper.getLevelData(stack);
                levelUpHappened = levelData.addAP(ap);
                if (levelUpHappened && levelData.isMaxLevel()) {
                    double x = (double) ((float) pos.getX() + 0.5F) + Mth.nextDouble(level.random, -0.25D, 0.25D);
                    double y = (double) ((float) pos.getY() + 0.5F) + Mth.nextDouble(level.random, -0.25D, 0.25D);
                    double z = (double) ((float) pos.getZ() + 0.5F) + Mth.nextDouble(level.random, -0.25D, 0.25D);
                    ItemEntity itemEntity = new ItemEntity(level, x, y, z, new ItemStack(stack.getItem()));
                    itemEntity.setDefaultPickUpDelay();
                    level.addFreshEntity(itemEntity);
                }
            }
        }

        if (levelUpHappened) {
            computeEffects(tool);
        }
    }
}

package Tavi007.Materia.capabilities.materia.collection.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import Tavi007.Materia.init.ReloadListenerList;
import Tavi007.Materia.items.IMateriaTool;
import Tavi007.Materia.items.MateriaItem;
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
    private List<CollectionToEffectMapper> mappers;
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
                CollectionToEffectMapper mapper = new CollectionToEffectMapper();
                mapper.deserializeNBT((CompoundTag) tag);
                mappers.add(mapper);
            });
        }
        selectedMapperIndex = nbt.getInt("selected_mapper");
    }

    public void computeEffects(ItemStack toolStack) {
        Item tool = toolStack.getItem();
        if (tool instanceof IMateriaTool materiaTool) {
            mappers = getCollectionToEffectMapper(materiaTool.getTopSlotIdMappings());
            mappers.addAll(getCollectionToEffectMapper(materiaTool.getBotSlotIdMappings()));
        }
    }

    private List<CollectionToEffectMapper> getCollectionToEffectMapper(List<List<Integer>> slotIdMappings) {
        List<CollectionToEffectMapper> mappers = new ArrayList<>();
        for (List<Integer> slotIndexList : slotIdMappings) {
            CollectionToEffectMapper mapper = new CollectionToEffectMapper(slotIndexList, getEffects(slotIndexList));
            mappers.add(mapper);
        }
        return mappers;
    }

    private List<ResourceLocation> getEffects(List<Integer> slotIndexList) {
        List<ResourceLocation> itemLocations = new ArrayList<>();
        for (Integer index : slotIndexList) {
            ItemStack stack = getStackInSlot(index);
            if (!stack.isEmpty()) {
                itemLocations.add(ForgeRegistries.ITEMS.getKey(stack.getItem()));
            }
        }
        return ReloadListenerList.MATERIA_EFFECT_RECIPE_MANGER.getEffects(itemLocations);
    }

    public List<ResourceLocation> getSelectedEffects() {
        if (selectedMapperIndex < 0 || selectedMapperIndex >= mappers.size()) {
            selectedMapperIndex = 0;
        }
        return mappers.get(selectedMapperIndex).getEffects();
    }

    public List<ResourceLocation> getAllEffects() {
        List<ResourceLocation> effects = new ArrayList<>();
        mappers.forEach(mapper -> effects.addAll(mapper.getEffects()));
        return effects;
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

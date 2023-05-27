package Tavi007.Materia.capabilities.materia.collection.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import Tavi007.Materia.items.MateriaItem;
import Tavi007.Materia.util.CapabilityHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class MateriaCollectionHandler extends ItemStackHandler {

    private final static int maxItemStackSlots = 8;
    private List<CollectionToEffectMapper> mappers;

    public MateriaCollectionHandler() {
        super(maxItemStackSlots);
        mappers = Collections.emptyList();
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
        mappers.forEach(mapper -> listTag.add(mapper.serializeNBT()));
        nbt.put("mappers", listTag);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        super.deserializeNBT(nbt.getCompound("stacks"));
        mappers = new ArrayList<>();
        ListTag listTag = (ListTag) nbt.get("mappers");
        listTag.forEach(tag -> {
            CollectionToEffectMapper mapper = new CollectionToEffectMapper();
            mapper.deserializeNBT(nbt);
            mappers.add(mapper);
        });
    }

    public void markDirty(int slotIndex) {
        for (CollectionToEffectMapper mapper : mappers) {
            if (mapper.hasSlotIndex(slotIndex)) {
                mapper.markAsDirty();
            }
        }
    }

    public List<ResourceLocation> getEffects(int selectedCollectionId) {
        if (selectedCollectionId >= 0 && selectedCollectionId < mappers.size()) {
            return mappers.get(selectedCollectionId).getEffects();
        }
        return Collections.emptyList();
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

package Tavi007.Materia.capabilities.materia.collection.handler;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;

public class CollectionToEffectMapper implements INBTSerializable<CompoundTag> {

    List<Integer> slotIndexList;
    List<ResourceLocation> effects;
    private boolean dirty;

    protected CollectionToEffectMapper() {
        this.slotIndexList = new ArrayList<>();
        this.effects = new ArrayList<>();
    }

    public CollectionToEffectMapper(List<Integer> slotIndexList, List<ResourceLocation> effects) {
        this.slotIndexList = slotIndexList;
        this.effects = effects;
    }

    public List<Integer> getSlotIndexList() {
        return slotIndexList;
    }

    public List<ResourceLocation> getEffects() {
        if (dirty) {
            // TODO recompute effects
            dirty = false;
        }
        return effects;
    }

    public boolean hasSlotIndex(int slotIndex) {
        return slotIndexList.contains(slotIndex);
    }

    public void markAsDirty() {
        dirty = true;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.put("slot_index_list", new IntArrayTag(slotIndexList));
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        slotIndexList = new ArrayList<>();
        IntArrayTag arrayTag = (IntArrayTag) nbt.get("slot_index_list");
        arrayTag.forEach(tag -> slotIndexList.add(tag.getAsInt()));
        dirty = true;
    }

}

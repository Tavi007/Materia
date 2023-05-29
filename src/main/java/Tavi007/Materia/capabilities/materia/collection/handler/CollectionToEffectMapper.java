package Tavi007.Materia.capabilities.materia.collection.handler;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;

public class CollectionToEffectMapper implements INBTSerializable<CompoundTag> {

    List<Integer> slotIndexList;
    List<ResourceLocation> effects;

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
        return effects;
    }

    public boolean hasSlotIndex(int slotIndex) {
        return slotIndexList.contains(slotIndex);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        IntArrayTag slotIndexTag = new IntArrayTag(slotIndexList);
        nbt.put("slot_index_list", slotIndexTag);
        ListTag effectsTag = new ListTag();
        if (effects != null) {
            effects.forEach(effect -> effectsTag.add(StringTag.valueOf(effect.toString())));
        }
        nbt.put("effects", effectsTag);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        slotIndexList = new ArrayList<>();
        IntArrayTag slotIndexTag = (IntArrayTag) nbt.get("slot_index_list");
        if (slotIndexTag != null) {
            slotIndexTag.forEach(tag -> slotIndexList.add(tag.getAsInt()));
        }
        effects = new ArrayList<>();
        ListTag effectsTag = (ListTag) nbt.get("effects");
        if (effectsTag != null) {
            effectsTag.forEach(effectTag -> {
                StringTag stringTag = (StringTag) effectTag;
                effects.add(new ResourceLocation(stringTag.getAsString()));
            });
        }
    }

    @Override
    public String toString() {
        return "{slotIndexList:" + slotIndexList.toString() + "=>effects:" + effects.toString() + "}";
    }

}

package Tavi007.Materia.capabilities.materia.collection.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import Tavi007.Materia.data.pojo.effects.AbstractMateriaEffect;
import Tavi007.Materia.registries.MateriaEffectRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.ListTag;
import net.minecraftforge.common.util.INBTSerializable;

public class CollectionToEffectRecipeMapper implements INBTSerializable<CompoundTag> {

    List<Integer> slotIndexList;
    List<AbstractMateriaEffect> effects;

    protected CollectionToEffectRecipeMapper() {
        this.slotIndexList = new ArrayList<>();
        this.effects = new ArrayList<>();
    }

    public CollectionToEffectRecipeMapper(List<Integer> slotIndexList, List<AbstractMateriaEffect> effects) {
        this.slotIndexList = slotIndexList;
        this.effects = effects;
    }

    public List<Integer> getSlotIndexList() {
        return slotIndexList;
    }

    public List<AbstractMateriaEffect> getEffects() {
        return effects;
    }

    public boolean hasEffectConfigurations() {
        return effects != null && !effects.isEmpty();
    }

    public boolean hasSlotIndex(int slotIndex) {
        return slotIndexList.contains(slotIndex);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        IntArrayTag slotIndexTag = new IntArrayTag(slotIndexList);
        tag.put("slot_index_list", slotIndexTag);

        ListTag effectsTags = new ListTag();
        List<AbstractMateriaEffect> effects = getEffects();
        if (effects != null) {
            effectsTags.addAll(effects.stream().map(AbstractMateriaEffect::serializeNBT).collect(Collectors.toList()));
        }
        tag.put("effects", effectsTags);

        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        slotIndexList = new ArrayList<>();
        IntArrayTag slotIndexTag = (IntArrayTag) tag.get("slot_index_list");
        if (slotIndexTag != null) {
            slotIndexTag.forEach(entry -> slotIndexList.add(entry.getAsInt()));
        }

        ListTag list = tag.getList("effects", CompoundTag.TAG_COMPOUND);
        effects = list.stream()
            .map(entry -> (CompoundTag) entry)
            .map(entry -> MateriaEffectRegistry.get(entry.getString("class_name"), entry))
            .filter(entry -> entry != null)
            .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "{slotIndexList:" + slotIndexList.toString() + "=>effectRecipes:" + effects.toString() + "}";
    }

}

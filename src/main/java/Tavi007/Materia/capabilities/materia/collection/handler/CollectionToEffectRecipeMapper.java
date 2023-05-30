package Tavi007.Materia.capabilities.materia.collection.handler;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;

public class CollectionToEffectRecipeMapper implements INBTSerializable<CompoundTag> {

    List<Integer> slotIndexList;
    List<ResourceLocation> effectRecipes;

    protected CollectionToEffectRecipeMapper() {
        this.slotIndexList = new ArrayList<>();
        this.effectRecipes = new ArrayList<>();
    }

    public CollectionToEffectRecipeMapper(List<Integer> slotIndexList, List<ResourceLocation> effectRecipes) {
        this.slotIndexList = slotIndexList;
        this.effectRecipes = effectRecipes;
    }

    public List<Integer> getSlotIndexList() {
        return slotIndexList;
    }

    public List<ResourceLocation> getEffectRecipes() {
        return effectRecipes;
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
        if (effectRecipes != null) {
            effectRecipes.forEach(effect -> effectsTag.add(StringTag.valueOf(effect.toString())));
        }
        nbt.put("effect_recipes", effectsTag);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        slotIndexList = new ArrayList<>();
        IntArrayTag slotIndexTag = (IntArrayTag) nbt.get("slot_index_list");
        if (slotIndexTag != null) {
            slotIndexTag.forEach(tag -> slotIndexList.add(tag.getAsInt()));
        }
        effectRecipes = new ArrayList<>();
        ListTag effectsTag = (ListTag) nbt.get("effect_recipes");
        if (effectsTag != null) {
            effectsTag.forEach(effectTag -> {
                StringTag stringTag = (StringTag) effectTag;
                effectRecipes.add(new ResourceLocation(stringTag.getAsString()));
            });
        }
    }

    @Override
    public String toString() {
        return "{slotIndexList:" + slotIndexList.toString() + "=>effectRecipes:" + effectRecipes.toString() + "}";
    }

}

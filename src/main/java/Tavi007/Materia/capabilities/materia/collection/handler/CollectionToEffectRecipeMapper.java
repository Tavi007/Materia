package Tavi007.Materia.capabilities.materia.collection.handler;

import java.util.ArrayList;
import java.util.List;

import Tavi007.Materia.data.pojo.configurations.AbstractMateriaEffectConfiguration;
import Tavi007.Materia.init.ReloadListenerList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;

public class CollectionToEffectRecipeMapper implements INBTSerializable<CompoundTag> {

    List<Integer> slotIndexList;
    List<AbstractMateriaEffectConfiguration> effectConfigurations;

    protected CollectionToEffectRecipeMapper() {
        this.slotIndexList = new ArrayList<>();
        this.effectConfigurations = new ArrayList<>();
    }

    public CollectionToEffectRecipeMapper(List<Integer> slotIndexList, List<AbstractMateriaEffectConfiguration> effectConfigurations) {
        this.slotIndexList = slotIndexList;
        this.effectConfigurations = effectConfigurations;
    }

    public List<Integer> getSlotIndexList() {
        return slotIndexList;
    }

    public List<AbstractMateriaEffectConfiguration> getEffectConfigurations() {
        return effectConfigurations;
    }

    public boolean hasEffectConfigurations() {
        return effectConfigurations != null && !effectConfigurations.isEmpty();
    }

    public boolean hasSlotIndex(int slotIndex) {
        return slotIndexList.contains(slotIndex);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        IntArrayTag slotIndexTag = new IntArrayTag(slotIndexList);
        nbt.put("slot_index_list", slotIndexTag);
        ListTag configurationIds = new ListTag();
        if (effectConfigurations != null) {
            effectConfigurations.forEach(configuration -> configurationIds.add(StringTag.valueOf(configuration.getId().toString())));
        }
        nbt.put("effect_configuration_ids", configurationIds);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        slotIndexList = new ArrayList<>();
        IntArrayTag slotIndexTag = (IntArrayTag) nbt.get("slot_index_list");
        if (slotIndexTag != null) {
            slotIndexTag.forEach(tag -> slotIndexList.add(tag.getAsInt()));
        }
        effectConfigurations = new ArrayList<>();
        ListTag configurationIds = (ListTag) nbt.get("effect_configuration_ids");
        if (configurationIds != null) {
            configurationIds.forEach(entry -> {
                StringTag configTag = (StringTag) entry;
                ResourceLocation configurationId = new ResourceLocation(configTag.getAsString());
                AbstractMateriaEffectConfiguration configuration = ReloadListenerList.MATERIA_EFFECT_CONFIGURATION_MANGER.getConfiguration(configurationId);
                if (configuration != null) {
                    effectConfigurations.add(configuration);
                }
            });
        }
    }

    @Override
    public String toString() {
        return "{slotIndexList:" + slotIndexList.toString() + "=>effectRecipes:" + effectConfigurations.toString() + "}";
    }

}

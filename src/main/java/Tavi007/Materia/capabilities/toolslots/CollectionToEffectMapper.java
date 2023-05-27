package Tavi007.Materia.capabilities.toolslots;

import java.util.List;

import net.minecraft.resources.ResourceLocation;

public class CollectionToEffectMapper {

    List<Integer> slotIndexList;
    List<ResourceLocation> effects;
    private boolean dirty;

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

}

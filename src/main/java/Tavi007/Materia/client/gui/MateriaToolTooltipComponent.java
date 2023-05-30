package Tavi007.Materia.client.gui;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import Tavi007.Materia.capabilities.materia.collection.handler.MateriaCollectionHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

public class MateriaToolTooltipComponent implements TooltipComponent {

    private MateriaCollectionHandler materiaCollection;
    private List<Integer> topCollectionSizes;
    private List<Integer> botCollectionSizes;
    private String descriptionIdSuffix;

    public MateriaToolTooltipComponent(List<Integer> topCollectionSizes, List<Integer> botCollectionSizes, MateriaCollectionHandler materiaCollection,
            String descriptionIdSuffix) {
        this.topCollectionSizes = topCollectionSizes;
        this.botCollectionSizes = botCollectionSizes;
        this.materiaCollection = materiaCollection;
        this.descriptionIdSuffix = descriptionIdSuffix;
    }

    public List<Integer> getTopCollectionSizes() {
        return topCollectionSizes;
    }

    public List<Integer> getBotCollectionSizes() {
        return botCollectionSizes;
    }

    public int getHeight() {
        return 36 + 9 * getEffects().size();
    }

    public ItemStack getStack(int slotIndex) {
        return materiaCollection.getStackInSlot(slotIndex);
    }

    public int getWidth() {
        return 4 + 18 * Math.max(topCollectionSizes.stream().collect(Collectors.summingInt(Integer::intValue)),
            botCollectionSizes.stream().collect(Collectors.summingInt(Integer::intValue)));
    }

    public boolean hasTopCollection() {
        return topCollectionSizes != null && !topCollectionSizes.isEmpty();
    }

    public boolean hasBotCollection() {
        return botCollectionSizes != null && !botCollectionSizes.isEmpty();
    }

    public Set<ResourceLocation> getEffects() {
        return new HashSet<>(materiaCollection.getAllEffects());
    }

    public boolean isEffectSelected(ResourceLocation effectRL) {
        return materiaCollection.getSelectedEffects().contains(effectRL);
    }

    public String getDescriptionIdSuffix() {
        return descriptionIdSuffix;
    }

}

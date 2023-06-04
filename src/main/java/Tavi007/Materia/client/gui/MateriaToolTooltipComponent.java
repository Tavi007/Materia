package Tavi007.Materia.client.gui;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import Tavi007.Materia.capabilities.materia.collection.handler.MateriaCollectionHandler;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

public class MateriaToolTooltipComponent implements TooltipComponent {

    private MateriaCollectionHandler materiaCollection;
    private List<Integer> topCollectionSizes;
    private List<Integer> botCollectionSizes;
    private String suffix;

    public MateriaToolTooltipComponent(List<Integer> topCollectionSizes, List<Integer> botCollectionSizes, MateriaCollectionHandler materiaCollection,
            String descriptionIdSuffix) {
        this.topCollectionSizes = topCollectionSizes;
        this.botCollectionSizes = botCollectionSizes;
        this.materiaCollection = materiaCollection;
        this.suffix = descriptionIdSuffix;
    }

    public List<Integer> getTopCollectionSizes() {
        return topCollectionSizes;
    }

    public List<Integer> getBotCollectionSizes() {
        return botCollectionSizes;
    }

    public MateriaCollectionHandler getMateriaCollection() {
        return materiaCollection;
    }

    public int getHeight() {
        return 36 + 9 * materiaCollection.getSelectedEffectConfigurations().size();
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

    public List<Pair<String, Integer>> getSelectedEffectDescriptions() {
        return materiaCollection.getSelectedEffectConfigurations()
            .stream()
            .map(configuration -> Pair.of(configuration.getDescriptionId(suffix), configuration.getTooltipColor()))
            .collect(Collectors.toList());
    }

    public List<Pair<String, Integer>> getEffectDescriptions(int index) {
        return materiaCollection.getEffectConfigurations(index)
            .stream()
            .map(configuration -> Pair.of(configuration.getDescriptionId(suffix), configuration.getTooltipColor()))
            .collect(Collectors.toList());
    }

}

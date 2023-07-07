package Tavi007.Materia.client.gui;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import Tavi007.Materia.capabilities.materia.collection.handler.MateriaCollectionHandler;
import Tavi007.Materia.items.IMateriaTool;
import net.minecraft.client.gui.Font;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

public class MateriaToolComponent implements TooltipComponent {

    private MateriaCollectionHandler materiaCollection;
    private List<Integer> topCollectionSizes;
    private List<Integer> botCollectionSizes;
    private String suffix;

    public MateriaToolComponent(IMateriaTool materiaTool, MateriaCollectionHandler materiaCollection) {
        this.topCollectionSizes = materiaTool.getTopCollectionSizes();
        this.botCollectionSizes = materiaTool.getBotCollectionSizes();
        this.suffix = materiaTool.getDescriptionIdSuffix();
        this.materiaCollection = materiaCollection;
    }

    public boolean hasTopCollection() {
        return topCollectionSizes != null && !topCollectionSizes.isEmpty();
    }

    public List<Integer> getTopCollectionSizes() {
        return topCollectionSizes;
    }

    public boolean hasBotCollection() {
        return botCollectionSizes != null && !botCollectionSizes.isEmpty();
    }

    public List<Integer> getBotCollectionSizes() {
        return botCollectionSizes;
    }

    public MateriaCollectionHandler getMateriaCollection() {
        return materiaCollection;
    }

    public ItemStack getStack(int slotIndex) {
        return materiaCollection.getStackInSlot(slotIndex);
    }

    // height and width of collection display
    public int getCollectionHeight() {
        return 36;
    }

    public int getCollectionWidth() {
        return 4 + 18 * Math.max(topCollectionSizes.stream().collect(Collectors.summingInt(Integer::intValue)),
            botCollectionSizes.stream().collect(Collectors.summingInt(Integer::intValue)));
    }

    // height and width and the description of effects of a specific mapping
    public List<Pair<String, Integer>> getEffectDescriptions(int index) {
        return materiaCollection.getEffectConfigurations(index)
            .stream()
            .map(configuration -> Pair.of(configuration.getDescriptionId(suffix), configuration.getTooltipColor()))
            .collect(Collectors.toList());
    }

    public int getDescriptionHeight(int effectMapperIndex) {
        return 9 * getEffectDescriptions(effectMapperIndex).size();
    }

    public int getDescriptionWidth(int effectMapperIndex, Font font) {
        int width = 0;
        for (Pair<String, Integer> pair : getEffectDescriptions(effectMapperIndex)) {
            width = Math.max(width, font.width(pair.getLeft()));
        }
        return width;
    }

    // height and width of description of currently selected effects
    public List<Pair<String, Integer>> getSelectedEffectDescriptions() {
        return getEffectDescriptions(materiaCollection.getSelectedConfigurationsIndex());
    }

    public int getSelectedDescriptionHeight() {
        return getDescriptionHeight(materiaCollection.getSelectedConfigurationsIndex());
    }

    public int getSelectedDescriptionWidth(Font font) {
        return getDescriptionWidth(materiaCollection.getSelectedConfigurationsIndex(), font);
    }

}

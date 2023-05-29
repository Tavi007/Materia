package Tavi007.Materia.client.gui;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.tooltip.TooltipComponent;

public class MateriaToolTooltipComponent implements TooltipComponent {

    Map<Integer, ResourceLocation> materiaToSlotMapping;
    List<Integer> topCollectionSizes;
    List<Integer> botCollectionSizes;

    public MateriaToolTooltipComponent(List<Integer> topCollectionSizes, List<Integer> botCollectionSizes) {
        this.topCollectionSizes = topCollectionSizes;
        this.botCollectionSizes = botCollectionSizes;
    }

    public List<Integer> getTopCollectionSizes() {
        return topCollectionSizes;
    }

    public List<Integer> getBotCollectionSizes() {
        return botCollectionSizes;
    }

    public int getHeight() {
        int height = 0;
        if (topCollectionSizes != null && !topCollectionSizes.isEmpty()) {
            height += 18;
        }
        if (botCollectionSizes != null && !botCollectionSizes.isEmpty()) {
            height += 18;
        }
        return height;
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

}

package Tavi007.Materia.items;

import java.util.List;

import Tavi007.Materia.effect.configurations.AbstractMateriaEffectConfiguration;
import Tavi007.Materia.util.MateriaToolHelper;

public interface IMateriaTool {

    public default List<List<Integer>> getTopSlotIdMappings() {
        return MateriaToolHelper.fromCollectionSizesToIdMappings(getTopCollectionSizes(), 0);
    }

    public default List<List<Integer>> getBotSlotIdMappings() {
        return MateriaToolHelper.fromCollectionSizesToIdMappings(getBotCollectionSizes(), 4);
    }

    public List<Integer> getTopCollectionSizes();

    public List<Integer> getBotCollectionSizes();

    public String getDescriptionIdSuffix();

    // Note: maybe move this logic to an event.
    // or let the configuration check if it is applicable to the tool.
    public boolean canConfigurationBeApplied(AbstractMateriaEffectConfiguration configuration);
}

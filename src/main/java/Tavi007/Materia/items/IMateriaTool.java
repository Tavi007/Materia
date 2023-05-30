package Tavi007.Materia.items;

import java.util.List;

public interface IMateriaTool {

    public List<List<Integer>> getTopSlotIdMappings();

    public List<List<Integer>> getBotSlotIdMappings();

    public List<Integer> getTopCollectionSizes();

    public List<Integer> getBotCollectionSizes();

    public String getDescriptionIdSuffix();
}

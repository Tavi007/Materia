package Tavi007.Materia.common.data.datapack;

import Tavi007.Materia.common.util.ResourceLocationHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LevelUpDataManager {

    private static Map<ResourceLocation, List<Integer>> data = Map.of();

    public static void replace(Map<ResourceLocation, List<Integer>> newData) {
        data = Map.copyOf(newData);
    }

    public static List<Integer> get(Item item) {
        return get(ResourceLocationHelper.get(item));
    }

    public static List<Integer> get(ResourceLocation id) {
        return data.getOrDefault(id, new ArrayList<>());
    }
}

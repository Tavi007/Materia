package Tavi007.Materia.common.data.datapack;

import Tavi007.Materia.common.data.pojo.MobData;
import Tavi007.Materia.common.util.ResourceLocationHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

import java.util.Map;

public class MobDataManager {

    private static Map<ResourceLocation, MobData> data = Map.of();

    public static void replace(Map<ResourceLocation, MobData> newData) {
        data = Map.copyOf(newData);
    }

    public static MobData get(LivingEntity entity) {
        return get(ResourceLocationHelper.get(entity.getType()));
    }

    public static MobData get(ResourceLocation id) {
        return data.getOrDefault(id, new MobData());
    }
}

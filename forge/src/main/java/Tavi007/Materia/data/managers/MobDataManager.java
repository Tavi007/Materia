package Tavi007.Materia.data.managers;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import Tavi007.Materia.Materia;
import Tavi007.Materia.data.pojo.MobData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = Materia.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MobDataManager extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
    private Map<ResourceLocation, MobData> mobDataRegister = ImmutableMap.of();

    public MobDataManager() {
        super(GSON, "mob_data");
    }

    public MobData getMobData(LivingEntity livingEntity) {
        return getMobData(ForgeRegistries.ENTITY_TYPES.getKey(livingEntity.getType()));
    }

    public MobData getMobData(ResourceLocation id) {
        MobData mobData = mobDataRegister.get(id);
        if (mobData != null) {
            return new MobData(mobData);
        }
        return new MobData();
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> objectIn, ResourceManager resourceManagerIn, ProfilerFiller profilerIn) {
        Builder<ResourceLocation, MobData> mobDataBuilder = ImmutableMap.builder();
        objectIn.forEach((rl, json) -> {
            try {
                Resource res = resourceManagerIn.getResourceOrThrow(getPreparedPath(rl));
                mobDataBuilder.put(rl, GSON.fromJson(json, MobData.class));
            } catch (Exception exception) {
                Materia.LOGGER.error("Couldn't parse mob data of {}", rl, exception);
            }
        });

        mobDataRegister = mobDataBuilder.build();
    }
}

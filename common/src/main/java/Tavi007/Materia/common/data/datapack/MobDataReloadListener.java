package Tavi007.Materia.common.data.datapack;

import Tavi007.Materia.common.Constants;
import Tavi007.Materia.common.data.pojo.MobData;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.Map;

public class MobDataReloadListener extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();

    public MobDataReloadListener() {
        super(GSON,"mob_data");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> files, ResourceManager resourceManager, ProfilerFiller profiler) {
        ImmutableMap.Builder<ResourceLocation, MobData> dataBuilder = ImmutableMap.builder();
        files.forEach((rl, json) -> {
            try {
                MobData data = GSON.fromJson(json, MobData.class);
                dataBuilder.put(rl, data);
            } catch (Exception exception) {
                Constants.LOGGER.error("Couldn't parse mob data of {}", rl, exception);
            }
        });

        MobDataManager.replace(dataBuilder.build());
    }
}

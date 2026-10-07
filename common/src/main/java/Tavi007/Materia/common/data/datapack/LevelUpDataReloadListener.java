package Tavi007.Materia.common.data.datapack;

import Tavi007.Materia.common.Constants;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LevelUpDataReloadListener extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();

    public LevelUpDataReloadListener() {
        super(GSON,"level_up_data");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> files, ResourceManager resourceManager, ProfilerFiller profiler) {
        ImmutableMap.Builder<ResourceLocation, List<Integer>> dataBuilder = ImmutableMap.builder();
        files.forEach((rl, json) -> {
            try {
                List<Integer> data = new ArrayList<>();
                json.getAsJsonArray().forEach(jsonElement -> data.add(jsonElement.getAsInt()));
                dataBuilder.put(rl, data);
            } catch (Exception exception) {
                Constants.LOGGER.error("Couldn't parse level up data of {}", rl, exception);
            }
        });

        LevelUpDataManager.replace(dataBuilder.build());
    }
}

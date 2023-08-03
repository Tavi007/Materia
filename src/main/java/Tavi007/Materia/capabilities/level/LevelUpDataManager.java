package Tavi007.Materia.capabilities.level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import Tavi007.Materia.Materia;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Materia.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LevelUpDataManager extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
    private Map<ResourceLocation, List<Integer>> levelUpData = ImmutableMap.of();

    public LevelUpDataManager() {
        super(GSON, "level_up_data");
    }

    public List<Integer> getLevelUpData(ResourceLocation id) {
        List<Integer> data = levelUpData.get(id);
        if (data != null) {
            return data;
        }
        return Collections.emptyList();
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> objectIn, ResourceManager resourceManagerIn, ProfilerFiller profilerIn) {
        Builder<ResourceLocation, List<Integer>> levelUpDataBuilder = ImmutableMap.builder();
        objectIn.forEach((rl, json) -> {
            try {
                Resource res = resourceManagerIn.getResourceOrThrow(getPreparedPath(rl));
                List<Integer> data = new ArrayList<>();
                json.getAsJsonArray().forEach(jsonElement -> data.add(jsonElement.getAsInt()));
                levelUpDataBuilder.put(rl, data);
            } catch (Exception exception) {
                Materia.LOGGER.error("Couldn't parse level up data of {}", rl, exception);
            }
        });

        levelUpData = levelUpDataBuilder.build();
    }
}

package Tavi007.Materia.data.managers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import Tavi007.Materia.Materia;
import Tavi007.Materia.data.pojo.effects.AbstractMateriaEffect;
import Tavi007.Materia.data.pojo.effects.configurations.AbstractMateriaEffectConfiguration;
import Tavi007.Materia.items.IMateriaTool;
import Tavi007.Materia.network.clientbound.SyncMateriaEffectConfigurationsPacket;
import Tavi007.Materia.registries.MateriaEffectConfigurationRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Materia.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MateriaEffectConfigurationManager extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
    private Map<ResourceLocation, AbstractMateriaEffectConfiguration> registeredEffectConfigurations = ImmutableMap.of();

    public MateriaEffectConfigurationManager() {
        super(GSON, "effect_configurations");
    }

    public void applySyncMessage(SyncMateriaEffectConfigurationsPacket packet) {
        this.registeredEffectConfigurations = packet.getEffectConfigurations();
    }

    public SyncMateriaEffectConfigurationsPacket getSyncPacket() {
        return new SyncMateriaEffectConfigurationsPacket(registeredEffectConfigurations);
    }

    public AbstractMateriaEffect getEffect(ResourceLocation id, IMateriaTool tool, List<ItemStack> stacks) {
        AbstractMateriaEffectConfiguration configuration = registeredEffectConfigurations.get(id);
        if (configuration != null && tool.canConfigurationBeApplied(configuration)) {
            return configuration.computeEffect(stacks);
        }
        return null;
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> objectIn, ResourceManager resourceManagerIn, ProfilerFiller profilerIn) {
        Builder<ResourceLocation, AbstractMateriaEffectConfiguration> effectMapBuilder = ImmutableMap.builder();
        Map<Class<? extends AbstractMateriaEffectConfiguration>, Integer> counterMap = new HashMap<>();

        objectIn.forEach((rl, json) -> {
            try {
                Resource res = resourceManagerIn.getResourceOrThrow(getPreparedPath(rl));

                ResourceLocation type = getType(json.getAsJsonObject());
                if (type == null) {
                    throw new Exception("Could not find 'type' property.");
                }

                // TODO remove this
                if (type.getPath().contains("spell")) {
                    System.out.println("");
                }

                Class<? extends AbstractMateriaEffectConfiguration> clazz = MateriaEffectConfigurationRegistry.get(type);
                if (clazz == null) {
                    throw new Exception("Unknown type '" + type + "'.");
                }

                AbstractMateriaEffectConfiguration configuration = GSON.fromJson(json, clazz);
                if (!configuration.isValid()) {
                    throw new Exception("Invalid configuration encountered: " + rl);
                }

                configuration.setId(rl);
                effectMapBuilder.put(rl, configuration);
                counterMap.put(clazz, counterMap.getOrDefault(clazz, 0) + 1);
            } catch (Exception exception) {
                Materia.LOGGER.error("Couldn't parse materia effect configuration {}", rl, exception);
            }
        });

        registeredEffectConfigurations = effectMapBuilder.build();
        counterMap.forEach((type, count) -> logLoading("Server", count, type.toString()));
    }

    private ResourceLocation getType(JsonObject json) {
        if (json.has("type")) {
            return new ResourceLocation(json.get("type").getAsString());
        }
        return null;
    }

    private void logLoading(String side, int size, String type) {
        Materia.LOGGER.info(side + " loaded " + size + " materia effect configuration for " + type);
    }
}

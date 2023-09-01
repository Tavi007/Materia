package Tavi007.Materia.effect.configurations;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Tavi007.Materia.Materia;
import Tavi007.Materia.data.pojo.configurations.AbstractMateriaEffectConfiguration;
import Tavi007.Materia.data.pojo.configurations.AttackConfiguration;
import Tavi007.Materia.data.pojo.configurations.MorphItemConfiguration;
import Tavi007.Materia.init.MateriaEffectConfigurationTypeList;
import Tavi007.Materia.network.clientbound.SyncMateriaEffectConfigurationsPacket;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class TestMateriaEffectConfigurationParsing {

    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
    private Map<ResourceLocation, AbstractMateriaEffectConfiguration> configurations;

    @Before
    public void before() {
        MateriaEffectConfigurationTypeList.init();
        configurations = new HashMap<>();
    }

    private ResourceLocation getResource(String path) {
        return new ResourceLocation(Materia.MOD_ID, path);
    }

    private AbstractMateriaEffectConfiguration parseFile(String path, String id, Class<? extends AbstractMateriaEffectConfiguration> clazz) throws IOException {
        Path file = Path.of("src/test/resources/data/materia/effect_configurations/", path);
        AbstractMateriaEffectConfiguration configuration = GSON.fromJson(Files.readString(file), clazz);
        configuration.setId(getResource(id));
        return configuration;
    }

    private void checkSyncMateriaEffectConfigurationsPacketEncodingAndDecoding() {
        SyncMateriaEffectConfigurationsPacket packet = new SyncMateriaEffectConfigurationsPacket(configurations);

        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        packet.encode(buf);
        SyncMateriaEffectConfigurationsPacket decodedPacket = new SyncMateriaEffectConfigurationsPacket(buf);
        Assert.assertEquals(packet.getEffectConfigurations(), decodedPacket.getEffectConfigurations());
    }

    @Test
    public void testRecipeConfiguration() throws IOException {
        configurations.put(getResource("test"), parseFile("recipe/test_recipe.json", "recipe", MorphItemConfiguration.class));
        checkSyncMateriaEffectConfigurationsPacketEncodingAndDecoding();
    }

    @Test
    public void testAttackConfiguration() throws IOException {
        configurations.put(getResource("test"), parseFile("attack/test_attack.json", "attack", AttackConfiguration.class));
        checkSyncMateriaEffectConfigurationsPacketEncodingAndDecoding();
    }
}

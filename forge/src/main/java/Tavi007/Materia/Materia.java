package Tavi007.Materia;

import Tavi007.Materia.common.Constants;
import Tavi007.Materia.init.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Tavi007.Materia.client.ClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("materia")
public class Materia {

    public static Materia INSTANCE;
    public static IEventBus MOD_EVENT_BUS;

    public Materia() {
        MOD_EVENT_BUS = FMLJavaModLoadingContext.get().getModEventBus();
        INSTANCE = this;

        ModLoadingContext.get().registerConfig(Type.CLIENT, ClientConfig.CONFIG_SPEC, Constants.MOD_ID + "-client.toml");
        ModLoadingContext.get().registerConfig(Type.SERVER, ServerConfig.CONFIG_SPEC, Constants.MOD_ID + "-server.toml");

        // register common
        BlockList.BLOCKS.register(MOD_EVENT_BUS);
        ItemList.ITEMS.register(MOD_EVENT_BUS);
        MenuList.MENU_TYPES.register(MOD_EVENT_BUS);
        EntityTypeList.ENTITY_TYPES.register(MOD_EVENT_BUS);
        MateriaEffectConfigurationTypeList.init();
        MateriaEffectList.init();
        ParticleTypeList.PARTICLES.register(MOD_EVENT_BUS);

        RecipeTypeList.RECIPE_TYPES.register(MOD_EVENT_BUS);
        RecipeTypeList.RECIPE_SERIALIZERS.register(MOD_EVENT_BUS);

        CreativeTabList.TABS.register(MOD_EVENT_BUS);
        MOD_EVENT_BUS.register(StartupCommon.class);
        MinecraftForge.EVENT_BUS.register(this);
    }
}

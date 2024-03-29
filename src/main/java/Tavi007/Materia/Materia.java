package Tavi007.Materia;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Tavi007.Materia.client.ClientConfig;
import Tavi007.Materia.init.BlockList;
import Tavi007.Materia.init.EntityTypeList;
import Tavi007.Materia.init.ItemList;
import Tavi007.Materia.init.MateriaEffectConfigurationTypeList;
import Tavi007.Materia.init.MateriaEffectList;
import Tavi007.Materia.init.MenuList;
import Tavi007.Materia.init.ParticleTypeList;
import Tavi007.Materia.init.RecipeTypeList;
import Tavi007.Materia.init.StartupCommon;
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
    public static Minecraft MINECRAFT = Minecraft.getInstance();
    public static final String MOD_ID = "materia";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static IEventBus MOD_EVENT_BUS;

    public Materia() {
        MOD_EVENT_BUS = FMLJavaModLoadingContext.get().getModEventBus();
        INSTANCE = this;

        ModLoadingContext.get().registerConfig(Type.CLIENT, ClientConfig.CONFIG_SPEC, Materia.MOD_ID + "-client.toml");
        ModLoadingContext.get().registerConfig(Type.SERVER, ServerConfig.CONFIG_SPEC, Materia.MOD_ID + "-server.toml");

        // register common
        MOD_EVENT_BUS.register(StartupCommon.class);
        ItemList.ITEMS.register(MOD_EVENT_BUS);
        MOD_EVENT_BUS.register(ItemList.class);
        BlockList.BLOCKS.register(MOD_EVENT_BUS);
        BlockList.ITEMS.register(MOD_EVENT_BUS);
        MenuList.MENU_TYPES.register(MOD_EVENT_BUS);
        EntityTypeList.ENTITY_TYPES.register(MOD_EVENT_BUS);
        MateriaEffectConfigurationTypeList.init();
        MateriaEffectList.init();
        ParticleTypeList.PARTICLES.register(MOD_EVENT_BUS);

        RecipeTypeList.RECIPE_TYPES.register(MOD_EVENT_BUS);
        RecipeTypeList.RECIPE_SERIALIZERS.register(MOD_EVENT_BUS);

        MinecraftForge.EVENT_BUS.register(this);
    }
}

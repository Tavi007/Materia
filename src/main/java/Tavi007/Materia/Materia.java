package Tavi007.Materia;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Tavi007.Materia.client.init.ScreenList;
import Tavi007.Materia.init.BlockList;
import Tavi007.Materia.init.ItemList;
import Tavi007.Materia.init.MateriaEffectList;
import Tavi007.Materia.init.StartupCommon;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("materia")
public class Materia {

    public static Materia instance;
    public static final String MOD_ID = "materia";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    @SuppressWarnings("deprecation")
    public Materia() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // register common
        ItemList.ITEMS.register(modEventBus);
        BlockList.BLOCKS.register(modEventBus);
        BlockList.ITEMS.register(modEventBus);

        // ContainerTypeList.CONTAINER_TYPES.register(modEventBus);

        modEventBus.register(StartupCommon.class);

        MateriaEffectList.init();

        // register client only
        DistExecutor.runWhenOn(Dist.CLIENT, () -> Materia::registerClientOnlyEvents);

        instance = this;
        MinecraftForge.EVENT_BUS.register(this);
    }

    private static void registerClientOnlyEvents() {
        FMLJavaModLoadingContext.get().getModEventBus().register(ScreenList.class);
        LOGGER.info("clientRegistries method registered.");
    }
}

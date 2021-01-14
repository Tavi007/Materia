package Tavi007.Materia.init;

import Tavi007.Materia.Materia;
import Tavi007.Materia.capabilities.level.LevelDataCapability;
import Tavi007.Materia.capabilities.toolslots.MateriaCollectionCapability;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class StartupCommon {
	
	@SubscribeEvent
	public static void onCommonSetup(FMLCommonSetupEvent event){
		//capabilities
		LevelDataCapability.register();
		MateriaCollectionCapability.register();
		
		Materia.LOGGER.info("setup method registered.");
	}

}

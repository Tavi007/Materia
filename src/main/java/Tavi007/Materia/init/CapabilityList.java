package Tavi007.Materia.init;

import Tavi007.Materia.Materia;
import Tavi007.Materia.capabilities.effects.EffectCapability;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = Materia.MOD_ID, bus = Bus.MOD)
public class CapabilityList {
	@SubscribeEvent
	public static void registerCapabilities(final FMLCommonSetupEvent event) {
		EffectCapability.register();
	}
}

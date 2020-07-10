package Tavi007.Materia.events;

import java.util.ArrayList;
import java.util.List;

import Tavi007.Materia.Materia;
import Tavi007.Materia.effects.MateriaEffect;
import Tavi007.Materia.effects.MateriaEffectFire;
import Tavi007.Materia.effects.MateriaEffectIce;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = Materia.MOD_ID, bus = Bus.FORGE)
public class BlockHarvestMateriaEvent {
	
	@SubscribeEvent
	public static void blockHarvestMateriaEvent(BlockEvent.HarvestDropsEvent event){
		
		ItemStack activeItem = event.getHarvester().getActiveItemStack();
		//check if activeItem is a MateriaTool
			
		//get effectList
		List<MateriaEffect> test = new ArrayList<MateriaEffect>();
		test.add(new MateriaEffectFire());
		test.add(new MateriaEffectIce());

		//apply effects
		test.forEach(effect -> {
			effect.onBlockHarvest(event);
		});

	}
}

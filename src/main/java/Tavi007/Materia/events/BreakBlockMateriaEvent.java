package Tavi007.Materia.events;

import java.util.ArrayList;

import Tavi007.Materia.Materia;
import Tavi007.Materia.effects.IMateriaEffect;
import Tavi007.Materia.effects.MateriaEffectFire;
import Tavi007.Materia.effects.MateriaEffectIce;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = Materia.MOD_ID, bus = Bus.FORGE)
public class BreakBlockMateriaEvent {
	
	@SubscribeEvent
	public static void breakBlockMateriaEvent(BlockEvent.BreakEvent event){
		
		ItemStack activeItem = event.getPlayer().getActiveItemStack();
		//check if activeItem is a MateriaTool
			
		//get effectList
		ArrayList<IMateriaEffect> test = new ArrayList<IMateriaEffect>();
		test.add(new MateriaEffectFire(null));
		test.add(new MateriaEffectIce(null));

		//apply effects
		test.forEach(effect -> {
		});

	}
}

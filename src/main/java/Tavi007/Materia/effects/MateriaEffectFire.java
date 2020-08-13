package Tavi007.Materia.effects;

import Tavi007.Materia.objects.items.MateriaItemFire;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;

public class MateriaEffectFire implements IMateriaEffect {
	
	public MateriaItemFire fireMateria;
	
	public MateriaEffectFire(MateriaItemFire fireMateria){
		this.fireMateria = fireMateria;
	}
	
	@Override
	public void onBreakBlock(BlockEvent.BreakEvent event) {
		System.out.println("Fire Event");
	}

	@Override
	public void onBlockHarvest(HarvestDropsEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRightClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAttackEntity() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProjectileImpact() {
		// TODO Auto-generated method stub
		
	}
}

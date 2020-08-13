package Tavi007.Materia.effects;

import Tavi007.Materia.objects.items.MateriaItemArea;
import Tavi007.Materia.objects.items.MateriaItemFire;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;

public class MateriaEffectFireArea implements IMateriaEffect  {

	public MateriaItemFire fireMateria;
	public MateriaItemArea areaMateria;
	
	public MateriaEffectFireArea(MateriaItemFire fireMateria, MateriaItemArea areaMateria){
		this.fireMateria = fireMateria;
		this.areaMateria = areaMateria;
	}
	@Override
	public void onBlockHarvest(HarvestDropsEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBreakBlock(BreakEvent event) {
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

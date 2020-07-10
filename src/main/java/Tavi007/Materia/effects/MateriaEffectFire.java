package Tavi007.Materia.effects;

import net.minecraftforge.event.world.BlockEvent;

public class MateriaEffectFire extends MateriaEffect{
	
	public MateriaEffectFire(){
		super();
	}
	
	public MateriaEffectFire(int materiaLevel, int areaLevel, boolean bonusEffect, boolean negateEffect){
		super(materiaLevel, areaLevel, bonusEffect, negateEffect);
	}
	
	@Override
	public void onBreakBlock(BlockEvent.BreakEvent event) {
		System.out.println("Fire Event");
	}
}

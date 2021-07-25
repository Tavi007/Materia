package Tavi007.Materia.init;


import Tavi007.Materia.effects.MateriaEffectArea;
import Tavi007.Materia.effects.MateriaEffectAreaFire;
import Tavi007.Materia.effects.MateriaEffectFire;
import Tavi007.Materia.effects.MateriaEffectIce;
import Tavi007.Materia.effects.MateriaEffectRegistry;

public class MateriaEffectList {

	public static void init() {
		MateriaEffectRegistry.register(new MateriaEffectFire());
		MateriaEffectRegistry.register(new MateriaEffectIce());
		MateriaEffectRegistry.register(new MateriaEffectArea());
		MateriaEffectRegistry.register(new MateriaEffectAreaFire());
	}
	
}

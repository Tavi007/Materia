package Tavi007.Materia.init;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

import Tavi007.Materia.effects.MateriaEffect;
import Tavi007.Materia.effects.MateriaEffectAreaFire;
import Tavi007.Materia.effects.MateriaEffectFire;
import Tavi007.Materia.effects.MateriaEffectIce;

public class MateriaEffectList {

	public static final ArrayList<Constructor<? extends MateriaEffect>> CONSTRUCTORS = new ArrayList<Constructor<? extends MateriaEffect>>();
	
	@SuppressWarnings("unchecked")
	public static void init() {
		ArrayList<Class<? extends MateriaEffect>> EFFECTS = new ArrayList<Class<? extends MateriaEffect>>();
		//single materia effect
		EFFECTS.add(MateriaEffectFire.class);
		EFFECTS.add(MateriaEffectIce.class);
		//duo materia effect
		EFFECTS.add(MateriaEffectAreaFire.class);
		//triple materia effect

		//quadruple materia effect
		

		EFFECTS.forEach(effectClass -> {
			Constructor<? extends MateriaEffect>[] constructors = (Constructor<? extends MateriaEffect>[]) effectClass.getConstructors();
			for(int i=0; i<constructors.length; i++) {
				CONSTRUCTORS.add(constructors[i]);
			}
		});
	}
	
	
}

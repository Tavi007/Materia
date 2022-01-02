package Tavi007.Materia.init;

import Tavi007.Materia.effects.Element;
import Tavi007.Materia.effects.MateriaEffectRegistry;
import Tavi007.Materia.effects.SpellEffect;
import Tavi007.Materia.effects.Stat;
import Tavi007.Materia.effects.StatUpEffect;

public class MateriaEffectList {

	public static void init() {
		MateriaEffectRegistry.register(new SpellEffect(Element.FIRE));
		MateriaEffectRegistry.register(new SpellEffect(Element.ICE));
		
		MateriaEffectRegistry.register(new StatUpEffect(Stat.MINING_SPEED));
	}
	
}

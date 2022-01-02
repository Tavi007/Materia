package Tavi007.Materia.init;

import Tavi007.Materia.effects.Element;
import Tavi007.Materia.effects.EffectRegistry;
import Tavi007.Materia.effects.SpellEffect;
import Tavi007.Materia.effects.Stat;
import Tavi007.Materia.effects.StatUpEffect;

public class MateriaEffectList {

	public static void init() {
		EffectRegistry.register(new SpellEffect(Element.FIRE));
		EffectRegistry.register(new SpellEffect(Element.ICE));
		
		EffectRegistry.register(new StatUpEffect(Stat.MINING_SPEED));
	}
	
}

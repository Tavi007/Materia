package Tavi007.Materia.effects;

import Tavi007.Materia.Materia;
import net.minecraft.util.ResourceLocation;

public class SpellEffect extends MateriaEffect {
	
	private Element element;
	
	public SpellEffect(Element element) {
		super(new ResourceLocation(Materia.MOD_ID, element.getName()));
		this.element = element;
	}

	@Override
	public String getDefaultTooltip() {
		return element.getTooltip();
	}

}

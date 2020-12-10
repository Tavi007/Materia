package Tavi007.Materia.items;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class MateriaItemIce extends MateriaItem {

	public MateriaItemIce(Properties properties) {
		super(properties);
	}

	@Override
	public ITextComponent getName() {
		return new StringTextComponent("Ice Materia");
	}
}

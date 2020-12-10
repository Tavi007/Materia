package Tavi007.Materia.items;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class MateriaItemFire extends MateriaItem {

	public MateriaItemFire(Properties properties) {
		super(properties);
	}

	@Override
	public ITextComponent getName() {
		return new StringTextComponent("Fire Materia");
	}

}

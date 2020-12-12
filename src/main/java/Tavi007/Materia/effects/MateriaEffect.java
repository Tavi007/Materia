package Tavi007.Materia.effects;

import java.util.ArrayList;

import Tavi007.Materia.items.BaseMateria;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class MateriaEffect {
	private String name = "Base";
	protected ArrayList<BaseMateria> materiaList = new ArrayList<BaseMateria>();
	
	public MateriaEffect(String name) {
		this.name = name;	
	}

	public MateriaEffect() {
	}

	public String getName() {
		return name;
	};

	public void addAP(int amount) {
		materiaList.forEach(materia ->{
			materia.addAP(amount);
		});
	}

	public ITextComponent getToolTip() {
		return new StringTextComponent("Base");
	};
}

package Tavi007.Materia.effects;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.text.ITextComponent;

public class MateriaEffect {
	private String name = "Base";
	protected ArrayList<Integer> levelList = new ArrayList<Integer>();
	
	public MateriaEffect(String name) {
		this.name = name;	
	}

	public MateriaEffect() {
	}

	public String getName() {
		return name;
	}

	public ArrayList<Integer> getMateriaList(){
		return levelList;
	}
	
	public void addPickaxeToolTip(List<ITextComponent> tooltip) {
		return;
	}
	
	public void addAxeToolTip(List<ITextComponent> tooltip) {
		return;
	}
	
	public void addShovelToolTip(List<ITextComponent> tooltip) {
		return;
	}
	
	public void addSwordToolTip(List<ITextComponent> tooltip) {
		return;
	}
	
	public void addWandToolTip(List<ITextComponent> tooltip) {
		return;
	}
}

package Tavi007.Materia.items;

import java.util.ArrayList;

import Tavi007.Materia.effects.MateriaEffect;

public interface IMateriaTool {
	ArrayList<MateriaEffect> getMateriaEffectList();
	void setMateriaEffectList(ArrayList<MateriaEffect> list);
}

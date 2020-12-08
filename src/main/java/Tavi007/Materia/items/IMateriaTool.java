package Tavi007.Materia.items;

import java.util.ArrayList;

import Tavi007.Materia.capabilities.effects.IMateriaEffect;

public interface IMateriaTool {
	ArrayList<IMateriaEffect> getMateriaEffectList();
	void setMateriaEffectList(ArrayList<IMateriaEffect> list);
}

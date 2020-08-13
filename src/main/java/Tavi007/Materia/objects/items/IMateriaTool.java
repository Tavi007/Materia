package Tavi007.Materia.objects.items;

import java.util.ArrayList;

import Tavi007.Materia.effects.IMateriaEffect;

public interface IMateriaTool {
	ArrayList<IMateriaEffect> getMateriaEffectList();
	void setMateriaEffectList(ArrayList<IMateriaEffect> list);
}

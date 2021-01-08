package Tavi007.Materia.items;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

import Tavi007.Materia.Materia;
import Tavi007.Materia.effects.MateriaEffect;
import Tavi007.Materia.init.MateriaEffectList;
import net.minecraft.item.ItemStack;

public class MateriaToolSlotCollection {

	public ArrayList<MateriaEffect> effectList = new ArrayList<MateriaEffect>();
	private final int noSlots;
	private final ItemStack[] materiaStacks; //also contains empty stacks

	public MateriaToolSlotCollection(int noSlots) {
		if (noSlots>0 && noSlots<4) {
			this.noSlots = noSlots;
			materiaStacks = new ItemStack[noSlots];
			Arrays.fill(materiaStacks, 0, noSlots, ItemStack.EMPTY);
		}
		else {
			//incorrect constructor call.
			this.noSlots = 0;
			materiaStacks = new ItemStack[noSlots];

		}
	}

	public MateriaToolSlotCollection(ItemStack[] materiaStacks) {
		noSlots = materiaStacks.length;
		this.materiaStacks = materiaStacks;
		computeEffectList();
	}

	public int getNoSlots() {
		return noSlots;
	}

	public void addAP(int amount) {
		//add AP to the ItemStacks
		for(int i=0; i<materiaStacks.length; i++) {
			if(!materiaStacks[i].isEmpty()) {
				((BaseMateria) materiaStacks[i].getItem()).addAP(amount);;
			}
		}
		//recompute EffectList
		//maybe write an updateEffectList instead, to skip some redundant effect search
		//only the levels need to be updated.
		computeEffectList();
	}

	public ArrayList<BaseMateria> getMateriaList() {
		ArrayList<BaseMateria> materiaList = new ArrayList<BaseMateria>();
		for(int i=0; i<materiaStacks.length; i++) {
			materiaList.add((BaseMateria) materiaStacks[i].getItem());
		}
		return materiaList;
	}

	public ArrayList<ItemStack> getMateriaStackList() {
		ArrayList<ItemStack> materiaStackList = new ArrayList<ItemStack>();
		for(int i=0; i<materiaStacks.length; i++) {
			materiaStackList.add(materiaStacks[i]);
		}
		return materiaStackList;
	}

	public void setMateriaStack(ItemStack stack, int index) {
		if(index>materiaStacks.length) {
			Materia.LOGGER.error("setMateriaStack: index greater than array size.");
			return;
		}
		materiaStacks[index] = stack;
		computeEffectList();
	}


	@SuppressWarnings("rawtypes")
	private void computeEffectList() {
		effectList = new ArrayList<MateriaEffect>();

		//collect equipped Materia (no empty ItemStacks) and their classes
		ArrayList<BaseMateria> equippedMateria = new ArrayList<BaseMateria>();
		for (int i=0; i<materiaStacks.length; i++) {
			if(!materiaStacks[i].isEmpty()) {
				equippedMateria.add((BaseMateria) materiaStacks[i].getItem());	
			}
		}
		Class[] materiaClasses = new Class[equippedMateria.size()];
		for (int i=0; i<equippedMateria.size(); i++) {
			materiaClasses[i] = equippedMateria.get(i).getItem().getClass();
		}

		//compare classes with every effect constructor
		boolean combinedEffectfound = useEffectConstructor(materiaClasses, equippedMateria);

		//check single Materia 
		if (!combinedEffectfound) {
			for (int i=0; i<materiaClasses.length; i++) {
				Class[] materiaClass = {materiaClasses[i]};
				ArrayList<BaseMateria> singleMateria = new ArrayList<BaseMateria>();
				singleMateria.add(equippedMateria.get(i));
				useEffectConstructor(materiaClass, singleMateria);
			}
		}
		return;
	}

	@SuppressWarnings("rawtypes")
	private boolean useEffectConstructor(Class[] materiaClasses, ArrayList<BaseMateria> equippedMateria) {
		//compare classes with every effect constructor and then use it
		for(int i=0; i<MateriaEffectList.CONSTRUCTORS.size(); i++) {
			Class[] parameterTypes = MateriaEffectList.CONSTRUCTORS.get(i).getParameterTypes();
			if(Arrays.equals(materiaClasses, parameterTypes)) {
				try {
					switch(materiaClasses.length) {
					case 1:
						effectList.add(MateriaEffectList.CONSTRUCTORS.get(i).newInstance(equippedMateria.get(0)));
						break;
					case 2:
						effectList.add(MateriaEffectList.CONSTRUCTORS.get(i).newInstance(equippedMateria.get(0), equippedMateria.get(1)));
						break;
					case 3:
						effectList.add(MateriaEffectList.CONSTRUCTORS.get(i).newInstance(equippedMateria.get(0), equippedMateria.get(1), equippedMateria.get(2)));
						break;
					case 4:
						effectList.add(MateriaEffectList.CONSTRUCTORS.get(i).newInstance(equippedMateria.get(0), equippedMateria.get(1), equippedMateria.get(2), equippedMateria.get(3)));
						break;
					}
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return true;	
			}
		}
		return false;
	}

}

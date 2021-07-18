package Tavi007.Materia.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

import Tavi007.Materia.capabilities.toolslots.MateriaCollection;
import Tavi007.Materia.effects.MateriaEffect;
import Tavi007.Materia.init.MateriaEffectList;
import Tavi007.Materia.items.IMateriaTool;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MateriaEffectHelper {

	public static void computeEffectList(ItemStack stack) {
		if (stack.getItem() instanceof IMateriaTool) {
			IMateriaTool tool = (IMateriaTool) stack.getItem();
			MateriaCollection collection = CapabilityHelper.getMateriaCollection(stack);

			//check if stacks are empty
			boolean isEmpty = true;
			for (int i=0; i<collection.getSlots(); i++) {
				if (!collection.getStackInSlot(i).isEmpty()) {
					isEmpty = false;
				}
			}
			if (isEmpty) return;

			// compute effects
			ArrayList<MateriaEffect> effectList = new ArrayList<MateriaEffect>();
			effectList.addAll(computeEffects(0, collection, tool.getTopCollectionSizes()));
			effectList.addAll(computeEffects(4, collection, tool.getBotCollectionSizes()));
			collection.effectList = effectList;
		}
	}

	private static ArrayList<MateriaEffect> computeEffects(int startStackCounter, MateriaCollection collection, int[] collectionSize) {
		ArrayList<MateriaEffect> effectList = new ArrayList<MateriaEffect>();
		
		for(int i=0; i<collectionSize.length; i++) {
			ArrayList<ItemStack> stackList = new ArrayList<ItemStack>();
			boolean hasMateria = false;
			for (int j=0; j<collectionSize[i]; j++) {
				ItemStack stack = collection.getStackInSlot(startStackCounter+j);
				stackList.add(stack);
				if (!stack.isEmpty()) {
					hasMateria = true;
				}
			}
			if(hasMateria) {
				effectList.addAll(computeEffects(stackList));
			}
			startStackCounter += collectionSize[i];
		}
		return effectList;
	}

	@SuppressWarnings("rawtypes")
	private static ArrayList<MateriaEffect> computeEffects(ArrayList<ItemStack> stacks) {
		ArrayList<MateriaEffect> effectList = new ArrayList<MateriaEffect>();
		
		ArrayList<Item> equippedItem = new ArrayList<Item>();
		Class[] materiaClasses = new Class[stacks.size()];

		// fill materiaClasses and equippedItem
		for (int i=0; i<stacks.size(); i++) {
			materiaClasses[i] = stacks.get(i).getItem().getClass();
			equippedItem.add(stacks.get(i).getItem());
		}

		boolean combinedEffectfound = useEffectConstructor(effectList, materiaClasses, equippedItem);
		//check single Materia 
		if (!combinedEffectfound) {
			for (int i=0; i<materiaClasses.length; i++) {
				Class[] materiaClass = {materiaClasses[i]};
				ArrayList<Item> singleMateria = new ArrayList<Item>();
				singleMateria.add(equippedItem.get(i));
				useEffectConstructor(effectList, materiaClass, singleMateria);
			}
		}
		return effectList;
	}

	@SuppressWarnings("rawtypes")
	private static boolean useEffectConstructor(ArrayList<MateriaEffect> effectList, Class[] materiaClasses, ArrayList<Item> equippedItem) {
		//compare classes with every effect constructor and then use it
		for(int i=0; i<MateriaEffectList.CONSTRUCTORS.size(); i++) {
			Class[] parameterTypes = MateriaEffectList.CONSTRUCTORS.get(i).getParameterTypes();
			MateriaEffect effect;
			if(Arrays.equals(materiaClasses, parameterTypes)) {
				try {
					switch(materiaClasses.length) {
					case 1:
						effect = MateriaEffectList.CONSTRUCTORS.get(i).newInstance(equippedItem.get(0));
						//update level of effect
						effectList.add(effect);
						break;
					case 2:
						effect = MateriaEffectList.CONSTRUCTORS.get(i).newInstance(equippedItem.get(0), equippedItem.get(1));
						effectList.add(effect);
						break;
					case 3:
						effect = MateriaEffectList.CONSTRUCTORS.get(i).newInstance(equippedItem.get(0), equippedItem.get(1), equippedItem.get(2));
						effectList.add(effect);
						break;
					case 4:
						effect = MateriaEffectList.CONSTRUCTORS.get(i).newInstance(equippedItem.get(0), equippedItem.get(1), equippedItem.get(2), equippedItem.get(3));
						effectList.add(effect);
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

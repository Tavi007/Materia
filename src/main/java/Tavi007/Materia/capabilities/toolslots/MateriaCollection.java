package Tavi007.Materia.capabilities.toolslots;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

import Tavi007.Materia.Materia;
import Tavi007.Materia.effects.MateriaEffect;
import Tavi007.Materia.init.MateriaEffectList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MateriaCollection {

	//hold information, that needs to be saved in nbt
	private ItemStack[] stacks = new ItemStack[8];
	
	//should be saved in nbt too or rather be recalculated everytime?
	private ArrayList<MateriaEffect> effectList = new ArrayList<MateriaEffect>();

	//these are constant. maybe move them to the item?
	private int[] topCollectionSizes = {0};
	private int[] botCollectionSizes = {0};
	
	public MateriaCollection(int[] topCollectionSizes, int[] botCollectionSizes) {
		Arrays.fill(stacks, 0, 8, ItemStack.EMPTY);
		if(isCollectionSizesValid(topCollectionSizes)) {
			this.topCollectionSizes = topCollectionSizes;
		}
		if(isCollectionSizesValid(botCollectionSizes)) {
			this.botCollectionSizes = botCollectionSizes;
		}
	}
	
	public MateriaCollection() {
		Arrays.fill(stacks, 0, 8, ItemStack.EMPTY);
	}

	//
	// Getters
	//
	
	public ItemStack[] getStacks() {
		return stacks;
	}

	public ArrayList<MateriaEffect> getEffects() {
		return effectList;
	}

	public int[] getTopCollectionSizes() {
		return topCollectionSizes;
	}
	

	public int[] getBotCollectionSizes() {
		return botCollectionSizes;
	}

	public int getNoTopSlots() {return sumIntArray(topCollectionSizes);	}
	public int getNoBotSlots() {return sumIntArray(botCollectionSizes);	}

	//
	// Setters
	//
	
	public void setMateriaStack(ItemStack stack, int index) {
		if(index>stacks.length) {
			Materia.LOGGER.error("setMateriaStack: index greater than array size.");
			return;
		}
		stacks[index] = stack;
		computeEffectList();
	}

	//
	// Various
	//
	
	private int sumIntArray(int[] array) {
		int ret = 0;
		for (int i=0; i<array.length; i++) {
			ret += array[i];
		}
		return ret;
	}
	
	private boolean isCollectionSizesValid(int[] in) {
		int counter = 0;
		for (int i=0; i<in.length; i++) {
			if(in[i]<1) {
				return false;
			}
			counter += in[i];
		}
		if (counter>4) {
			return false;
		}
		return true;
	}
	
	public void computeEffectList() {
		//check if stacks are empty
		boolean isEmpty = true;
		for (int i=0; i<stacks.length; i++) {
			if (!stacks[i].isEmpty()) {
				isEmpty = false;
			}
		}
		if (isEmpty) return;
		
		// compute effects
		effectList.clear();
		computeEffects(0, topCollectionSizes);
		computeEffects(4, botCollectionSizes);
	}
	
	private void computeEffects(int startStackCounter, int[] collectionSize) {
		for(int i=0; i<collectionSize.length; i++) {
			ArrayList<ItemStack> stackList = new ArrayList<ItemStack>();
			boolean hasMateria = false;
			for (int j=0; j<collectionSize[i]; j++) {
				stackList.add(stacks[startStackCounter+j]);
				if (!stacks[startStackCounter+j].isEmpty()) {
					hasMateria = true;
				}
			}
			if(hasMateria) {
				computeEffects(stackList);
			}
			startStackCounter += collectionSize[i];
		}
		
	}
	
	@SuppressWarnings("rawtypes")
	private void computeEffects(ArrayList<ItemStack> stacks){
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
	}

	@SuppressWarnings("rawtypes")
	private boolean useEffectConstructor(ArrayList<MateriaEffect> effectList, Class[] materiaClasses, ArrayList<Item> equippedItem) {
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

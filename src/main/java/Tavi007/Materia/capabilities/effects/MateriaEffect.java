package Tavi007.Materia.capabilities.effects;

import Tavi007.Materia.Materia;

public class MateriaEffect {
	
	// max index for the following is the number of Materia (it's usually 1. But can be 2 or 3) 
	private int[] maxLevel = {0}; // 
	private int[] abilityPower = {0};
	private int[] level = {0};
	
	// from 0 to maxLevel[0] is for first effect; 
	// maxLevel[0]+1 - maxLevel[1] is for second effect 
	private int[] nextLevelAP = {0}; 
	private String name = "Base";

	public MateriaEffect(String name, int[] maxLevel, int[] abilityPower, int[] level, int[] nextLevelAP) {
		this.name = name;
		int sum = 0;
		for (int i=0; i<maxLevel.length; i++) {
			sum += maxLevel[i];
		}
		
		//make sure, that they have the same length
		if(!(maxLevel.length == abilityPower.length && maxLevel.length == level.length && nextLevelAP.length == sum)) {
			Materia.LOGGER.info("MateriaEffect: Input doesn't have the right Dimensions.");
			return;
		}
		this.maxLevel = maxLevel;
		this.abilityPower = abilityPower;
		this.level = level;
		this.nextLevelAP = nextLevelAP;
	}

	public MateriaEffect(String name) {
		this.name = name;	
	}

	public MateriaEffect() {
	}

	public String getName() {
		return name;
	};

	public int[] getLevel() {
		return level;
	};	

	public int[] getAP() {
		return abilityPower;
	};	

	public int[] getMaxLevel() {
		return maxLevel;
	};	

	public int[] getNextLevelAP() {
		return nextLevelAP;
	};	

	public void addAP(int amount) {
		//loop over materiaEffects
		int nextLevelAP_Id = 0;
		for(int i=0; i<level.length; i++) {
			if(level[i] >= maxLevel[i]) {
				return;
			}
			
			abilityPower[i] += amount;
			int materieNextLevelAP = nextLevelAP[nextLevelAP_Id + level[i]];
			nextLevelAP_Id += maxLevel[i];
			if (abilityPower[i] > materieNextLevelAP) {
				//level up
				abilityPower[i] -= materieNextLevelAP;
				level[i] += 1; 
			}
		}
	};
}

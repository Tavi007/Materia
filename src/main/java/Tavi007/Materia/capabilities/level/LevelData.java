package Tavi007.Materia.capabilities.level;

public class LevelData {
	
	public int level;
	public int ap;
	
	public LevelData() {
		level = 0;
		ap = 0;
	}

	public LevelData(int level, int ap) {
		this.level = 0;
		this.ap = 0;
	}

	public boolean isMaxLevel(int[] apToNextLevel) {
		return level >= apToNextLevel.length; 
	}

	public void addAP(int amount, int[] apToNextLevel) {
		if(isMaxLevel(apToNextLevel)) {
			return;
		}
		
		int extraAmount = (ap+amount) - apToNextLevel[level];
		if(extraAmount >= 0) {
			level++;
			ap = 0;
			addAP(extraAmount, apToNextLevel);
		}
		else {
			ap += amount;
		}
	}
}

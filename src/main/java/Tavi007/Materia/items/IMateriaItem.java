package Tavi007.Materia.items;

import java.util.ArrayList;

public interface IMateriaItem {

	void setLevel(int level);
	int getLevel();
	
	void setAP(int ap);
	int getAP();
	void addAP(int amount);
	
	ArrayList<Integer> getLevelUpAP();
	
}

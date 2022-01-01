package Tavi007.Materia.capabilities.magic;

public class MagicData {

	private int spellIndex = 0;
	private int mana = 0;

	public MagicData() {
	}

	public int getMana() {
		return mana;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}

	public int getSpellIndex() {
		return spellIndex;
	}

	public void setSpellIndex(int index) {
		this.spellIndex = index;
	}

	public void incrementSpellIndex() {
		spellIndex++;
	}
}

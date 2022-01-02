package Tavi007.Materia.effects;

import net.minecraft.util.text.TextFormatting;

public enum Stat {
	MINING_SPEED("Mining Speed", TextFormatting.GRAY),
	MINING_LEVEL("Mining Level", TextFormatting.DARK_GRAY),
	DAMAGE("Damage", TextFormatting.DARK_RED),
	AP("AP", TextFormatting.LIGHT_PURPLE),
	EXP("EXP", TextFormatting.GREEN);

	private String tooltip;
	
	private Stat(String name, TextFormatting color) {
		this.tooltip = color + " " + name + " " + TextFormatting.RESET;
	}
	
	public String getTooltip() {
		return tooltip;
	}
	
}

package Tavi007.Materia.effects;

import org.codehaus.plexus.util.StringUtils;

import net.minecraft.util.text.TextFormatting;

public enum Element {

	FIRE("fire", TextFormatting.RED),
	ICE("ice", TextFormatting.BLUE),
	WATER("water", TextFormatting.DARK_AQUA),
	THUNDER("thunder", TextFormatting.YELLOW),
	HOLY("light", TextFormatting.WHITE),
	GRAVITAS("darkness", TextFormatting.BLACK),
	EARTH("earth", TextFormatting.GOLD),
	AERO("wind", TextFormatting.AQUA),
	BIO("flora", TextFormatting.GREEN);
	
	private String name;
	private TextFormatting color;
	
	private Element(String name, TextFormatting color) {
		this.name = name;
		this.color = color;
	}
	
	public String getName() {
		return name;
	}
	
	private String getCapitalizedName() {
		return StringUtils.capitalise(name);
	}
	
	public String getTooltip() {
		return color + " " + getCapitalizedName() + " " + TextFormatting.RESET;
	}
}

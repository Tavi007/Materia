package Tavi007.Materia.effects;

import org.codehaus.plexus.util.StringUtils;

public enum Element {

    FIRE("fire"),
    ICE("ice"),
    WATER("water"),
    THUNDER("thunder"),
    HOLY("light"),
    GRAVITAS("darkness"),
    EARTH("earth"),
    AERO("wind"),
    BIO("flora");

    private String name;

    private Element(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private String getCapitalizedName() {
        return StringUtils.capitalise(name);
    }

    public String getTooltip() {
        return getCapitalizedName();
    }
}

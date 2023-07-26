package Tavi007.Materia.effect.configurations;

public enum Stat {

    MINING_SPEED("Mining Speed"),
    MINING_LEVEL("Mining Level"),
    DAMAGE("Damage"),
    AP("AP"),
    EXP("EXP");

    private String tooltip;

    private Stat(String name) {
        this.tooltip = name;
    }

    public String getTooltip() {
        return tooltip;
    }

}

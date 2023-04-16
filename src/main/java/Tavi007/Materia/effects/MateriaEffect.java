package Tavi007.Materia.effects;

import java.util.List;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public abstract class MateriaEffect {

    private ResourceLocation rl;

    protected MateriaEffect(ResourceLocation rl) {
        this.rl = rl;
    }

    public String getName() {
        // change to get text from lang file
        return rl.toString();
    }

    public MateriaEffect initializeCopy(List<ItemStack> itemstacks) {
        return null;
    }

    public abstract String getDefaultTooltip();

    public String getPickaxeTooltip() {
        return getDefaultTooltip();
    }

    public String getAxeTooltip() {
        return getDefaultTooltip();
    }

    public String getShovelTooltip() {
        return getDefaultTooltip();
    }

    public String getHoeTooltip() {
        return getDefaultTooltip();
    }

    public String getSwordTooltip() {
        return getDefaultTooltip();
    }

    public String getWandTooltip() {
        return getDefaultTooltip();
    }

    public String getAccessoryTooltip() {
        return getDefaultTooltip();
    }
}

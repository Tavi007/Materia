package Tavi007.Materia.data.pojo.effects;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class MorphItemEffect extends AbstractMateriaEffect {

    int level;
    String recipe;

    public MorphItemEffect(ResourceLocation id, int tooltipColor, int level, String recipe) {
        super(id, tooltipColor);
        this.level = level;
        this.recipe = recipe;
    }

    public MorphItemEffect(CompoundTag tag) {
        this.deserializeNBT(tag);
    }

    public List<ItemStack> applyRecipe(List<ItemStack> stackIn) {
        List<ItemStack> stackOut = new ArrayList<ItemStack>();
        return stackOut;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = super.serializeNBT();
        tag.putInt("level", level);
        tag.putString("area", recipe);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        super.deserializeNBT(tag);
        level = tag.getInt("level");
        recipe = tag.getString("recipe");
    }
}

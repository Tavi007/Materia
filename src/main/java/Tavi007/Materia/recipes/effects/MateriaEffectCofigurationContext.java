package Tavi007.Materia.recipes.effects;

import java.util.HashSet;

import javax.annotation.Nullable;

import com.google.common.collect.Sets;
import com.google.gson.JsonParseException;

import net.minecraft.resources.ResourceLocation;

public class MateriaEffectCofigurationContext {

    public final ResourceLocation name;
    private final boolean vanilla;
    public final boolean custom;
    public int poolCount = 0;
    public int entryCount = 0;
    private HashSet<String> entryNames = Sets.newHashSet();

    public MateriaEffectCofigurationContext(ResourceLocation name, boolean custom) {
        this.name = name;
        this.custom = custom;
        this.vanilla = "minecraft".equals(this.name.getNamespace());
    }

    public String validateEntryName(@Nullable String name) {
        if (name != null && !this.entryNames.contains(name)) {
            this.entryNames.add(name);
            return name;
        }

        if (!this.vanilla)
            throw new JsonParseException("Materia effec configuration \"" + this.name.toString() + "\" Duplicate entry name \"" + name + "\" for pool #"
                + (this.poolCount - 1) + " entry #" + (this.entryCount - 1));

        int x = 0;
        while (this.entryNames.contains(name + "#" + x))
            x++;

        name = name + "#" + x;
        this.entryNames.add(name);

        return name;
    }
}

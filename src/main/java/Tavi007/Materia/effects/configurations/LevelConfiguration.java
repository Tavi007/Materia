package Tavi007.Materia.effects.configurations;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import Tavi007.Materia.util.CapabilityHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public class LevelConfiguration {

    int base = 1;
    List<Item> add = new ArrayList<>();
    List<Item> subtract = new ArrayList<>();

    // TODO make NullPointer Safe
    public LevelConfiguration(JsonObject json) {
        this.base = json.getAsJsonPrimitive("base").getAsInt();
        json.getAsJsonArray("add").asList().forEach(element -> {
            add.add(ForgeRegistries.ITEMS.getValue(new ResourceLocation(element.getAsString())));
        });
        json.getAsJsonArray("subtract").asList().forEach(element -> {
            subtract.add(ForgeRegistries.ITEMS.getValue(new ResourceLocation(element.getAsString())));
        });
    }

    public LevelConfiguration(int base, List<ResourceLocation> addRl, List<ResourceLocation> subtractRl) {
        this.base = base;
        addRl.forEach(rl -> add.add(ForgeRegistries.ITEMS.getValue(rl)));
        subtractRl.forEach(rl -> subtract.add(ForgeRegistries.ITEMS.getValue(rl)));
    }

    public int getLevel(List<ItemStack> stacks) {
        int result = base;
        for (ItemStack stack : stacks) {
            if (add.contains(stack.getItem())) {
                base += CapabilityHelper.getLevelData(stack).level;
            }
            if (subtract.contains(stack.getItem())) {
                base -= CapabilityHelper.getLevelData(stack).level;
            }
        }
        return Math.min(1, result);
    }
}

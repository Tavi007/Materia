package Tavi007.Materia.recipes.effects.configuration;

import java.util.ArrayList;
import java.util.List;

import Tavi007.Materia.util.CapabilityHelper;
import Tavi007.Materia.util.NetworkHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public class LevelConfiguration {

    int base = 1;
    List<String> add = new ArrayList<>();
    List<String> subtract = new ArrayList<>();

    private LevelConfiguration() {
    }

    public LevelConfiguration copy() {
        LevelConfiguration copy = new LevelConfiguration();
        copy.base = this.base;
        copy.add = new ArrayList<>();
        copy.add.addAll(this.add);
        copy.subtract = new ArrayList<>();
        copy.subtract.addAll(this.subtract);
        return copy;
    }

    public int getLevel(List<ItemStack> stacks) {
        int result = base;
        for (ItemStack stack : stacks) {
            if (containsItemstack(add, stack)) {
                base += CapabilityHelper.getLevelData(stack).level;
            }
            if (containsItemstack(subtract, stack)) {
                base -= CapabilityHelper.getLevelData(stack).level;
            }
        }
        return Math.min(1, result);
    }

    private boolean containsItemstack(List<String> list, ItemStack stack) {
        return list.contains(ForgeRegistries.ITEMS.getKey(stack.getItem()).toString());
    }

    public boolean isValid() {
        return add != null && subtract != null;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(base);
        NetworkHelper.writeStringList(buf, add);
        NetworkHelper.writeStringList(buf, subtract);
    }

    public LevelConfiguration(FriendlyByteBuf buf) {
        base = buf.readInt();
        add = NetworkHelper.readStringList(buf);
        subtract = NetworkHelper.readStringList(buf);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof LevelConfiguration otherConfiguration) {
            return base == otherConfiguration.base
                && ((add == null && otherConfiguration.add == null)
                    || add.equals(otherConfiguration.add))
                && ((subtract == null && otherConfiguration.subtract == null)
                    || subtract.equals(otherConfiguration.subtract));
        }
        return false;
    }
}

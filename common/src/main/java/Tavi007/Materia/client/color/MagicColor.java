package Tavi007.Materia.client.color;

import Tavi007.Materia.common.Constants;
import Tavi007.Materia.common.items.Magicite;
import Tavi007.Materia.common.items.ParaMagicPhial;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;

public class MagicColor implements ItemColor {

    private static HashMap<String, Integer> COLOR_MAP = new HashMap<>();

    public static void register(String magic, Integer color) {
        COLOR_MAP.put(magic, color);
    }

    public static int getColor(String magic) {
        return COLOR_MAP.getOrDefault(magic, 0);
    }

    static {
        register(Constants.FIRE, 0xFF0000);
        register(Constants.ICE, 0x00FFFF);
        register(Constants.LIGHTNING, 0xFFFF00);
        register(Constants.AQUA, 0x0000FF);
        register(Constants.BIO, 0x00FF00);
    }


    @Override
    public int getColor(ItemStack stack, int tintIndex) {
        if (tintIndex == 1) {
            Item item = stack.getItem();
            if(item instanceof Magicite magicite) {
                return magicite.getColor();
            } else if(item instanceof ParaMagicPhial phial) {
                return phial.getColor();
            }
        }
        return 0xFFFFFFFF;
    }
}

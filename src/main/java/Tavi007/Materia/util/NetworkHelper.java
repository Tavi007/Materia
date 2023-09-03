package Tavi007.Materia.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Tavi007.Materia.data.pojo.configurations.SpellEntityConfiguration;
import net.minecraft.network.FriendlyByteBuf;

public class NetworkHelper {

    public static void writeStringList(FriendlyByteBuf buf, List<String> list) {
        buf.writeInt(list.size());
        list.forEach(entry -> buf.writeUtf(entry));
    }

    public static List<String> readStringList(FriendlyByteBuf buf) {
        int size = buf.readInt();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(buf.readUtf());
        }
        return list;
    }

    public static void writeStringSet(FriendlyByteBuf buf, Set<String> list) {
        buf.writeInt(list.size());
        list.forEach(entry -> buf.writeUtf(entry));
    }

    public static Set<String> readStringSet(FriendlyByteBuf buf) {
        int size = buf.readInt();
        Set<String> list = new HashSet<>();
        for (int i = 0; i < size; i++) {
            list.add(buf.readUtf());
        }
        return list;
    }

    public static void writeSpellEntityConfigurationList(FriendlyByteBuf buf, List<SpellEntityConfiguration> list) {
        buf.writeInt(list.size());
        list.forEach(entry -> entry.encode(buf));
    }

    public static List<SpellEntityConfiguration> readSpellEntityConfigurationList(FriendlyByteBuf buf) {
        int size = buf.readInt();
        List<SpellEntityConfiguration> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(new SpellEntityConfiguration(buf));
        }
        return list;
    }

}

package Tavi007.Materia.util;

import java.util.ArrayList;
import java.util.List;

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

}

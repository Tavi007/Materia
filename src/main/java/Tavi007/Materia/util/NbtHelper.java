package Tavi007.Materia.util;

import java.util.List;
import java.util.stream.Collectors;

import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;

public class NbtHelper {

    public static ListTag toTagList(List<String> list) {
        ListTag listTag = new ListTag();
        list.forEach(string -> listTag.add(StringTag.valueOf(string)));
        return listTag;
    }

    public static List<String> fromStringTagList(ListTag listTag) {
        return listTag.stream()
            .map(tag -> (StringTag) tag)
            .map(tag -> tag.getAsString())
            .collect(Collectors.toList());
    }

}

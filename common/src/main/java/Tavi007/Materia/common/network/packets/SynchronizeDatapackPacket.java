package Tavi007.Materia.common.network.packets;

import Tavi007.Materia.common.data.datapack.DatapackDataAccessor;
import Tavi007.Materia.common.network.IPacketContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SynchronizeDatapackPacket extends AbstractPacket {

    public final Map<ResourceLocation, List<Integer>> levelUpData;

    public SynchronizeDatapackPacket(Map<ResourceLocation, List<Integer>> levelUpData) {
        this.levelUpData = levelUpData;
    }

    public SynchronizeDatapackPacket(FriendlyByteBuf buf) {
        int mapSize = buf.readVarInt();

        this.levelUpData = new HashMap<>(mapSize);

        for (int i = 0; i < mapSize; i++) {
            ResourceLocation resourceLocation = buf.readResourceLocation();

            int listSize = buf.readVarInt();
            List<Integer> levels = new ArrayList<>(listSize);

            for (int j = 0; j < listSize; j++) {
                levels.add(buf.readVarInt());
            }

            this.levelUpData.put(resourceLocation, levels);
        }
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeVarInt(levelUpData.size());

        for (Map.Entry<ResourceLocation, List<Integer>> entry : levelUpData.entrySet()) {
            buf.writeResourceLocation(entry.getKey());

            List<Integer> levels = entry.getValue();
            buf.writeVarInt(levels.size());

            for (int level : levels) {
                buf.writeVarInt(level);
            }
        }
    }

    @Override
    public void handle(IPacketContext context) {
        DatapackDataAccessor.applySyncMessage(this);
    }


    @Override
    public boolean isValid() {
        return levelUpData != null;
    }
}

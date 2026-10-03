package Tavi007.Materia.common.network.packets;

import Tavi007.Materia.common.data.datapack.DatapackDataAccessor;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SyncronizeDatapackPacket extends AbstractPacket {

    public final Map<ResourceLocation, List<Integer>> levelUpData;

    public SyncronizeDatapackPacket(Map<ResourceLocation, List<Integer>> levelUpData) {
        this.levelUpData = levelUpData;
    }

    public SyncronizeDatapackPacket(FriendlyByteBuf buf) {
        this.levelUpData = readLevelUpData(buf);
    }

    private static Map<ResourceLocation, List<Integer>> readLevelUpData(FriendlyByteBuf buf) {
        return new HashMap<>();
    }

    public void encode(FriendlyByteBuf buf) {
        writeLevelUpData(buf);
    }

    private void writeLevelUpData(FriendlyByteBuf buf) {
    }


    @Override
    public void processPacket(Optional<Level> level) {
        DatapackDataAccessor.applySyncMessage(this);
    }

    @Override
    public boolean isValid() {
        return levelUpData != null;
    }
}

package Tavi007.Materia.common.network.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;

import java.util.Optional;

public abstract class AbstractPacket {

    protected AbstractPacket() {
    }

    public AbstractPacket(FriendlyByteBuf buf) {
    }

    public abstract boolean isValid();


    public abstract void encode(FriendlyByteBuf buf);

    public abstract void processPacket(Optional<Level> level);


}

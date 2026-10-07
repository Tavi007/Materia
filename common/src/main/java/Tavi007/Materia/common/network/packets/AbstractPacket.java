package Tavi007.Materia.common.network.packets;

import Tavi007.Materia.common.network.IPacketContext;
import net.minecraft.network.FriendlyByteBuf;

public abstract class AbstractPacket {

    public abstract boolean isValid();

    public abstract void encode(FriendlyByteBuf buf);

    public abstract void handle(IPacketContext context);

}

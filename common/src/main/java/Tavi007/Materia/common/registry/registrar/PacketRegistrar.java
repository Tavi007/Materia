package Tavi007.Materia.common.registry.registrar;

import Tavi007.Materia.common.network.packets.AbstractPacket;
import net.minecraft.network.FriendlyByteBuf;

import java.util.function.Function;

public interface PacketRegistrar {
    <T extends AbstractPacket> void register(
            Class<T> packetClass,
            Function<FriendlyByteBuf, T> decoder
    );
}

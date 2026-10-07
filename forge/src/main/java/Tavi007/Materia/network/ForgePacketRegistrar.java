package Tavi007.Materia.network;

import Tavi007.Materia.common.init.registrar.PacketRegistrar;
import Tavi007.Materia.common.network.packets.AbstractPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Function;

public class ForgePacketRegistrar implements PacketRegistrar {
    private int packetId = 0;

    public ForgePacketRegistrar() {
    }

    @Override
    public <T extends AbstractPacket> void register(
            Class<T> packetClass,
            Function<FriendlyByteBuf, T> decoder
    ) {
        ForgePacketManager.CHANNEL.messageBuilder(packetClass, packetId++)
                .encoder(AbstractPacket::encode)
                .decoder(decoder)
                .consumerMainThread((packet, ctx) -> {
                    NetworkEvent.Context context = ctx.get();

                    context.enqueueWork(() -> {
                        packet.handle(
                                new ForgePacketContext(context)
                        );
                    });

                    context.setPacketHandled(true);
                })
                .add();
    }
}

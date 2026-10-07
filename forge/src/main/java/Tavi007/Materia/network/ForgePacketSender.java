package Tavi007.Materia.network;

import Tavi007.Materia.common.network.IPacketSender;
import Tavi007.Materia.common.network.packets.AbstractPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.PacketDistributor;

public final class ForgePacketSender implements IPacketSender {

    @Override
    public <T extends AbstractPacket>
    void sendToClient(T packet, ServerPlayer player) {
        ForgePacketManager.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> player),
                packet
        );
    }

    @Override
    public <T extends AbstractPacket>
    void sendToAllClients(T packet) {
        ForgePacketManager.CHANNEL.send(
                PacketDistributor.ALL.noArg(),
                packet
        );
    }

    @Override
    public <T extends AbstractPacket>
    void sendToServer(T packet) {
        ForgePacketManager.CHANNEL.sendToServer(packet);
    }
}

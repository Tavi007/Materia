package Tavi007.Materia.common.network;

import Tavi007.Materia.common.network.packets.AbstractPacket;
import net.minecraft.server.level.ServerPlayer;

public interface IPacketSender {

    <T extends AbstractPacket>
    void sendToServer(T packet);

    <T extends AbstractPacket>
    void sendToClient(T packet, ServerPlayer player);

    <T extends AbstractPacket>
    void sendToAllClients(T packet);
}

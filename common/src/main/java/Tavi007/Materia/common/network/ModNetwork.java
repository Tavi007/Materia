package Tavi007.Materia.common.network;

import Tavi007.Materia.common.network.packets.AbstractPacket;
import net.minecraft.server.level.ServerPlayer;

public class ModNetwork {

    private static IPacketSender sender;

    public static void init(IPacketSender sender) {
        ModNetwork.sender = sender;
    }

    public static void sendToClient(AbstractPacket packet, ServerPlayer player) {
        sender.sendToClient(packet, player);
    }

    public static void sendToAll(AbstractPacket packet) {
        sender.sendToAllClients(packet);
    }
}

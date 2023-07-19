package Tavi007.Materia.network;

import java.util.function.Function;

import Tavi007.Materia.Materia;
import Tavi007.Materia.network.clientbound.SpawnAbilityPointOrbPacket;
import Tavi007.Materia.network.clientbound.SyncMateriaEffectConfigurationsPacket;
import Tavi007.Materia.network.clientbound.SyncMateriaEffectRecipesPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketManager {

    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
        new ResourceLocation(Materia.MOD_ID, "main"),
        () -> PROTOCOL_VERSION,
        PROTOCOL_VERSION::equals,
        PROTOCOL_VERSION::equals);

    private static int NUM_PACKETS = 0;

    private PacketManager() {
    }

    public static void init() {
        register(SpawnAbilityPointOrbPacket.class, SpawnAbilityPointOrbPacket::new);
        register(SyncMateriaEffectConfigurationsPacket.class, SyncMateriaEffectConfigurationsPacket::new);
        register(SyncMateriaEffectRecipesPacket.class, SyncMateriaEffectRecipesPacket::new);

        Materia.LOGGER.info("Registered {} packets", NUM_PACKETS);
    }

    public static void sendToAllClients(Packet packet) {
        CHANNEL.send(PacketDistributor.ALL.noArg(), packet);
    }

    public static void sendToClient(Packet packet, Player player) {
        if (!player.level.isClientSide()) {
            CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), packet);
        } else {
            throw new IllegalArgumentException("Player must be a server player!");
        }
    }

    public static <MSG extends Packet> void register(Class<MSG> clazz, Function<FriendlyByteBuf, MSG> decoder) {
        CHANNEL.messageBuilder(clazz, NUM_PACKETS++)
            .encoder(Packet::encode)
            .decoder(decoder)
            .consumerMainThread((msg, ctx) -> msg.handle(ctx.get()))
            .add();
    }
}

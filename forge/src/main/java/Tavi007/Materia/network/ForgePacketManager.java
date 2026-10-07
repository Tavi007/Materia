package Tavi007.Materia.network;

import Tavi007.Materia.common.Constants;
import Tavi007.Materia.common.init.ModPackets;
import Tavi007.Materia.common.network.ModNetwork;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class ForgePacketManager {

    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Constants.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );


    public static void init() {
        ForgePacketRegistrar registrar = new ForgePacketRegistrar();
        ModPackets.register(registrar);
        ModNetwork.init(new ForgePacketSender());
    }
}

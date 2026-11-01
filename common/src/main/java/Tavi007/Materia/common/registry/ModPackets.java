package Tavi007.Materia.common.registry;

import Tavi007.Materia.common.registry.registrar.PacketRegistrar;
import Tavi007.Materia.common.network.packets.SpawnAbilityPointOrbPacket;
import Tavi007.Materia.common.network.packets.SynchronizeDatapackPacket;

public class ModPackets {

    public static void register(PacketRegistrar registrar) {
        registrar.register(
                SpawnAbilityPointOrbPacket.class,
                SpawnAbilityPointOrbPacket::new
        );

        registrar.register(
                SynchronizeDatapackPacket.class,
                SynchronizeDatapackPacket::new
        );
    }
}

package Tavi007.Materia.common.network.packets;

import Tavi007.Materia.common.entities.AbilityPointOrb;
import Tavi007.Materia.common.network.IPacketContext;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;

public class SpawnAbilityPointOrbPacket extends AbstractPacket{

    private final int id;
    private final double x;
    private final double y;
    private final double z;
    private final int value;

    public SpawnAbilityPointOrbPacket(AbilityPointOrb orb) {
        id = orb.getId();
        x = orb.getX();
        y = orb.getY();
        z = orb.getZ();
        value = orb.getValue();
    }

    public SpawnAbilityPointOrbPacket(FriendlyByteBuf buf) {
        id = buf.readInt();
        x = buf.readDouble();
        y = buf.readDouble();
        z = buf.readDouble();
        value = buf.readInt();
    }

    @Override
    public boolean isValid() {
        return id != 0 && value > 0;
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeVarInt(id);
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeShort(value);
    }

    @Override
    public void handle(IPacketContext context) {
        if (!context.isServerSide()) {
            context.level().ifPresent(level -> {
                ClientLevel clientLevel = (ClientLevel) level;
                Entity entity = new AbilityPointOrb(level, x, y, z, value);
                entity.syncPacketPositionCodec(x, y, z);
                entity.setYRot(0.0F);
                entity.setXRot(0.0F);
                entity.setId(id);
                clientLevel.putNonPlayerEntity(id, entity);
            });
        }
    }
}

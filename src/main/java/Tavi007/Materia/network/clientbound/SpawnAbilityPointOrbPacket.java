package Tavi007.Materia.network.clientbound;

import Tavi007.Materia.Materia;
import Tavi007.Materia.entities.AbilityPointOrb;
import Tavi007.Materia.network.Packet;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent.Context;

public class SpawnAbilityPointOrbPacket extends Packet {

    private int id;
    private double x;
    private double y;
    private double z;
    private int value;

    public SpawnAbilityPointOrbPacket(AbilityPointOrb orb) {
        this.id = orb.getId();
        this.x = orb.getX();
        this.y = orb.getY();
        this.z = orb.getZ();
        this.value = orb.getValue();
    }

    public SpawnAbilityPointOrbPacket(FriendlyByteBuf buf) {
        this.id = buf.readInt();
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.z = buf.readDouble();
        this.value = buf.readInt();
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeVarInt(this.id);
        buf.writeDouble(this.x);
        buf.writeDouble(this.y);
        buf.writeDouble(this.z);
        buf.writeShort(this.value);
    }

    @Override
    public void handle(Context context) {
        context.enqueueWork(() -> {
            ClientLevel level = Materia.MINECRAFT.level;
            Entity entity = new AbilityPointOrb(level, x, y, z, value);
            entity.syncPacketPositionCodec(x, y, z);
            entity.setYRot(0.0F);
            entity.setXRot(0.0F);
            entity.setId(id);
            level.putNonPlayerEntity(id, entity);
            context.setPacketHandled(true);
        });
    }

    public int getId() {
        return this.id;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public int getValue() {
        return this.value;
    }
}

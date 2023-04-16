package Tavi007.Materia.network;

import Tavi007.Materia.entity.AbilityPointOrb;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;

public class SpawnAbilityPointOrbPacket implements Packet<ClientGamePacketListener> {

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

    public void write(FriendlyByteBuf p_131526_) {
        p_131526_.writeVarInt(this.id);
        p_131526_.writeDouble(this.x);
        p_131526_.writeDouble(this.y);
        p_131526_.writeDouble(this.z);
        p_131526_.writeShort(this.value);
    }

    @Override
    public void handle(ClientGamePacketListener p_131523_) {
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

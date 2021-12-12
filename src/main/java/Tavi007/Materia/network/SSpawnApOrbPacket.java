package Tavi007.Materia.network;

import java.io.IOException;

import Tavi007.Materia.entity.ApOrbEntity;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SSpawnApOrbPacket implements IPacket<IClientPlayNetHandler> {
	   private int entityID;
	   private double posX;
	   private double posY;
	   private double posZ;
	   private int apValue;


    public SSpawnApOrbPacket(ApOrbEntity orb) {
    	this.entityID = orb.getEntityId();
      	this.posX = orb.getPosX();
      	this.posY = orb.getPosY();
      	this.posZ = orb.getPosZ();
        this.apValue = orb.getApValue();
    }


    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException {
       this.entityID = buf.readVarInt();
       this.posX = buf.readDouble();
       this.posY = buf.readDouble();
       this.posZ = buf.readDouble();
       this.apValue = buf.readShort();
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException {
       buf.writeVarInt(this.entityID);
       buf.writeDouble(this.posX);
       buf.writeDouble(this.posY);
       buf.writeDouble(this.posZ);
       buf.writeShort(this.apValue);
    }
    
	@Override
	public void processPacket(IClientPlayNetHandler handler) {
	}

    @OnlyIn(Dist.CLIENT)
    public int getEntityID() {
       return this.entityID;
    }

    @OnlyIn(Dist.CLIENT)
    public double getX() {
       return this.posX;
    }

    @OnlyIn(Dist.CLIENT)
    public double getY() {
       return this.posY;
    }

    @OnlyIn(Dist.CLIENT)
    public double getZ() {
       return this.posZ;
    }

    @OnlyIn(Dist.CLIENT)
    public int getAPValue() {
       return this.apValue;
    }
}

package Tavi007.Materia.data.pojo.effects;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.common.util.INBTSerializable;

public class SpellEntityEffect implements INBTSerializable<CompoundTag> {

    private String texture;
    private String element;
    private float damage;
    private float speed;

    public SpellEntityEffect(String texture, String element, float damage, float speed) {
        this.texture = texture;
        this.element = element;
        this.damage = damage;
        this.speed = speed;
    }

    public SpellEntityEffect(FriendlyByteBuf buf) {
        this.texture = buf.readUtf();
        this.element = buf.readUtf();
        this.damage = buf.readFloat();
        this.speed = buf.readFloat();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUtf(texture);
        buf.writeUtf(element);
        buf.writeFloat(damage);
        buf.writeFloat(speed);
    }

    public String getTexture() {
        return texture;
    }

    public String getElement() {
        return element;
    }

    public float getDamage() {
        return damage;
    }

    public float getSpeed() {
        return speed;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putString("texture", texture);
        tag.putString("element", element);
        tag.putFloat("damage", damage);
        tag.putFloat("speed", speed);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        texture = tag.getString("texture");
        element = tag.getString("element");
        damage = tag.getFloat("damage");
        speed = tag.getFloat("speed");
    }
}

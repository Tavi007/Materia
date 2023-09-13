package Tavi007.Materia.data.pojo.effects;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public class SpellEntityEffect implements INBTSerializable<CompoundTag> {

    private String texture;
    private String element;
    private float damage;

    public SpellEntityEffect(String texture, String element, float damage) {
        this.texture = texture;
        this.element = element;
        this.damage = damage;
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

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putString("texture", texture);
        tag.putString("element", element);
        tag.putFloat("damage", damage);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        texture = tag.getString("texture");
        element = tag.getString("element");
        damage = tag.getFloat("damage");
    }
}

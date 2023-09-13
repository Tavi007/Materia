package Tavi007.Materia.data.pojo.effects;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

public class AttackEffect extends AbstractMateriaEffect {

    private String element;
    private float damage;
    private AreaEffect area;

    public AttackEffect(ResourceLocation id, int tooltipColor, String element, float damage, AreaEffect area) {
        super(id, tooltipColor);
        this.element = element;
        this.damage = damage;
        this.area = area;
    }

    public AttackEffect(CompoundTag tag) {
        this.deserializeNBT(tag);
    }

    public String getElement() {
        return element;
    }

    public float getDamage() {
        return damage;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = super.serializeNBT();
        tag.putString("element", element);
        tag.putFloat("damage", damage);
        tag.put("area", area.serializeNBT());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        super.deserializeNBT(tag);
        element = tag.getString("element");
        damage = tag.getFloat("damage");
        area = new AreaEffect(0, 0, 0);
        area.deserializeNBT(tag.getCompound("area"));
    }
}

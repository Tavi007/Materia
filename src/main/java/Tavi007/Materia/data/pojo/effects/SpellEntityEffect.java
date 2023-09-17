package Tavi007.Materia.data.pojo.effects;

import java.util.List;

import Tavi007.Materia.util.NbtHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.common.util.INBTSerializable;

public class SpellEntityEffect implements INBTSerializable<CompoundTag> {

    private String texture;
    private String trailTexture;

    private String element;
    private float damage;
    private float speed;
    private boolean homing;
    private List<String> onHitCommands;
    private List<String> onLivingEntityHitCommands;
    private List<String> onBlockHitCommands;

    public SpellEntityEffect(String texture, String trailTexture, String element, float damage, float speed, boolean homing,
            List<String> onHitCommands, List<String> onLivingEntityHitCommands, List<String> onBlockHitCommands) {
        this.texture = texture;
        this.trailTexture = trailTexture;
        this.element = element;
        this.damage = damage;
        this.speed = speed;
        this.homing = homing;
        this.onHitCommands = onHitCommands;
        this.onLivingEntityHitCommands = onLivingEntityHitCommands;
        this.onBlockHitCommands = onBlockHitCommands;
    }

    public SpellEntityEffect(CompoundTag tag) {
        this.deserializeNBT(tag);
    }

    public SpellEntityEffect(FriendlyByteBuf buf) {
        this.texture = buf.readUtf();
        this.trailTexture = buf.readUtf();
        this.element = buf.readUtf();
        this.damage = buf.readFloat();
        this.speed = buf.readFloat();
        this.homing = buf.readBoolean();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUtf(texture);
        buf.writeUtf(trailTexture);
        buf.writeUtf(element);
        buf.writeFloat(damage);
        buf.writeFloat(speed);
        buf.writeBoolean(homing);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putString("texture", texture);
        tag.putString("trail_texture", trailTexture);
        tag.putString("element", element);
        tag.putFloat("damage", damage);
        tag.putFloat("speed", speed);
        tag.putBoolean("homing", homing);
        tag.put("on_hit_commands", NbtHelper.toTagList(onHitCommands));
        tag.put("on_living_entity_hit_commands", NbtHelper.toTagList(onLivingEntityHitCommands));
        tag.put("on_block_hit_commands", NbtHelper.toTagList(onBlockHitCommands));
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        texture = tag.getString("texture");
        trailTexture = tag.getString("trail_texture");
        element = tag.getString("element");
        damage = tag.getFloat("damage");
        speed = tag.getFloat("speed");
        homing = tag.getBoolean("homing");
        onHitCommands = NbtHelper.fromStringTagList(tag.getList("on_hit_commands", Tag.TAG_STRING));
        onLivingEntityHitCommands = NbtHelper.fromStringTagList(tag.getList("on_living_entity_hit_commands", Tag.TAG_STRING));
        onBlockHitCommands = NbtHelper.fromStringTagList(tag.getList("on_block_hit_commands", Tag.TAG_STRING));
    }

    public String getTexture() {
        return texture;
    }

    public String getTrailTexture() {
        return trailTexture;
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

    public boolean isHoming() {
        return homing;
    }

    public List<String> getOnHitCommands() {
        return onHitCommands;
    }

    public List<String> getOnLivingEntityHitCommands() {
        return onLivingEntityHitCommands;
    }

    public List<String> getOnBlockHitCommands() {
        return onBlockHitCommands;
    }
}

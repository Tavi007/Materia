package Tavi007.Materia.data.pojo.effects;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import Tavi007.ElementalCombat.api.BasePropertiesAPI;
import Tavi007.Materia.util.DefaultResourceLocation;
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
        buf.writeUtf(getTexture());
        buf.writeUtf(getTrailTexture());
        buf.writeUtf(getElement());
        buf.writeFloat(getDamage());
        buf.writeFloat(getSpeed());
        buf.writeBoolean(isHoming());
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putString("texture", getTexture());
        tag.putString("trail_texture", getTrailTexture());
        tag.putString("element", getElement());
        tag.putFloat("damage", getDamage());
        tag.putFloat("speed", getSpeed());
        tag.putBoolean("homing", isHoming());
        tag.put("on_hit_commands", NbtHelper.toTagList(getOnHitCommands()));
        tag.put("on_living_entity_hit_commands", NbtHelper.toTagList(getOnLivingEntityHitCommands()));
        tag.put("on_block_hit_commands", NbtHelper.toTagList(getOnBlockHitCommands()));
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
        return Optional.ofNullable(texture).orElse(DefaultResourceLocation.SPELL_TEXTURE.toString());
    }

    public String getTrailTexture() {
        return Optional.ofNullable(trailTexture).orElse(DefaultResourceLocation.SPELL_TRAIL_TEXTURE.toString());
    }

    public String getElement() {
        return Optional.ofNullable(element).orElse(BasePropertiesAPI.getDefaultAttackElement());
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
        return Optional.ofNullable(onHitCommands).orElse(Collections.emptyList());
    }

    public List<String> getOnLivingEntityHitCommands() {
        return Optional.ofNullable(onLivingEntityHitCommands).orElse(Collections.emptyList());
    }

    public List<String> getOnBlockHitCommands() {
        return Optional.ofNullable(onBlockHitCommands).orElse(Collections.emptyList());
    }
}

package Tavi007.Materia.data.pojo.effects;

import java.util.List;
import java.util.stream.Collectors;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;

public class SpellEffect extends AbstractMateriaEffect {

    private String messageId;
    private List<SpellEntityEffect> spells;

    public SpellEffect(ResourceLocation id, int tooltipColor, String messageId, List<SpellEntityEffect> spells) {
        super(id, tooltipColor);
        this.messageId = messageId;
        this.spells = spells;
    }

    public SpellEffect(CompoundTag tag) {
        this.deserializeNBT(tag);
    }

    public String getMessageId() {
        return messageId;
    }

    public List<SpellEntityEffect> getSpellEntityEffects() {
        return spells;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = super.serializeNBT();
        tag.putString("message_id", messageId);
        ListTag list = new ListTag();
        list.addAll(spells.stream().map(SpellEntityEffect::serializeNBT).collect(Collectors.toList()));
        tag.put("spells", list);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        super.deserializeNBT(tag);
        messageId = tag.getString("message_id");
        ListTag list = tag.getList("spells", CompoundTag.TAG_COMPOUND);
        spells = list.stream()
            .map(entry -> (CompoundTag) entry)
            .map(entry -> {
                SpellEntityEffect effect = new SpellEntityEffect(null, null, 0);
                effect.deserializeNBT(entry);
                return effect;
            })
            .collect(Collectors.toList());
    }

}

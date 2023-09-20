package Tavi007.Materia.particles;

import java.util.Optional;

import com.mojang.brigadier.StringReader;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import Tavi007.Materia.util.DefaultResourceLocation;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class SpellEntityTrailParticleOption extends ParticleType<SpellEntityTrailParticleOption> implements ParticleOptions {

    private ResourceLocation texture;
    private final ParticleType<SpellEntityTrailParticleOption> type;

    private static final ParticleOptions.Deserializer<SpellEntityTrailParticleOption> DESERIALIZER = new ParticleOptions.Deserializer<SpellEntityTrailParticleOption>() {

        public SpellEntityTrailParticleOption fromCommand(ParticleType<SpellEntityTrailParticleOption> particleType, StringReader reader) {
            return (SpellEntityTrailParticleOption) particleType;
        }

        public SpellEntityTrailParticleOption fromNetwork(ParticleType<SpellEntityTrailParticleOption> particleType, FriendlyByteBuf buf) {
            return (SpellEntityTrailParticleOption) particleType;
        }
    };

    public SpellEntityTrailParticleOption() {
        super(false, DESERIALIZER);
        this.type = this;
        this.texture = null;
    }

    public SpellEntityTrailParticleOption(ParticleType<SpellEntityTrailParticleOption> type, ResourceLocation texture) {
        super(false, DESERIALIZER);
        this.type = type;
        this.texture = texture;
    }

    public SpellEntityTrailParticleOption(String texture) {
        this(new ResourceLocation(texture));
    }

    public SpellEntityTrailParticleOption(ResourceLocation texture) {
        super(false, DESERIALIZER);
        this.texture = texture;
        this.type = this;
    }

    @Override
    public ParticleType<SpellEntityTrailParticleOption> getType() {
        return type;
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf buf) {
        buf.writeResourceLocation(getTexture());
    }

    @Override
    public String writeToString() {
        return ForgeRegistries.PARTICLE_TYPES.getKey(this.getType()) + " [texture:" + getTexture() + "]";
    }

    @Override
    public Codec<SpellEntityTrailParticleOption> codec() {
        return null;
    }

    public static final Codec<SpellEntityTrailParticleOption> CODEC = RecordCodecBuilder.create((particleOptionInstance) -> {
        return particleOptionInstance
            .group(Codec.STRING
                .fieldOf("texture")
                .forGetter((particleOption) -> {
                    return particleOption.getTexture().toString();
                }))
            .apply(particleOptionInstance, SpellEntityTrailParticleOption::new);
    });

    public ResourceLocation getTexture() {
        return Optional.ofNullable(texture).orElse(DefaultResourceLocation.SPELL_TRAIL_TEXTURE);
    }

}

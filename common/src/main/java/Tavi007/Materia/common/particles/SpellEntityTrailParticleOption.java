package Tavi007.Materia.common.particles;

import java.util.Optional;

import Tavi007.Materia.common.Constants;
import Tavi007.Materia.common.init.ModParticles;
import com.mojang.brigadier.StringReader;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class SpellEntityTrailParticleOption implements ParticleOptions {

    public static final ParticleOptions.Deserializer<SpellEntityTrailParticleOption> DESERIALIZER = new ParticleOptions.Deserializer<SpellEntityTrailParticleOption>() {

        public SpellEntityTrailParticleOption fromCommand(ParticleType<SpellEntityTrailParticleOption> particleType, StringReader reader) {
            return new SpellEntityTrailParticleOption();
        }

        public SpellEntityTrailParticleOption fromNetwork(ParticleType<SpellEntityTrailParticleOption> particleType, FriendlyByteBuf buf) {
            return new SpellEntityTrailParticleOption();
        }
    };

    public static final Codec<SpellEntityTrailParticleOption> CODEC = RecordCodecBuilder.create((particleOptionInstance) -> {
        return particleOptionInstance
            .group(Codec.STRING
                .fieldOf("texture")
                .forGetter((particleOption) -> {
                    return particleOption.getTexture().toString();
                }))
            .apply(particleOptionInstance, SpellEntityTrailParticleOption::new);
    });

    private ResourceLocation texture;
    private final ParticleType<SpellEntityTrailParticleOption> type;

    public SpellEntityTrailParticleOption() {
        this.type = ModParticles.SPELL_TRAIL.get();
        this.texture = Constants.SPELL_TRAIL_TEXTURE;
    }

    public SpellEntityTrailParticleOption(ResourceLocation texture) {
        this.type = ModParticles.SPELL_TRAIL.get();
        this.texture = texture;
    }

    public SpellEntityTrailParticleOption(String texture) {
        this(new ResourceLocation(texture));
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
        return " [texture:" + getTexture() + "]";
    }

    public ResourceLocation getTexture() {
        return Optional.ofNullable(texture).orElse(Constants.SPELL_TRAIL_TEXTURE);
    }

}

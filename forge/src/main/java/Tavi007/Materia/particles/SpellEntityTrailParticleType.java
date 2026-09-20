package Tavi007.Materia.particles;

import com.mojang.serialization.Codec;

import net.minecraft.core.particles.ParticleType;

public class SpellEntityTrailParticleType extends ParticleType<SpellEntityTrailParticleOption> {

    private final ParticleType<SpellEntityTrailParticleOption> type;

    public SpellEntityTrailParticleType() {
        super(false, SpellEntityTrailParticleOption.DESERIALIZER);
        this.type = this;
    }

    public SpellEntityTrailParticleType(ParticleType<SpellEntityTrailParticleOption> type) {
        super(false, SpellEntityTrailParticleOption.DESERIALIZER);
        this.type = type;
    }

    @Override
    public Codec<SpellEntityTrailParticleOption> codec() {
        return SpellEntityTrailParticleOption.CODEC;
    }

}

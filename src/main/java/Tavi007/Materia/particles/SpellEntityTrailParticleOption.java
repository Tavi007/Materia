package Tavi007.Materia.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.serialization.Codec;

import Tavi007.Materia.Materia;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class SpellEntityTrailParticleOption extends ParticleType<SpellEntityTrailParticleOption> implements ParticleOptions {

    private ResourceLocation texture;
    private final ParticleType<SpellEntityTrailParticleOption> type;

    private static final ParticleOptions.Deserializer<SpellEntityTrailParticleOption> DESERIALIZER = new ParticleOptions.Deserializer<SpellEntityTrailParticleOption>() {

        public SpellEntityTrailParticleOption fromCommand(ParticleType<SpellEntityTrailParticleOption> p_123846_, StringReader p_123847_) {
            return (SpellEntityTrailParticleOption) p_123846_;
        }

        public SpellEntityTrailParticleOption fromNetwork(ParticleType<SpellEntityTrailParticleOption> p_123849_, FriendlyByteBuf p_123850_) {
            return (SpellEntityTrailParticleOption) p_123849_;
        }
    };

    public SpellEntityTrailParticleOption() {
        super(false, DESERIALIZER);
        this.type = this;
        this.texture = new ResourceLocation(Materia.MOD_ID, "test");
    }

    public SpellEntityTrailParticleOption(ParticleType<SpellEntityTrailParticleOption> type, ResourceLocation texture) {
        super(false, DESERIALIZER);
        this.type = type;
        this.texture = texture;
    }

    @Override
    public ParticleType<SpellEntityTrailParticleOption> getType() {
        return type;
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf buf) {
        buf.writeResourceLocation(texture);
    }

    public String writeToString() {
        return ForgeRegistries.PARTICLE_TYPES.getKey(this.getType()) + " [texture:" + texture + "]";
    }

    @Override
    public Codec<SpellEntityTrailParticleOption> codec() {
        return null;
    }

}

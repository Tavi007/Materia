package Tavi007.Materia.init;

import Tavi007.Materia.Materia;
import Tavi007.Materia.particles.SpellEntityTrailParticleOption;
import Tavi007.Materia.particles.SpellEntityTrailParticleType;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ParticleTypeList {

    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Materia.MOD_ID);

    public static final RegistryObject<ParticleType<SpellEntityTrailParticleOption>> SPELL_TRAIL = PARTICLES.register("spell_trail",
        () -> new SpellEntityTrailParticleType());
}

package Tavi007.Materia.init;

import Tavi007.Materia.common.Constants;
import Tavi007.Materia.common.init.ModParticles;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ParticleTypeList {

    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Constants.MOD_ID);

    static {
        ModParticles.PARTICLE_MAP.forEach(PARTICLES::register);
    }
}

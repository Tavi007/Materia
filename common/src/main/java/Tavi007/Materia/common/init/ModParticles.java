package Tavi007.Materia.common.init;

import Tavi007.Materia.common.Constants;
import Tavi007.Materia.common.particles.SpellEntityTrailParticleOption;
import Tavi007.Materia.common.particles.SpellEntityTrailParticleType;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import net.minecraft.core.particles.ParticleType;

import java.util.HashMap;

public class ModParticles {
    public static final HashMap<String, Supplier<? extends ParticleType<?>>> PARTICLE_MAP = new HashMap<>();

    public static final Supplier<ParticleType<SpellEntityTrailParticleOption>> SPELL_TRAIL = Suppliers.memoize(SpellEntityTrailParticleType::new);

    static {
        PARTICLE_MAP.put(Constants.SPELL_PROJECTILE, SPELL_TRAIL);
    }
}

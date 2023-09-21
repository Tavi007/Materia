package Tavi007.Materia.client.particles;

import Tavi007.Materia.particles.SpellEntityTrailParticleOption;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class SpellEntityTrailParticle extends TextureSheetParticle {

    // TODO either use particleOption here or remove it from constructor
    protected SpellEntityTrailParticle(ClientLevel level, double x, double y, double z, TextureAtlasSprite sprite,
            SpellEntityTrailParticleOption particleOption) {
        super(level, x, y, z);
        setSprite(sprite);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }
}

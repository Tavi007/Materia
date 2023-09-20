package Tavi007.Materia.client.particles;

import javax.annotation.Nullable;

import Tavi007.Materia.Materia;
import Tavi007.Materia.particles.SpellEntityTrailParticleOption;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

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

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SpellEntityTrailParticleOption> {

        public Provider() {
        }

        @Nullable
        public Particle createParticle(SpellEntityTrailParticleOption particleOption, ClientLevel level, double x, double y, double z,
                double p_106641_, double p_106642_, double p_106643_) {
            ResourceLocation textureLocation = particleOption.getTexture();
            if (textureLocation == null) {
                return null;
            }
            TextureAtlasSprite sprite = Materia.MINECRAFT.getTextureAtlas(TextureAtlas.LOCATION_PARTICLES).apply(textureLocation);
            if (sprite == null) {
                return null;
            }

            return new SpellEntityTrailParticle(level, x, y, z, sprite, particleOption);

        }
    }

}

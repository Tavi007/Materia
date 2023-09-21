package Tavi007.Materia.client.particles;

import java.util.function.Function;

import Tavi007.Materia.Materia;
import Tavi007.Materia.particles.SpellEntityTrailParticleOption;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;

public class SpellEntityTrailParticleProvider implements ParticleProvider<SpellEntityTrailParticleOption> {

    public SpellEntityTrailParticleProvider() {
    }

    @Override
    public Particle createParticle(SpellEntityTrailParticleOption particleOption, ClientLevel level, double xPos, double yPos, double zPos,
            double xVel, double yVel, double zVel) {

        ResourceLocation textureLocation = particleOption.getTexture();
        if (textureLocation == null) {
            return null;
        }

        AbstractTexture test1 = Materia.MINECRAFT.getTextureManager().getTexture(textureLocation);
        Function<ResourceLocation, TextureAtlasSprite> test = Materia.MINECRAFT.getTextureAtlas(TextureAtlas.LOCATION_BLOCKS);
        TextureAtlasSprite sprite = test.apply(textureLocation);
        if (sprite == null) {
            return null;
        }

        return new SpellEntityTrailParticle(level, xPos, yPos, zPos, sprite, particleOption);
    }

}

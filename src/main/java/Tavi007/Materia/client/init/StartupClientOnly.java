package Tavi007.Materia.client.init;

import Tavi007.Materia.Materia;
import Tavi007.Materia.client.gui.MateriaToolClientComponent;
import Tavi007.Materia.client.gui.MateriaToolComponent;
import Tavi007.Materia.client.particles.SpellEntityTrailParticleProvider;
import Tavi007.Materia.client.renderer.entity.AbilityPointOrbRenderer;
import Tavi007.Materia.client.renderer.entity.SpellProjectileRenderer;
import Tavi007.Materia.init.EntityTypeList;
import Tavi007.Materia.init.ParticleTypeList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Materia.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class StartupClientOnly {

    @SubscribeEvent
    public static void onRegisterClientTooltipComponentFactoriesEvent(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(MateriaToolComponent.class, MateriaToolClientComponent::new);
        Materia.LOGGER.info("Tooltip component factories registered.");
    }

    @SubscribeEvent
    public static void onRegisterRenderersEvent(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityTypeList.ABILITY_POINT_ORB.get(), AbilityPointOrbRenderer::new);
        event.registerEntityRenderer(EntityTypeList.SPELL_PROJECTILE.get(), SpellProjectileRenderer::new);
        Materia.LOGGER.info("Entity renderers registered.");
    }

    @SubscribeEvent
    public static void onRegisterRenderersEvent(RegisterParticleProvidersEvent event) {
        event.register(ParticleTypeList.SPELL_TRAIL.get(), sprite -> new SpellEntityTrailParticleProvider());
        Materia.LOGGER.info("Particle provider registered.");
    }
}

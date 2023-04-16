package Tavi007.Materia.capabilities.magic;

import Tavi007.Materia.Materia;
import Tavi007.Materia.capabilities.SerializableCapabilityProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class MagicDataCapability {

    public static final Capability<MagicData> CAPABILITY = null;

    /**
     * The default {@link Direction} to use for this capability.
     */
    public static final Direction defaultFacing = null;

    /**
     * The ID of this capability.
     */
    public static final ResourceLocation ID = new ResourceLocation(Materia.MOD_ID, "magic");

    public static void register(final RegisterCapabilitiesEvent event) {
        event.register(MagicData.class);
    }

    public static ICapabilityProvider createProvider(final MagicData MagicData) {
        return new SerializableCapabilityProvider<>(CAPABILITY, defaultFacing, MagicData);
    }

    @Mod.EventBusSubscriber(modid = Materia.MOD_ID)
    private static class EventHandler {

        @SubscribeEvent
        public static void attachCapabilitiesItem(final AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof Player) {
                final MagicData collection = new MagicData();
                event.addCapability(ID, createProvider(collection));
            }
        }
    }
}

package Tavi007.Materia.capabilities.level;

import Tavi007.Materia.Materia;
import Tavi007.Materia.capabilities.SerializableCapabilityProvider;
import Tavi007.Materia.init.ReloadListenerList;
import Tavi007.Materia.items.MateriaItem;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class LevelDataCapability {

    public static final Capability<LevelData> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });

    /**
     * The default {@link Direction} to use for this capability.
     */
    public static final Direction defaultFacing = null;

    /**
     * The ID of this capability.
     */
    public static final ResourceLocation ID = new ResourceLocation(Materia.MOD_ID, "level_data");

    public static void register(final RegisterCapabilitiesEvent event) {
        event.register(LevelData.class);
    }

    public static ICapabilityProvider createProvider(final LevelData levelData) {
        return new SerializableCapabilityProvider<>(CAPABILITY, defaultFacing, levelData);
    }

    @Mod.EventBusSubscriber(modid = Materia.MOD_ID)
    private static class EventHandler {

        @SubscribeEvent
        public static void attachCapabilitiesItem(final AttachCapabilitiesEvent<ItemStack> event) {
            Item item = event.getObject().getItem();
            if (item instanceof MateriaItem) {
                final LevelData data = new LevelData(ReloadListenerList.LEVEL_UP_DATA_MANAGER.getLevelUpData(item));
                event.addCapability(ID, createProvider(data));
            }
        }
    }
}

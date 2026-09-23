package Tavi007.Materia.capabilities;

import Tavi007.Materia.common.Constants;
import Tavi007.Materia.common.data.capabilities.MateriaLevelData;
import Tavi007.Materia.init.ReloadListenerList;
import Tavi007.Materia.common.items.MateriaItem;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.LevelData;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

public class MateriaLevelDataCapability {

    public static final Capability<MateriaLevelDataSerializer> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });

    /**
     * The default {@link Direction} to use for this capability.
     */
    public static final Direction defaultFacing = null;

    /**
     * The ID of this capability.
     */
    public static final ResourceLocation ID = new ResourceLocation(Constants.MOD_ID, "level_data");

    public static void register(final RegisterCapabilitiesEvent event) {
        event.register(LevelData.class);
    }

    public static ICapabilityProvider createProvider(final MateriaLevelData data) {
        return new SerializableCapabilityProvider<>(CAPABILITY, defaultFacing, new MateriaLevelDataSerializer(data));
    }

    @Mod.EventBusSubscriber(modid = Constants.MOD_ID)
    private static class EventHandler {

        @SubscribeEvent
        public static void attachCapabilitiesItem(final AttachCapabilitiesEvent<ItemStack> event) {
            Item item = event.getObject().getItem();
            if (item instanceof MateriaItem) {
                final MateriaLevelData data = new MateriaLevelData(ReloadListenerList.LEVEL_UP_DATA_MANAGER.getLevelUpData(item), 0 ,0);
                event.addCapability(ID, createProvider(data));
            }
        }
    }
}

package Tavi007.Materia.network.clientbound;

import java.util.HashMap;
import java.util.Map;

import Tavi007.Materia.Materia;
import Tavi007.Materia.data.pojo.effects.configurations.AbstractMateriaEffectConfiguration;
import Tavi007.Materia.init.ReloadListenerList;
import Tavi007.Materia.network.Packet;
import Tavi007.Materia.registries.MateriaEffectConfigurationRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent.Context;

public class SyncMateriaEffectConfigurationsPacket extends Packet {

    private Map<ResourceLocation, AbstractMateriaEffectConfiguration> registeredEffectConfigurations;

    public SyncMateriaEffectConfigurationsPacket(Map<ResourceLocation, AbstractMateriaEffectConfiguration> registeredEffectConfigurations) {
        this.registeredEffectConfigurations = registeredEffectConfigurations;
    }

    public SyncMateriaEffectConfigurationsPacket(FriendlyByteBuf buf) {
        this.registeredEffectConfigurations = new HashMap<>();
        int size = buf.readInt();
        for (int i = 0; i < size; i++) {
            ResourceLocation rl = buf.readResourceLocation();
            try {
                ResourceLocation effectType = buf.readResourceLocation();
                AbstractMateriaEffectConfiguration configuration = MateriaEffectConfigurationRegistry.get(effectType)
                    .getConstructor(FriendlyByteBuf.class)
                    .newInstance(buf);
                registeredEffectConfigurations.put(rl, configuration);
            } catch (Exception exception) {
                Materia.LOGGER.error("could not handle bytes after {}", rl, exception);
            }
        }
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(registeredEffectConfigurations.size());
        registeredEffectConfigurations.forEach((rl, configuration) -> {
            buf.writeResourceLocation(rl);
            buf.writeResourceLocation(MateriaEffectConfigurationRegistry.get(configuration.getClass()));
            configuration.encode(buf);
        });
    }

    @Override
    public void handle(Context context) {
        context.enqueueWork(() -> {
            if (!isValid()) {
                return;
            }
            ReloadListenerList.MATERIA_EFFECT_CONFIGURATION_MANGER.applySyncMessage(this);
            context.setPacketHandled(true);
        });
    }

    private boolean isValid() {
        return registeredEffectConfigurations != null;
    }

    public Map<ResourceLocation, AbstractMateriaEffectConfiguration> getEffectConfigurations() {
        return registeredEffectConfigurations;
    }

}

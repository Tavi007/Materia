package Tavi007.Materia.network.clientbound;

import java.util.Map;

import Tavi007.Materia.init.ReloadListenerList;
import Tavi007.Materia.network.Packet;
import Tavi007.Materia.recipes.effects.configuration.AbstractMateriaEffectConfiguration;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent.Context;

public class SyncMateriaEffectConfigurationsPacket extends Packet {

    private Map<ResourceLocation, AbstractMateriaEffectConfiguration> registeredEffectConfigurations;

    public SyncMateriaEffectConfigurationsPacket(Map<ResourceLocation, AbstractMateriaEffectConfiguration> registeredEffectConfigurations) {
        this.registeredEffectConfigurations = registeredEffectConfigurations;
    }

    public SyncMateriaEffectConfigurationsPacket(FriendlyByteBuf buf) {

    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        // TODO Auto-generated method stub

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

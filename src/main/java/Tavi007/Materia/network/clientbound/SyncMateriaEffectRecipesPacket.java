package Tavi007.Materia.network.clientbound;

import java.util.Map;

import Tavi007.Materia.init.ReloadListenerList;
import Tavi007.Materia.network.Packet;
import Tavi007.Materia.recipes.effects.MateriaEffectRecipePojo;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent.Context;

public class SyncMateriaEffectRecipesPacket extends Packet {

    private Map<ResourceLocation, MateriaEffectRecipePojo> registeredEffectRecipes;

    public SyncMateriaEffectRecipesPacket(Map<ResourceLocation, MateriaEffectRecipePojo> registeredEffectRecipes) {
        this.registeredEffectRecipes = registeredEffectRecipes;
    }

    public SyncMateriaEffectRecipesPacket(FriendlyByteBuf buf) {

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
            ReloadListenerList.MATERIA_EFFECT_RECIPE_MANGER.applySyncPacket(this);
            context.setPacketHandled(true);
        });
    }

    private boolean isValid() {
        return registeredEffectRecipes != null;
    }

    public Map<ResourceLocation, MateriaEffectRecipePojo> getEffectRecipes() {
        return registeredEffectRecipes;
    }

}

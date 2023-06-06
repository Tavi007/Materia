package Tavi007.Materia.client;

import java.util.List;

import com.mojang.datafixers.util.Either;

import Tavi007.Materia.Materia;
import Tavi007.Materia.capabilities.materia.collection.handler.MateriaCollectionHandler;
import Tavi007.Materia.client.gui.MateriaToolComponent;
import Tavi007.Materia.client.gui.SelectMateriaEffectScreen;
import Tavi007.Materia.items.IMateriaTool;
import Tavi007.Materia.util.CapabilityHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = Materia.MOD_ID, bus = Bus.FORGE)
public class RenderEvents {

    private static Minecraft MINECRAFT = Minecraft.getInstance();

    @SubscribeEvent
    public static void onGatherTooltip(RenderTooltipEvent.GatherComponents event) {
        List<Either<FormattedText, TooltipComponent>> tooltip = event.getTooltipElements();
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();
        if (item instanceof IMateriaTool tool) {
            MateriaCollectionHandler materiaCollection = CapabilityHelper.getMateriaCollectionHandler(stack);
            tooltip.add(Either.right(new MateriaToolComponent(tool, materiaCollection)));
        }
    }

    @SubscribeEvent
    public static void overlayEvent(RenderGuiOverlayEvent.Pre event) {
        if (MINECRAFT.screen instanceof SelectMateriaEffectScreen && event.getOverlay() == VanillaGuiOverlay.CROSSHAIR.type()) {
            event.setCanceled(true);
        }
    }
}

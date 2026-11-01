package Tavi007.Materia.common.inventory.slots;

import Tavi007.Materia.common.items.ParaMagicPhial;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;

public class ParaMagicExtractorResultSlot extends ResultSlot {

    public ParaMagicExtractorResultSlot(
            Player player,
            Container container,
            int slot,
            int x,
            int y
    ) {
        super(player, container, slot, x, y);
    }

    protected void checkTakeAchievements(ItemStack stack) {
        stack.onCraftedBy(this.player.level(), this.player, this.removeCount);
        if (this.player instanceof ServerPlayer serverPlayer) {
            // give achievement here
        }

        this.removeCount = 0;
    }
}

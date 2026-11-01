package Tavi007.Materia.common.blocks;


import Tavi007.Materia.common.blocks.entities.MateriaIncubatorBlockEntity;
import Tavi007.Materia.common.inventory.menus.MateriaIncubatorMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

// if an item contains para magic, it can be extracted and bottled into a phial
// input: item, empty bottles (amount depends on tier)
// outputs: chance of filled phials (chance depends on item, controlled by recipe data)
public class ParaMagicExtractorBlock extends BaseEntityBlock {

    private static final Component MENU_TITLE = Component.translatable("menu.para_magic_extractor");


    public ParaMagicExtractorBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hitResult) {
        if (worldIn.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            player.openMenu(state.getMenuProvider(worldIn, pos));
            return InteractionResult.CONSUME;
        }
    }

    @Override
    public MenuProvider getMenuProvider(BlockState blockState, Level level, BlockPos blockPos) {
        return new SimpleMenuProvider((windowId, playerInventory, playerEntity) -> new MateriaIncubatorMenu(windowId, playerInventory, null), MENU_TITLE);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MateriaIncubatorBlockEntity(pos, state);
    }
}

package Tavi007.Materia.blocks;

import net.minecraft.world.level.block.Block;

public class EquippingStationBlock extends Block {

    // private static final ITextComponent textComponent = new TranslationTextComponent("container.equipping_station");

    public EquippingStationBlock(Properties properties) {
        super(properties);
    }

    // @Override
    // public InteractionResultHolder<Block> use(BlockState state, Level worldIn, BlockPos pos, Player player, Hand handIn, BlockRayTraceResult hit) {
    // if (worldIn.isRemote) {
    // return ActionResultType.SUCCESS;
    // } else {
    // player.openContainer(state.getContainer(worldIn, pos));
    // return ActionResultType.CONSUME;
    // }
    // }

    // public INamedContainerProvider getContainer(BlockState state, Level worldIn, BlockPos pos) {
    // return new SimpleNamedContainerProvider((windowId, playerInventory, playerEntity) -> {
    // return new EquippingStationContainer(windowId, playerInventory, worldIn, pos);
    // }, textComponent);
    // }

}

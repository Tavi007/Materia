package Tavi007.Materia.blocks;

import net.minecraft.world.level.block.Block;

public class MateriaIncubatorBlock extends Block {

    // private static final ITextComponent textComponent = new TranslationTextComponent("container.materia_incubator");

    public MateriaIncubatorBlock(Properties properties) {
        super(properties);
    }

    // public ActionResultType onBlockActivated(BlockState state, Level worldIn, BlockPos pos, Player player, Hand handIn, BlockRayTraceResult hit) {
    // if (worldIn.isRemote) {
    // return ActionResultType.SUCCESS;
    // } else {
    // player.openContainer(state.getContainer(worldIn, pos));
    // return ActionResultType.CONSUME;
    // }
    // }
    //
    // public INamedContainerProvider getContainer(BlockState state, World worldIn, BlockPos pos) {
    // return new SimpleNamedContainerProvider((windowId, playerInventory, playerEntity) -> {
    // return new MateriaIncubatorContainer(windowId, playerInventory, worldIn, pos);
    // }, textComponent);
    // }

}

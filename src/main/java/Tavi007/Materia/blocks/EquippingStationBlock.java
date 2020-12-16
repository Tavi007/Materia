package Tavi007.Materia.blocks;

import Tavi007.Materia.inventory.container.EquippingStationContainer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class EquippingStationBlock extends Block {

	private static final ITextComponent textComponen = new TranslationTextComponent("container.equipping_station");
	   
	public EquippingStationBlock(Properties properties) {
		super(properties);
	}

	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (worldIn.isRemote) {
			return ActionResultType.SUCCESS;
		} else {
			player.openContainer(state.getContainer(worldIn, pos));
			return ActionResultType.CONSUME;
		}
	}

	public INamedContainerProvider getContainer(BlockState state, World worldIn, BlockPos pos) {
		return new SimpleNamedContainerProvider((windowId, playerInventory, playerEntity) -> {
			return new EquippingStationContainer(windowId, playerInventory, worldIn, pos);
		}, textComponen);
	}

}

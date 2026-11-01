package Tavi007.Materia.common.registry.registrar;

import com.google.common.base.Supplier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public interface BlockEntityRegistrar {
    <T extends BlockEntity> Supplier<BlockEntityType<T>> register(String id, BlockEntityFactory<T> builder, Supplier<Block> block);
}

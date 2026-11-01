package Tavi007.Materia.registry.registrar;

import Tavi007.Materia.common.registry.registrar.BlockEntityFactory;
import Tavi007.Materia.common.registry.registrar.BlockEntityRegistrar;
import Tavi007.Materia.registry.BlockEntityList;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;

public class ForgeBlockEntityRegistrar implements BlockEntityRegistrar {

    @Override
    public <T extends BlockEntity> Supplier<BlockEntityType<T>> register(String id, BlockEntityFactory<T> builder, Supplier<Block> block) {
        RegistryObject<BlockEntityType<T>> object = BlockEntityList.BLOCK_ENTITY_TYPES.register(
                id,
                () -> BlockEntityType.Builder.of(
                        builder::create,
                        block.get()
                ).build(null)
        );
        return Suppliers.memoize(object::get);
    }
}

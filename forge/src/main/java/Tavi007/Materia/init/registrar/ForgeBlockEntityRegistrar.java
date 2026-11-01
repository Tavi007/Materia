package Tavi007.Materia.init.registrar;

import Tavi007.Materia.common.blocks.MateriaIncubatorBlock;
import Tavi007.Materia.common.blocks.entities.MateriaIncubatorBlockEntity;
import Tavi007.Materia.common.init.ModBlockEntities;
import Tavi007.Materia.common.init.ModBlocks;
import Tavi007.Materia.common.init.registrar.BlockEntityFactory;
import Tavi007.Materia.common.init.registrar.BlockEntityRegistrar;
import Tavi007.Materia.common.init.registrar.MenuFactory;
import Tavi007.Materia.common.init.registrar.MenuRegistrar;
import Tavi007.Materia.init.BlockEntityList;
import Tavi007.Materia.init.MenuList;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.extensions.IForgeBlockEntity;
import net.minecraftforge.common.extensions.IForgeMenuType;
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

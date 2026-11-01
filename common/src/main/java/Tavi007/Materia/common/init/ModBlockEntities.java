package Tavi007.Materia.common.init;

import Tavi007.Materia.common.Constants;
import Tavi007.Materia.common.blocks.entities.MateriaIncubatorBlockEntity;
import Tavi007.Materia.common.init.registrar.BlockEntityRegistrar;
import com.google.common.base.Supplier;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntities {
    public static Supplier<BlockEntityType<MateriaIncubatorBlockEntity>> MATERIA_INCUBATOR;

    public static void register(BlockEntityRegistrar registrar) {
        MATERIA_INCUBATOR = registrar.register(
                Constants.MATERIA_INCUBATOR,
                MateriaIncubatorBlockEntity::new,
                ModBlocks.MATERIA_INCUBATOR);
    }
}

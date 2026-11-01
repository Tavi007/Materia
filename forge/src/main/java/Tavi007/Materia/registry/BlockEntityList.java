package Tavi007.Materia.registry;

import Tavi007.Materia.common.Constants;
import Tavi007.Materia.common.registry.ModBlockEntities;
import Tavi007.Materia.registry.registrar.ForgeBlockEntityRegistrar;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockEntityList {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Constants.MOD_ID);

    static {
        ForgeBlockEntityRegistrar registrar = new ForgeBlockEntityRegistrar();
        ModBlockEntities.register(registrar);
    }
}

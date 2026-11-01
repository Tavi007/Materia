package Tavi007.Materia.registry;

import Tavi007.Materia.common.Constants;
import Tavi007.Materia.common.registry.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockList {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Constants.MOD_ID);

    static {
        ModBlocks.BLOCK_MAP.forEach(BLOCKS::register);
    }

}

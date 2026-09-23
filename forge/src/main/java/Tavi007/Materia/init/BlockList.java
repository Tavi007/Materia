package Tavi007.Materia.init;

import Tavi007.Materia.Materia;
import Tavi007.Materia.common.Constants;
import Tavi007.Materia.common.blocks.EquippingStationBlock;
import Tavi007.Materia.common.blocks.MateriaIncubatorBlock;
import Tavi007.Materia.common.init.ModBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class BlockList {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Constants.MOD_ID);

    static {
        BiConsumer<String, Supplier<Block>> registerConsumer = BLOCKS::register;
        ModBlocks.register(registerConsumer);
    }

}

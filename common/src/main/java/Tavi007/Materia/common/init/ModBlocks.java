package Tavi007.Materia.common.init;

import Tavi007.Materia.common.Constants;
import Tavi007.Materia.common.blocks.EquippingStationBlock;
import Tavi007.Materia.common.blocks.MateriaIncubatorBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

import java.util.HashMap;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

public class ModBlocks {

    public static final HashMap<String, Supplier<Block>> BLOCK_MAP = new HashMap<>();

    public static final BlockBehaviour.Properties METAL = BlockBehaviour.Properties.of()
            .mapColor(MapColor.METAL)
            .instrument(NoteBlockInstrument.IRON_XYLOPHONE)
            .requiresCorrectToolForDrops()
            .strength(5.0F, 6.0F)
            .sound(SoundType.METAL);

    public static Supplier<Block> EQUIPPING_STATION = register(Constants.EQUIPPING_STATION, () -> new EquippingStationBlock(METAL));
    public static Supplier<Block> MATERIA_INCUBATOR = register(Constants.MATERIA_INCUBATOR, () -> new MateriaIncubatorBlock(METAL));

    private static Supplier<Block> register(String name, Supplier<Block> supplier) {
        Supplier<Block> memoized = Suppliers.memoize(supplier);
        BLOCK_MAP.put(name, memoized);
        return memoized;
    }
}

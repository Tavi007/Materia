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
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class ModBlocks {

    //private static final Map<String, Supplier<Block>> registry = new HashMap<>();

    public static final BlockBehaviour.Properties METAL = BlockBehaviour.Properties.of()
            .mapColor(MapColor.METAL)
            .instrument(NoteBlockInstrument.IRON_XYLOPHONE)
            .requiresCorrectToolForDrops()
            .strength(5.0F, 6.0F)
            .sound(SoundType.METAL);

    public static Supplier<Block> EQUIPPING_STATION; //= register(Constants.EQUIPPING_STATION, () -> new EquippingStationBlock(METAL));
    public static Supplier<Block> MATERIA_INCUBATOR; // = register(Constants.MATERIA_INCUBATOR, () -> new MateriaIncubatorBlock(METAL));

//    private static Supplier<Block> register(String name, Supplier<Block> block) {
//        registry.put(name, block);
//        return block;
//    }
//
//    public static void register(BiConsumer<String, Supplier<Block>> registerConsumer) {
//        registry.forEach(registerConsumer);
//    }
}

package Tavi007.Materia.init;

import Tavi007.Materia.Materia;
import Tavi007.Materia.items.MateriaAccessory;
import Tavi007.Materia.items.MateriaAxe;
import Tavi007.Materia.items.MateriaHoe;
import Tavi007.Materia.items.MateriaItem;
import Tavi007.Materia.items.MateriaPickaxe;
import Tavi007.Materia.items.MateriaShovel;
import Tavi007.Materia.items.MateriaSword;
import Tavi007.Materia.items.MateriaWand;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemList {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Materia.MOD_ID);

    // only used as Icon for Item group
    private static Properties singleStack = new Item.Properties().stacksTo(1);
    public static final RegistryObject<Item> BASE_MATERIA = ITEMS.register("base_materia", () -> new MateriaItem(singleStack, new int[] {}));

    // materia
    public static final RegistryObject<Item> FIRE_MATERIA = ITEMS.register("fire_materia", () -> new MateriaItem(singleStack, new int[] { 10, 25 }));
    public static final RegistryObject<Item> ICE_MATERIA = ITEMS.register("ice_materia", () -> new MateriaItem(singleStack, new int[] { 10, 25 }));
    public static final RegistryObject<Item> AREA_MATERIA = ITEMS.register("area_materia", () -> new MateriaItem(singleStack, new int[] { 50 }));

    public static final RegistryObject<Item> SPEED_UP_MATERIA = ITEMS.register("speed_up_materia", () -> new MateriaItem(singleStack, new int[] { 100 }));

    // pickaxes
    public static final RegistryObject<Item> MATERIA_DIAMOND_PICKAXE = ITEMS.register("materia_diamond_pickaxe",
        () -> new MateriaPickaxe(Tiers.DIAMOND, 1, -2.8F, singleStack, new int[] { 1 }, new int[] { 3 }));

    // axes
    public static final RegistryObject<Item> MATERIA_DIAMOND_AXE = ITEMS.register("materia_diamond_axe",
        () -> new MateriaAxe(Tiers.DIAMOND, 1, -2.8F, singleStack, new int[] { 1, 1 }, new int[] { 2 }));

    // shovel
    public static final RegistryObject<Item> MATERIA_DIAMOND_SHOVEL = ITEMS.register("materia_diamond_shovel",
        () -> new MateriaShovel(Tiers.DIAMOND, 1, -2.8F, singleStack, new int[] { 1, 2 }, new int[] { 2, 1 }));

    // hoe
    public static final RegistryObject<Item> MATERIA_DIAMOND_HOE = ITEMS.register("materia_diamond_hoe",
        () -> new MateriaHoe(Tiers.DIAMOND, 1, -2.8F, singleStack, new int[] { 1, 3 }, new int[] { 3, 1 }));

    // sword
    public static final RegistryObject<Item> MATERIA_DIAMOND_SWORD = ITEMS.register("materia_diamond_sword",
        () -> new MateriaSword(Tiers.DIAMOND, 1, -2.8F, singleStack, new int[] { 2, 2 }, new int[] { 4 }));

    // wand
    public static final RegistryObject<Item> MATERIA_DIAMOND_WAND = ITEMS.register("materia_diamond_wand",
        () -> new MateriaWand(Tiers.DIAMOND, singleStack, new int[] { 1, 1, 1 }, new int[] { 1, 1, 2 }));

    // accessory
    public static final RegistryObject<Item> MATERIA_DIAMOND_ACCESSORY = ITEMS.register("materia_diamond_accessory",
        () -> new MateriaAccessory(Tiers.DIAMOND, singleStack, new int[] { 1, 2, 1 }, new int[] { 2, 1, 1 }));

    @SubscribeEvent
    public static void onRegisterCreativeTabEvent(CreativeModeTabEvent.Register event) {
        event.registerCreativeModeTab(new ResourceLocation(Materia.MOD_ID, "items"),
            builder -> builder

                .title(Component.translatable("itemGroup.materia"))
                .icon(() -> new ItemStack(ItemList.BASE_MATERIA.get()))
                .displayItems((featureFlag, output, flag) -> {
                    output.accept(ItemList.FIRE_MATERIA.get());
                    output.accept(ItemList.ICE_MATERIA.get());
                    output.accept(ItemList.AREA_MATERIA.get());
                    output.accept(ItemList.SPEED_UP_MATERIA.get());

                    output.accept(ItemList.MATERIA_DIAMOND_PICKAXE.get());
                    output.accept(ItemList.MATERIA_DIAMOND_AXE.get());
                    output.accept(ItemList.MATERIA_DIAMOND_SHOVEL.get());
                    output.accept(ItemList.MATERIA_DIAMOND_HOE.get());
                    output.accept(ItemList.MATERIA_DIAMOND_SWORD.get());
                    output.accept(ItemList.MATERIA_DIAMOND_WAND.get());
                    output.accept(ItemList.MATERIA_DIAMOND_ACCESSORY.get());

                    output.accept(BlockList.EQUIPPING_STATION_ITEM.get());
                    output.accept(BlockList.MATERIA_INCUBATOR_ITEM.get());
                }));
    }
}

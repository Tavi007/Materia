package Tavi007.Materia.common;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Constants {

    public static final String MOD_ID = "materia";
    public static final String MOD_NAME = "Materia";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static Minecraft MINECRAFT = Minecraft.getInstance();

    // Elements
    public static final String FIRE = "fire";
    public static final String ICE = "ice";
    public static final String LIGHTNING = "lightning";
    public static final String AQUA = "aqua";
    public static final String BIO = "bio";

    // GUI

    // Blocks
    public static final String EQUIPPING_STATION = "equipping_station";
    public static final String MATERIA_INCUBATOR = "materia_incubator";
    public static final String PARA_MAGIC_EXTRACTOR = "para_magic_extractor";
    public static final String MAKO_PUMP = "mako_pump";

    // Capabilities
    public static final ResourceLocation MATERIA_LEVEL_DATA_CAPABILITY = new ResourceLocation(MOD_ID, "level");

    // Items
    public static final String MATERIA = "materia";
    public static final String MAGICITE = "magicite";
    public static final String PHIAL = "phial";

    public static final String FIRE_MATERIA = FIRE + "_" + MATERIA;
    public static final String FIRE_MAGICITE = FIRE + "_" + MAGICITE;
    public static final String FIRE_PHIAL = FIRE + "_" + PHIAL;
    public static final String ICE_MATERIA = ICE + "_" + MATERIA;
    public static final String ICE_MAGICITE = ICE + "_" + MAGICITE;
    public static final String ICE_PHIAL = ICE + "_" + PHIAL;
    public static final String LIGHTNING_MATERIA = LIGHTNING + "_" + MATERIA;
    public static final String LIGHTNING_MAGICITE = LIGHTNING + "_" + MAGICITE;
    public static final String LIGHTNING_PHIAL = LIGHTNING + "_" + PHIAL;
    public static final String AQUA_MATERIA = AQUA + "_" + MATERIA;
    public static final String AQUA_MAGICITE = AQUA + "_" + MAGICITE;
    public static final String AQUA_PHIAL = AQUA + "_" + PHIAL;
    public static final String BIO_MATERIA = BIO + "_" + MATERIA;
    public static final String BIO_MAGICITE = BIO + "_" + MAGICITE;
    public static final String BIO_PHIAL = BIO + "_" + PHIAL;

    public static final String SIZE_UP_MATERIA = "size_up_materia";
    public static final String WIDTH_UP_MATERIA = "width_up_materia";
    public static final String RANGE_UP_MATERIA = "range_up_materia";
    public static final String HEIGHT_UP_MATERIA = "height_up_materia";
    public static final String SPEED_UP_MATERIA = "speed_up_materia";
    public static final String TARGET_MATERIA = "target_materia";

    public static final String MATERIA_DIAMOND_PICKAXE = "materia_diamond_pickaxe";
    public static final String MATERIA_DIAMOND_AXE = "materia_diamond_axe";
    public static final String MATERIA_DIAMOND_SHOVEL = "materia_diamond_shovel";
    public static final String MATERIA_DIAMOND_HOE = "materia_diamond_hoe";
    public static final String MATERIA_DIAMOND_SWORD = "materia_diamond_sword";
    public static final String MATERIA_DIAMOND_WAND = "materia_diamond_wand";
    public static final String MATERIA_DIAMOND_ACCESSORY = "materia_diamond_accessory";

    public static final String ABILITY_POINT_BOTTLE = "ability_point_bottle";
    public static final String PARA_MAGIC_PHIAL = "para_magic_phial";

    // Creative Tabs
    public static final String MATERIA_TAB = "materias";
    public static final String MATERIA_TOOL_TAB = "materia_tools";
    public static final String MISC_TAB = "misc";

    // Particles

    //Entities
    public static final String ABILITY_POINT_ORB = "ability_point_orb";
    public static final String THROWN_ABILITY_POINT_BOTTLE = "thrown_ability_point_bottle";
    public static final String SPELL_PROJECTILE = "spell_projectile";

    // Recipes
    public static final String MATERIA_INCUBATOR_RECIPE = "recipes/materia_incubator";


    // some ResourceLocations
    public static final ResourceLocation SPELL_TEXTURE = new ResourceLocation(Constants.MOD_ID, "textures/spell/default.png");;
    public static final ResourceLocation SPELL_TRAIL_TEXTURE = new ResourceLocation(Constants.MOD_ID, "default_spell_trail");;

}

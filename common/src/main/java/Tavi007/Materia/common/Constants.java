package Tavi007.Materia.common;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {

    public static final String MOD_ID = "materia";
    public static final String MOD_NAME = "Materia";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static Minecraft MINECRAFT = Minecraft.getInstance();

    // GUI

    // Blocks
    public static final String EQUIPPING_STATION = "equipping_station";
    public static final String MATERIA_INCUBATOR = "materia_incubator";

    // Capabilities
    public static final ResourceLocation MATERIA_LEVEL_DATA_CAPABILITY = new ResourceLocation(MOD_ID, "level");

    // Items
    public static final String BASE_MATERIA = "base_materia";
    public static final String FIRE_MATERIA = "fire_materia";
    public static final String ICE_MATERIA = "ice_materia";
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

    // Particles

    //Entities
    public static final String ABILITY_POINT_ORB = "ability_point_orb";
    public static final String THROWN_ABILITY_POINT_BOTTLE = "thrown_ability_point_bottle";
    public static final String SPELL_PROJECTILE = "spell_projectile";
}

package Tavi007.Materia;

import Tavi007.Materia.server.ServerConfigAccessors;
import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class ServerConfig {

    public static final ForgeConfigSpec CONFIG_SPEC;
    private static final ServerConfig SERVER;

    static {
        Pair<ServerConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
        CONFIG_SPEC = specPair.getRight();
        SERVER = specPair.getLeft();
    }

    ServerConfig(ForgeConfigSpec.Builder builder) {
        ServerConfigAccessors.init(
        builder
            .comment("The default ap Amount for any mob, if not provided by a datapack.")
            .defineInRange("baseMobApAmount", 100, 0, 100_000_000),
        builder
            .comment(
                "Scale the ap drop penalty for killing to many mobs of the same type in a short time period. 0 disables the penalty, >1 increases it.")
            .defineInRange("scaleMobApDropPenalty", 1.0, 0, 10.0),
        builder
            .comment("The amount of Ability Points dropped by the Bottle of Ability Points. Amount will vary depending on the apBottleRandomPercent.")
            .defineInRange("apBottleAmount", 100, 0, 1_000_000),
        builder
            .comment(
                "Applies some randomness to the amount of Ability Points from the Bottle. If the bottle amount is 100 and the random percent is 10, the bottle will drop between 90 and 110 AP.")
            .defineInRange("apBottleRandomPercent", 10, 0, 100)
        );
    }
}

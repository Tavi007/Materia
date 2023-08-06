package Tavi007.Materia;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class ServerConfig {

    public static final ForgeConfigSpec CONFIG_SPEC;
    private static final ServerConfig SERVER;

    private final IntValue baseMobApAmount;
    private final DoubleValue mobApDropPenaltyScaling;

    static {
        Pair<ServerConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
        CONFIG_SPEC = specPair.getRight();
        SERVER = specPair.getLeft();
    }

    ServerConfig(ForgeConfigSpec.Builder builder) {
        baseMobApAmount = builder
            .comment("The default ap Amount for any mob, if not provided by a datapack.")
            .defineInRange("baseMobApAmount", 100, 0, Integer.MAX_VALUE);
        mobApDropPenaltyScaling = builder
            .comment(
                "Scale the ap drop penalty for killing to many mobs of the same type in a short time period. 0 disables the penalty, >1 increases it.")
            .defineInRange("scaleMobApDropPenalty", 1.0, 0, 10.0);
    }

    public static int getBaseMobApAmount() {
        return SERVER.baseMobApAmount.get();
    }

    public static double getMobApDropPenaltyScaling() {
        return SERVER.mobApDropPenaltyScaling.get();
    }
}

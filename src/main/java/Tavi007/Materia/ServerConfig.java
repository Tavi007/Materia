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

    private final IntValue apBottleAmount;
    private final IntValue apBottleRandomPercent;

    static {
        Pair<ServerConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
        CONFIG_SPEC = specPair.getRight();
        SERVER = specPair.getLeft();
    }

    ServerConfig(ForgeConfigSpec.Builder builder) {
        baseMobApAmount = builder
            .comment("The default ap Amount for any mob, if not provided by a datapack.")
            .defineInRange("baseMobApAmount", 100, 0, 100_000_000);
        mobApDropPenaltyScaling = builder
            .comment(
                "Scale the ap drop penalty for killing to many mobs of the same type in a short time period. 0 disables the penalty, >1 increases it.")
            .defineInRange("scaleMobApDropPenalty", 1.0, 0, 10.0);
        apBottleAmount = builder
            .comment("The amount of Ability Points dropped by the Bottle of Ability Points. Amount will vary depending on the apBottleRandomPercent.")
            .defineInRange("apBottleAmount", 100, 0, 1_000_000);
        apBottleRandomPercent = builder
            .comment(
                "Applies some randomness to the amount of Ability Points from the Bottle. If the bottle amount is 100 and the random percent is 10, the bottle will drop between 90 and 110 AP.")
            .defineInRange("apBottleRandomPercent", 10, 0, 100);
    }

    public static int getBaseMobApAmount() {
        return SERVER.baseMobApAmount.get();
    }

    public static double getMobApDropPenaltyScaling() {
        return SERVER.mobApDropPenaltyScaling.get();
    }

    public static int getApBottleMinAmount() {
        float lowerBoundPercent = ((float) 100 - SERVER.apBottleRandomPercent.get()) / 100;
        return (int) (lowerBoundPercent * SERVER.apBottleAmount.get());
    }

    public static int getApBottleMaxAmount() {
        float lowerBoundPercent = ((float) 100 + SERVER.apBottleRandomPercent.get()) / 100;
        return (int) (lowerBoundPercent * SERVER.apBottleAmount.get());
    }
}

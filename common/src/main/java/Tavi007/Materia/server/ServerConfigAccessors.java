package Tavi007.Materia.server;

import com.sun.jdi.DoubleValue;

import java.util.function.Supplier;

public class ServerConfigAccessors {

    private static Supplier<Integer> baseMobApAmount;
    private static Supplier<Double> mobApDropPenaltyScaling;
    private static Supplier<Integer> apBottleAmount;
    private static Supplier<Integer> apBottleRandomPercent;

    public static void init(Supplier<Integer> baseMobApAmount,
                            Supplier<Double> mobApDropPenaltyScaling,
                            Supplier<Integer> apBottleAmount,
                            Supplier<Integer> apBottleRandomPercent) {
        ServerConfigAccessors.baseMobApAmount = baseMobApAmount;
        ServerConfigAccessors.mobApDropPenaltyScaling = mobApDropPenaltyScaling;
        ServerConfigAccessors.apBottleAmount = apBottleAmount;
        ServerConfigAccessors.apBottleRandomPercent = apBottleRandomPercent;
    }

    public static int getBaseMobApAmount() {
        return baseMobApAmount.get();
    }

    public static double getMobApDropPenaltyScaling() {
        return mobApDropPenaltyScaling.get();
    }

    public static int getApBottleAmount() {
        return apBottleAmount.get();
    }

    public static int getApBottleRandomPercent() {
        return apBottleRandomPercent.get();
    }

}

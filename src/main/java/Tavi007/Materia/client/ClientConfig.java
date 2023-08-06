package Tavi007.Materia.client;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig {

    public static final ForgeConfigSpec CONFIG_SPEC;
    private static final ClientConfig CLIENT;

    static {
        Pair<ClientConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
        CONFIG_SPEC = specPair.getRight();
        CLIENT = specPair.getLeft();
    }

    ClientConfig(ForgeConfigSpec.Builder builder) {

    }
}

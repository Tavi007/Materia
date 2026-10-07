package Tavi007.Materia.common.data.datapack;


import Tavi007.Materia.common.Constants;
import Tavi007.Materia.common.network.packets.SynchronizeDatapackPacket;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatapackDataAccessor {

    public static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();

    private static Map<ResourceLocation, List<Integer>> levelUpData = new HashMap<>();

    public static void resetDatapackData(Map<ResourceLocation, JsonElement> jsonObjects) {
        Map<String, Map<String, Integer>> counter = new HashMap<String, Map<String, Integer>>(); // for logging

        Map<ResourceLocation, List<Integer>> levelUpData = new HashMap<>();

        jsonObjects.forEach((rl, json) -> {
            try {
                String modId = rl.getNamespace();
                String type = "incorrect resource location (check the directory path for typos!)";

                Map<String, Integer> propertyCounter = counter.get(modId);
                if (propertyCounter == null) {
                    propertyCounter = new HashMap<String, Integer>();
                    propertyCounter.put(type, 0);
                    counter.put(modId, propertyCounter);
                } else {
                    Integer count = propertyCounter.get(type);
                    if (count == null) {
                        propertyCounter.put(type, 0);
                    } else {
                        propertyCounter.put(type, propertyCounter.get(type) + 1);
                    }
                }
            } catch (Exception exception) {
                Constants.LOGGER.error("Couldn't parse combat properties {}", rl, exception);
            }
        });

        counter.forEach((modId, propertyCounter) -> {
            Constants.LOGGER.info("The mod " + modId + " loaded: ");
            propertyCounter.forEach((type, amount) -> {
                Constants.LOGGER.info(amount + " " + type);
            });
        });
    }

    public static List<Integer> getLevelUpData(ResourceLocation resourceLocation) {
        return levelUpData.get(resourceLocation);
    }

    public static void logLoadedData() {
        logLoadedData("server", levelUpData.size(), "level up data");
    }

    private static void logLoadedData(String side, int size, String type) {
        Constants.LOGGER.info(side + " loaded " + size + " combat properties for " + type);
    }

    public static void sendSyncMessage(ServerPlayer player) {
        SynchronizeDatapackPacket packet = new SynchronizeDatapackPacket(levelUpData);
        //ServerPacketSender.sendPacket(packet, player);
    }

    public static void applySyncMessage(SynchronizeDatapackPacket message) {
        levelUpData = message.levelUpData;
    }
}

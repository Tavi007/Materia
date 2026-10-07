package Tavi007.Materia.common.data.pojo;

import com.google.gson.annotations.SerializedName;

import Tavi007.Materia.server.ServerConfigAccessors;

public class MobData {

    @SerializedName("ap_amount")
    private final int apAmount;

    public MobData() {
        this.apAmount = ServerConfigAccessors.getBaseMobApAmount();
    }

    public MobData(MobData mobData) {
        this.apAmount = mobData.apAmount;
    }

    public int getApAmount() {
        return apAmount;
    }
}

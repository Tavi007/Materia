package Tavi007.Materia.data.pojo;

import com.google.gson.annotations.SerializedName;

import Tavi007.Materia.ServerConfig;

public class MobData {

    @SerializedName("ap_amount")
    private int apAmount;

    public MobData() {
        this.apAmount = ServerConfig.getBaseMobApAmount();
    }

    public MobData(MobData mobData) {
        this.apAmount = mobData.apAmount;
    }

    public int getApAmount() {
        return apAmount;
    }
}

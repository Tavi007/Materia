package Tavi007.Materia.capabilities;

import Tavi007.Materia.common.data.capabilities.MateriaLevelData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.ArrayList;

public class MateriaLevelDataSerializer implements INBTSerializable<Tag> {

    private final MateriaLevelData data;

    //  TODO: is this correct?
    public MateriaLevelDataSerializer() {
        this.data = new MateriaLevelData();
    }

    public MateriaLevelDataSerializer(MateriaLevelData data) {
        this.data = data;
    }

    public MateriaLevelData getData() {
        return data;
    }

    @Override
    public Tag serializeNBT() {
        return data.serializeNBT();
    }

    @Override
    public void deserializeNBT(Tag nbt) {
        data.deserializeNBT((CompoundTag) nbt);
    }
}

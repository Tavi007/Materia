package Tavi007.Materia.network;

import Tavi007.Materia.common.network.IPacketContext;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.Optional;

public class ForgePacketContext implements IPacketContext {

    private final NetworkEvent.Context context;

    public ForgePacketContext(NetworkEvent.Context context) {
        this.context = context;
    }

    @Override
    public boolean isServerSide() {
        return false;
    }

    @Override
    public Optional<Player> player() {
        return Optional.empty();
    }

    @Override
    public Optional<Level> level() {
        return Optional.empty();
    }

    @Override
    public void execute(Runnable runnable) {

    }
}

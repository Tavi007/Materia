package Tavi007.Materia.common.network;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.Optional;

public interface IPacketContext {

    boolean isServerSide();

    Optional<Player> player();

    Optional<Level> level();

    void execute(Runnable runnable);
}
package org.dp.event.LandedEvent;

import org.dp.assets.PlayerInfo;
import org.dp.event.IGameEvent;
import org.dp.logic.GameSystem;
import org.dp.logic.Player;
import org.dp.logic.Tile;

public class PlayerLandedOnHospitalTile implements IGameEvent{
    private final Player player;
    private final Tile currentTile;

    public PlayerLandedOnHospitalTile(Player player, Tile currentTile) {
        this.player = player;
        this.currentTile = currentTile;
        PlayerInfo playerInfos = GameSystem.get().getPlayerInfo();
        playerInfos.setInHospital(player.playerID,true,2);
    }

    public Player getPlayer() {
        return player;
    }

    public Tile getCurrentTile() {
        return currentTile;
    }
}

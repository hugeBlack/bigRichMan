package org.dp.event.LandedEvent;

import org.dp.event.IGameEvent;
import org.dp.logic.Player;
import org.dp.logic.Tile;

public class PlayerLandedOnHospitalTile implements IGameEvent{
    private final Player player;
    private final Tile currentTile;

    public PlayerLandedOnHospitalTile(Player player, Tile currentTile) {
        this.player = player;
        this.currentTile = currentTile;
    }

    public Player getPlayer() {
        return player;
    }

    public Tile getCurrentTile() {
        return currentTile;
    }
}

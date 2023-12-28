package org.dp.event.LandedEvent;

import org.dp.event.IGameEvent;
import org.dp.logic.Player;
import org.dp.logic.Tile;

public class PlayerLandedOnPlaceTile implements IGameEvent{
    private final Player player;
    private final Tile currentTile;

    public PlayerLandedOnPlaceTile(Player player, Tile currentTile) {
        this.player = player;
        this.currentTile = currentTile;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Tile getCurrentTile() {
        return this.currentTile;
    }

    
}

package org.dp.event;

import org.dp.components.PlayerComponent;
import org.dp.components.tiles.TileComponent;

import java.util.LinkedList;

public class UIPlayerMoveEvent implements IGameEvent{
    public LinkedList<TileComponent> tilesPassed = new LinkedList<>();
    public PlayerComponent target;
}

package org.dp.logic;

import org.dp.assets.PlayerInfo;
import org.dp.components.PlayerComponent;
import org.dp.components.tiles.TileComponent;
import org.dp.event.GameEventBus;
import org.dp.event.UIPlayerMoveEvent;
import org.dp.utils.Vector2i;

import java.util.LinkedList;


public class Player {

    public PlayerComponent playerComponent;
    public PlayerInfo playerInfo;
    public int playerID;

    // 玩家当前所在的地块
    public Tile currentTile;
    public Player(Tile initTile, int typeId){
        this.currentTile = initTile;
        this.playerID = typeId;
        Vector2i initLocation = initTile.component.getAbsPosition().add(20, 10);
        playerComponent = new PlayerComponent(initLocation, typeId);


    }

    public void gotoTile(Tile newTile){
        int l = 0;
        Tile nowTile = currentTile;
        LinkedList<TileComponent> path = new LinkedList<>();
        while(l < 100 && nowTile != newTile){
            nowTile = nowTile.next;
            path.add(nowTile.component);
            ++l;
        }
        if(l >= 100){
            throw new RuntimeException("Path too long! Does map contains a loop?");
        }
        UIPlayerMoveEvent e = new UIPlayerMoveEvent();
        e.tilesPassed = path;
        e.target = playerComponent;
        // 发出一个UI移动指令
        GameEventBus.get().emitEvent(e);
        currentTile = newTile;
    }
}

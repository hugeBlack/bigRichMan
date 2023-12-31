package org.dp.event.LandedEvent;

import org.dp.logic.GameSystem;
import org.dp.logic.Player;
import org.dp.logic.Tile;
import org.dp.assets.AssetFactory;
import org.dp.assets.PlayerInfo;
import org.dp.view.ComponentObserver;
import org.dp.view.ConfirmBox;
import org.dp.view.events.ComponentEvent;
import org.dp.view.events.ConfirmBoxEvent;
import org.dp.components.tiles.*;
import org.dp.event.GameEventListener;
import org.dp.event.IGameEvent;

public class HospitalTileListener implements GameEventListener {
    private Player currentPlayer;
    private int playerID;
    public PlayerInfo playerInfos = GameSystem.get().getPlayerInfo();
    public Tile currentTile;

    // 实现onEvent方法以处理事件
    @Override
    public void onEvent(IGameEvent event) {
        this.currentPlayer = ((PlayerLandedOnHospitalTile) event).getPlayer();
        this.currentTile = ((PlayerLandedOnHospitalTile) event).getCurrentTile();
        this.playerID = currentPlayer.playerID;

        PlayerLandedOnHospitalEvent();
    }

    private void PlayerLandedOnHospitalEvent() {
        // 处理玩家落在医院的事件

    }

}

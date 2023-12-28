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

public class StartTileListener implements GameEventListener {
    private Player currentPlayer;
    private int playerID;
    public PlayerInfo playerInfos = GameSystem.get().getPlayerInfo();
    public Tile currentTile;

    // 实现onEvent方法以处理事件
    @Override
    public void onEvent(IGameEvent event) {
        this.currentPlayer = ((PlayerLandedOnStartTile) event).getPlayer();
        this.currentTile = ((PlayerLandedOnStartTile) event).getCurrentTile();
        this.playerID = currentPlayer.playerID;

        // 根据事件的具体类型执行不同的操作
        PlayerLandedOnStartEvent();
        // 可以添加更多的事件类型和对应的处理逻辑
    }

    private void PlayerLandedOnStartEvent() {
        // 处理玩家落在起点的事件
        PlayerInfo playerInfo = playerInfos.getPlayerInfo(playerID);

        // 获得玩家当前金币数
        int currentCoin = playerInfo.coinNum;
        // 玩家到达起点，获得1000金币奖励
        playerInfo.updatePlayerInfo(playerID, "coin", currentCoin + 1000);
        // 弹窗提示
        ConfirmBox c = new ConfirmBox(playerInfo.defaultName + "路过起点，获得1000金币奖励！");
        c.show();
    }

}

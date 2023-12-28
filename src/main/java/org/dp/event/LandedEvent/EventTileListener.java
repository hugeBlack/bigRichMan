package org.dp.event.LandedEvent;

import org.dp.event.GameEventBus;
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

public class EventTileListener implements GameEventListener {
    private Player currentPlayer;
    private int playerID;
    public PlayerInfo playerInfos = GameSystem.get().getPlayerInfo();
    public Tile currentTile;

    // 实现onEvent方法以处理事件
    @Override
    public void onEvent(IGameEvent event) {
        this.currentPlayer = ((PlayerLandedOnEventTile) event).getPlayer();
        this.currentTile = ((PlayerLandedOnEventTile) event).getCurrentTile();
        this.playerID = currentPlayer.playerID;

        PlayerLandedOnEvent();
        
    }

    private void PlayerLandedOnEvent() {
        // 处理玩家落在事件格的事件
        // 处理玩家落在起点的事件
        PlayerInfo playerInfo = playerInfos.getPlayerInfo(playerID);

        // 获得玩家当前金币数
        int currentCoupon = playerInfo.couponNum;
        // 获得1-3的一个随机整数upNum
        int upNum = (int) (Math.random() * 3) + 1;
        // 玩家到达起点，获得点券奖励
        playerInfo.updatePlayerInfo(playerID, "coupon", currentCoupon + 1000*upNum);
        // 弹窗提示
        ConfirmBox c = new ConfirmBox(playerInfo.defaultName + "命运女神眷顾你，获得" + 1000 * upNum + "点券奖励！");
        c.show();
    }

}

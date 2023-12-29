package org.dp.event.LandedEvent;

import org.dp.event.IGameEvent;
import org.dp.logic.Player;
import org.dp.logic.Tile;
import org.dp.view.ConfirmBox;
import org.dp.assets.PlayerInfo;
import org.dp.logic.GameSystem;

public class PlayerLandedOnEventTile implements IGameEvent{
    private final Player player;
    private final Tile currentTile;

    public PlayerLandedOnEventTile(Player player, Tile currentTile) {
        this.player = player;
        this.currentTile = currentTile;
        add_coupon();
        ConfirmBox c = new ConfirmBox("点劵增加了1000点!");
        c.show();
        c.setCallback((type) -> {});
    }

    private void add_coupon(){
        PlayerInfo playerInfos = GameSystem.get().getPlayerInfo();
        PlayerInfo currPlayerInfo=playerInfos.getPlayerInfo(GameSystem.get().getActorChoose()[this.player.playerID]);
        int currCouponNum=currPlayerInfo.getPlayerInfo(this.player.playerID).couponNum;
        currPlayerInfo.updatePlayerInfo(this.player.playerID,"coupon",1000+currCouponNum);//这里想改成增加数量读取命运格子的bonus，但不知道怎么通过Tile获取EventTile
    }

    public Player getPlayer() {
        return player;
    }

    public Tile getCurrentTile() {
        return currentTile;
    }
}

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

public class PlaceTileListener implements GameEventListener {
    private Player currentPlayer;
    private int playerID;
    public PlayerInfo playerInfos = GameSystem.get().getPlayerInfo();
    public Tile currentTile;

    // 实现onEvent方法以处理事件
    @Override
    public void onEvent(IGameEvent event) {
        currentPlayer = ((PlayerLandedOnPlaceTile) event).getPlayer();
        currentTile = ((PlayerLandedOnPlaceTile) event).getCurrentTile();
        playerID = currentPlayer.playerID;
        PlayerLandedOnPlaceEvent();
        // 可以添加更多的事件类型和对应的处理逻辑
    }

    public void PlayerLandedOnPlaceEvent() {
        // 处理玩家落在地产的事件
        PlayerInfo playerInfo = playerInfos.getPlayerInfo(playerID);

        // 获得玩家当前金币数
        int currentCoin = playerInfo.coinNum;
        System.out.println("currentCoin: " + currentCoin);
        // 获取玩家当前房屋数
        int houseNum = playerInfo.houseNum;
        System.out.println("houseNum: " + houseNum);

        // 获取地块的所有者
        int ownerID = ((PlaceTileComponent) currentTile.component).getOwner();
        System.out.println("ownerID: " + ownerID);
        System.out.println("playerID: " + playerID);

        // 如果地块没有所有者，可以购买
        if (ownerID == -1) {
            // 获得地块的价格
            int tilePrice = ((PlaceTileComponent) currentTile.component).getToll(playerID);
            String title = ((PlaceTileComponent) currentTile.component).getTitle();
            // 如果玩家金币数大于地块价格，可以购买
            if (currentCoin > tilePrice) {
                // 弹窗提示
                ConfirmBox c = new ConfirmBox("是否支付" + tilePrice + "金币购买“" + title + "”？");
                c.registerObserver(new ComponentObserver() {
                    @Override
                    public void onEvent(ComponentEvent e) {
                        if (e instanceof ConfirmBoxEvent) {
                            ConfirmBoxEvent e1 = (ConfirmBoxEvent) e;
                            if (e1.getResult()) {
                                // 玩家选择购买
                                playerInfo.updatePlayerInfo(playerID, "coin", currentCoin - tilePrice); // 玩家支付地块价格
                                playerInfo.updatePlayerInfo(playerID, "place", houseNum + 1); // 玩家拥有地块数+1
                                ((PlaceTileComponent) currentTile.component).setOwner(playerID); // 设置地块所有者
                                ((PlaceTileComponent) currentTile.component).setLevel(1); // 设置房屋等级为1
                                ConfirmBox success = new ConfirmBox("购买成功！");
                                success.show();
                            } else {
                                // 玩家选择不购买或取消
                                // 这里可以根据你的游戏逻辑添加代码处理用户拒绝购买的情况
                            }
                        }
                    }
                });
                c.show();
            }
            // 如果玩家金币数小于地块价格，不能购买
            else {
                // 弹窗提示
                ConfirmBox c = new ConfirmBox("金币不足，无法购买！");
                c.show();
            }
        } else {
            PlayerInfo owner = playerInfo.getPlayerInfo(ownerID);
            if (ownerID != playerID) {
                // 获得地块的过路费
                int tollPrice = ((PlaceTileComponent) currentTile.component).getToll(playerID);
                // 如果玩家金币数大于过路费，可以支付过路费
                if (currentCoin > tollPrice) {
                    // 弹窗提示
                    ConfirmBox c = new ConfirmBox("支付" + tollPrice + "金币过路费");
                    c.show();
                    // 玩家支付过路费
                    playerInfo.updatePlayerInfo(playerID, "coin", currentCoin - tollPrice);
                    int ownerCoin = owner.coinNum;
                    owner.updatePlayerInfo(ownerID, "coin", ownerCoin + tollPrice);
                } else {
                    // 弹窗提示
                    ConfirmBox c = new ConfirmBox("金币不足，" + playerInfo.defaultName + "破产！");
                    c.show();
                }
            } else if (ownerID == playerID) {
                // 获取当前房屋等级
                int currentLevel = ((PlaceTileComponent) currentTile.component).getLevel();
                // 获得地块的升级费
                int tollPrice = ((PlaceTileComponent) currentTile.component).getToll(playerID);
                // 弹窗提示
                ConfirmBox c = new ConfirmBox("是否支付" + ((PlaceTileComponent) currentTile.component).getToll(playerID)
                        + "金币将房屋从" + currentLevel + "级升级至" + (currentLevel + 1) + "级？");
                c.registerObserver(new ComponentObserver() {
                    @Override
                    public void onEvent(ComponentEvent e) {
                        if (e instanceof ConfirmBoxEvent) {
                            ConfirmBoxEvent e1 = (ConfirmBoxEvent) e;
                            if (e1.getResult()) {
                                // 玩家支付升级费
                                int currentLevel = ((PlaceTileComponent) currentTile.component).getLevel();
                                playerInfo.updatePlayerInfo(playerID, "coin", currentCoin - tollPrice);
                                ((PlaceTileComponent) currentTile.component).setLevel(++currentLevel);
                                ConfirmBox success = new ConfirmBox("房屋升级至" + currentLevel + "级!");
                                success.show();
                            } else {
                                // 玩家选择不购买或取消
                                // 这里可以根据你的游戏逻辑添加代码处理用户拒绝购买的情况
                            }
                        }
                    }
                });
                c.show();
            }
        }
    }
}

package org.dp.logic;

import org.dp.assets.AssetFactory;
import org.dp.assets.PlayerInfo;
import org.dp.components.PlayerComponent;
import org.dp.components.tiles.*;
import org.dp.event.GameEventBus;
import org.dp.event.LandedEvent.*;
import org.dp.scene.GameScene;
import org.dp.utils.Vector2i;
import org.dp.view.ComponentObserver;
import org.dp.view.ConfirmBox;
import org.dp.view.events.ComponentEvent;
import org.dp.event.*;

// 单例模式
public class GameSystem implements IGameSystem {

    private static GameSystem instance = null;
    private Player currentPlayer;
    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    private Player players[] = new Player[4];
    private GameScene gameScene;
    private int playerNum;

    public PlayerInfo playerInfo = (PlayerInfo) AssetFactory.getAsset("playerInfo");

    @Override
    public PlayerInfo getPlayerInfo() {
        return playerInfo;
    }

    public void setCurrentPlayer(int type) {
        this.currentPlayer = players[type];
    }

    @Override
    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }

    @Override
    public Integer getPlayerNum() {
        return playerNum;
    }

    private GameMap gameMap;

    private int lastDicePoint = -1;

    public static IGameSystem get() {
        if (instance == null)
            instance = new GameSystem();
        return instance;
    }

    private GameSystem() {

    }

    @Override
    public boolean canRollDice() {
        // 现在是看上次的结果有没有用掉决定能不能掷色子，逻辑写好了自己换掉
        return lastDicePoint == -1;
    }

    @Override
    public int getNextDicePoint() {
        return lastDicePoint;
    }

    @Override
    public void setNextDicePoint(int i) {
        lastDicePoint = i;
    }

    @Override
    // performPlayerMove()方法：玩家移动的逻辑，根据骰子点数移动
    public void performPlayerMove() {
//        if (lastDicePoint == -1) {
//            ConfirmBox c = new ConfirmBox("请先投骰子！");
//            c.show();
//            return;
//        }
        // 根据点数，找到下一个目的地
        Tile dest = currentPlayer.currentTile;
        for (int i = 0; i < lastDicePoint; i++) {
            dest = dest.next;
        }
        currentPlayer.gotoTile(dest);
        lastDicePoint = -1;

        // 检测移动完成之后玩家位于什么地块
        playerTileJudge(currentPlayer, dest);
    }

    /**playerTileJudge()方法：
     * 玩家移动完成后位于地块时的逻辑
     * 人物和土地交互使用到GameEventBus（中介者模式）
     * */ 
    public void playerTileJudge(Player currentPlayer, Tile currentTile) {
        // 根据currentTile的类型，确定需要发出哪个事件
        TileComponent tileType = currentTile.component;
        if (tileType instanceof StartTileComponent) {
            // 发出玩家到达起点的事件
            GameEventBus.get().emitEvent(new PlayerLandedOnStartTile(currentPlayer, currentTile));
        }
        else if (tileType instanceof EventTileComponent) {
            // 发出玩家到达事件地块的事件
            GameEventBus.get().emitEvent(new PlayerLandedOnEventTile(currentPlayer, currentTile));
        }
        else if (tileType instanceof HospitalTileComponent) {
            // 发出玩家到达医院的事件
            GameEventBus.get().emitEvent(new PlayerLandedOnHospitalTile(currentPlayer, currentTile));
        }
        else if (tileType instanceof StoreTileComponent) {
            // 发出玩家到达商店的事件
            GameEventBus.get().emitEvent(new PlayerLandedOnStoreTile(currentPlayer, currentTile));
        }
        else if (tileType instanceof PlaceTileComponent) {
            // 发出玩家到达地产的事件
            GameEventBus.get().emitEvent(new PlayerLandedOnPlaceTile(currentPlayer, currentTile));
        }
        // ... 其他逻辑
    }

    @Override
    public GameScene getScene() {
        return gameScene;
    }

    @Override
    public void init() {
        gameScene = new GameScene();
        MapBuilder mapBuilder = new MapBuilder("assets/1.json", new Vector2i(0, 0));
        GameMap gameMap = mapBuilder.build(new Vector2i(300, 300));
        this.gameMap = gameMap;
        gameScene.addComponent(gameMap.mapComponent);

    }

    int[] currentChoose;

    @Override
    public void setActorChoose(int[] currentChoose) {
        this.currentChoose = currentChoose;
        for (int i = 0; i < playerNum; i++) {
            players[i] = new Player(gameMap.firstTile[i], currentChoose[i]);
            gameScene.addComponent(players[i].playerComponent);
        }
        this.currentPlayer = players[0];
    }

    @Override
    public int[] getActorChoose() {
        return currentChoose;
    }

}

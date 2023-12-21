package org.dp.logic;

import org.dp.components.PlayerComponent;
import org.dp.scene.GameScene;
import org.dp.utils.Vector2i;
import org.dp.view.ConfirmBox;

// 单例模式
public class GameSystem implements IGameSystem{

    private static GameSystem instance = null;
    private Player currentPlayer;

    private GameScene gameScene;
    private int   playerNum;
    @Override
    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }
    @Override
    public Integer getPlayerNum()
    {
        return playerNum;
    }
    private GameMap gameMap;

    private int lastDicePoint = -1;
    public static IGameSystem get(){
        if(instance == null)
            instance = new GameSystem();
        return instance;
    }

    private GameSystem(){

    }
    @Override
    public boolean canRollDice() {
        // 现在是看上次的结果有没有用掉决定能不能掷色子，逻辑写好了自己换掉
        return lastDicePoint == -1;
    }

    @Override
    public int getNextDicePoint() {
        lastDicePoint = (int)(Math.random() * 6) + 1;
        return lastDicePoint;
    }

    @Override
    public void performPlayerMove() {
        if(lastDicePoint == -1){
            ConfirmBox c = new ConfirmBox("请先投骰子！");
            c.show();
            return;
        }

        Tile dest = currentPlayer.currentTile;
        for(int i = 0; i < lastDicePoint; i++){
            dest = dest.next;
        }
        currentPlayer.gotoTile(dest);
        lastDicePoint = -1;
    }

    @Override
    public GameScene getScene() {
        return gameScene;
    }

    @Override
    public void init() {
        gameScene = new GameScene();


        MapBuilder mapBuilder = new MapBuilder("some json");
        GameMap gameMap= mapBuilder.build(new Vector2i(300,300));
        this.gameMap = gameMap;
        gameScene.addComponent(gameMap.mapComponent);
        currentPlayer = new Player(gameMap.firstTile, 0);
        gameScene.addComponent(currentPlayer.playerComponent );
    }


}

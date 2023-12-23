package org.dp.logic;

import org.dp.assets.AssetFactory;
import org.dp.assets.PlayerInfo;
import org.dp.components.PlayerComponent;
import org.dp.scene.GameScene;
import org.dp.utils.Vector2i;
import org.dp.view.ConfirmBox;

// 单例模式
public class GameSystem implements IGameSystem{

    private static GameSystem instance = null;
    private Player currentPlayer;
private Player players[] = new Player[4];
    private GameScene gameScene;
    private int   playerNum;

    public PlayerInfo playerInfo = (PlayerInfo) AssetFactory.getAsset("playerInfo");
    @Override
    public PlayerInfo getPlayerInfo()
    {
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
        return lastDicePoint;
    }
    @Override
    public void setNextDicePoint(int i)
    {
        lastDicePoint=i;
    }

    @Override
    public void performPlayerMove() {
        if(lastDicePoint == -1){
            ConfirmBox c = new ConfirmBox("请先投骰子！");
            c.show();
            return;
        }
//根据点数，找到下一个目的地
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
        MapBuilder mapBuilder = new MapBuilder("assets/1.json", new Vector2i(0,0));
        GameMap gameMap= mapBuilder.build(new Vector2i(300,300));
        this.gameMap = gameMap;
        gameScene.addComponent(gameMap.mapComponent);

    }
    int[] currentChoose;
    @Override
    public void setActorChoose(int[] currentChoose)
    {
        this.currentChoose=currentChoose;
        for(int i=0;i<playerNum;i++)
        {
            players[i] = new Player(gameMap.firstTile[i], currentChoose[i]);
            gameScene.addComponent(players[i].playerComponent );
        }
        this.currentPlayer = players[currentChoose[0]];
    }
    @Override
    public int[] getActorChoose()
    {
        return currentChoose;
    }


}

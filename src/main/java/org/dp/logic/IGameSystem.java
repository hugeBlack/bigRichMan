package org.dp.logic;

import org.dp.assets.PlayerInfo;
import org.dp.scene.GameScene;

// 外观模式！现在实现了掷色子、玩家移动，有需要的功能自己加
public interface IGameSystem {

    // 当前状态下是否需要掷色子？
    boolean canRollDice();

    // 如果可以的话就可以调用掷色子函数
    int getNextDicePoint();

    GameScene getScene();
    void init();
    void performPlayerMove();

    void setPlayerNum(int i);

    Integer getPlayerNum();
    void setActorChoose(int[] currentChoose);
    int[] getActorChoose();
    void setCurrentPlayer(int type);
    void setNextDicePoint(int i);
    PlayerInfo getPlayerInfo();
}

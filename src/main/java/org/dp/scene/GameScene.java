package org.dp.scene;

import org.dp.assets.*;

import org.dp.components.*;

import org.dp.event.ButtonClickEvent;
import org.dp.event.DiceChosenEvent;
import org.dp.event.DiceRolledEvent;
import org.dp.event.GameEventBus;
import org.dp.logic.GameSystem;
import org.dp.utils.Vector2i;
import org.dp.view.Component;
import org.dp.view.ComponentObserver;
import org.dp.view.ConfirmBox;
import org.dp.view.GameButton;
import org.dp.view.Playground;
import org.dp.view.events.ComponentEvent;

import java.awt.*;

public class GameScene extends Scene {
    // 这是个例子，图上有一个玩家，点一下玩家图像就左右移动，然后发出一个PlayerClicked事件，被observer接收，然后clickCount+1
    // private int clickCount;
    private int gamedays;    //记录游戏天数
    private TitleSceneAssets assets = (TitleSceneAssets)AssetFactory.getAsset("titleSceneAssets");
    private Font font = ((FontLib)AssetFactory.getAsset("fontLib")).testFont;//编辑字体
    private static int currentPlayer=0;
    PlayerPicture playerPicture = (PlayerPicture) AssetFactory.getAsset("player");
    PlayerInfo playerInfo = (PlayerInfo) AssetFactory.getAsset("playerInfo");
    private void alterCurrentPlayer(){
        // 切换为下一个人
        currentPlayer=(currentPlayer+1)%GameSystem.get().getPlayerNum();
        GameSystem.get().setCurrentPlayer(currentPlayer);
        // 清除上一个人的策略
        removeChildren((Component) diceStrategy);
        // 获取新的策略
        diceStrategy = PlayerInfo.playerInfos[currentPlayer].strategy;
        if (diceStrategy == null) {
            GameSystem.get().getPlayerInfo().updatePlayerInfo(currentPlayer,"strategy",1);
            diceStrategy = PlayerInfo.playerInfos[currentPlayer].strategy;
        }
        // 应用新的策略
        diceStrategy.setStatus(currentPlayer);
        addComponent((Component) diceStrategy);
        // 修改游戏天数
        if(currentPlayer==0)
            gamedays++;
    }
    private PlayerInfoComponent playerInfoComponent;
    private PlayerCardComponent playerCardComponent = new PlayerCardComponent();

    DiceStrategy diceStrategy;

    public GameScene(){
        // 每个 Player 持有自己的策略对象，Static 维护所有人的，直接拿
        GameSystem.get().getPlayerInfo().updatePlayerInfo(currentPlayer,"strategy",1);
        diceStrategy = PlayerInfo.playerInfos[currentPlayer].strategy;
        addComponent((Component) diceStrategy);
        // 第一个开始的人要先选卡牌
        playerCardComponent.show();
        gamedays = 1;//从第一天开始

        playerInfoComponent = new PlayerInfoComponent();

//        GameButton moveButton = new GameButton(new Vector2i(500,400), new Vector2i(300,50), "Move Player");
        GameButton buttonOpenConfirmBox = new GameButton(new Vector2i(500,500), new Vector2i(300,50), "Open ConfirmBox");
        GameButton storeButton = new GameButton(new Vector2i(1250,700), new Vector2i(300,50), "商店");
        GameButton backButton = new GameButton(new Vector2i(1250,800), new Vector2i(300,50), "退出游戏");

        buttonOpenConfirmBox.registerObserver(new ComponentObserver() {
            @Override
            public void onEvent(ComponentEvent e) {
                if(e instanceof ButtonClickEvent){
                    playerInfoComponent.show();
                }
            }
        });
        // 改成事件驱动的了
        GameEventBus.get().registerListener(DiceRolledEvent.class, event -> {
            // 处理扔骰子结束的逻辑
            GameSystem.get().performPlayerMove();
            playerCardComponent.show();
        });
        GameEventBus.get().registerListener(DiceChosenEvent.class, event ->{
            alterCurrentPlayer();
        });
//        moveButton.registerObserver(new ComponentObserver() {//人物移动，以及当前角色更改
//            @Override
//            public void onEvent(ComponentEvent e) {
//                if(e instanceof ButtonClickEvent){
//                    GameSystem.get().performPlayerMove();
//
//                    playerCardComponent.show();
//                    alterCurrentPlayer();
//                }
//            }
//        });

        addComponent(buttonOpenConfirmBox);
//        addComponent(moveButton);

        //商店按钮
        storeButton.registerObserver(new ComponentObserver() {
            @Override
            public void onEvent(ComponentEvent e) {
                Playground.get().switchScene(new StoreScene());//切换到商店场景
            }
        });
        addComponent(storeButton);

        backButton.registerObserver(new ComponentObserver() {
            @Override
            public void onEvent(ComponentEvent e) {
                Playground.get().switchScene(new TitleScene());
            }
        });
        addComponent(backButton);
    }
    @Override
    public void drawMe(Graphics graphics) {
        // 如何画一个组件：
        // 先获取左上角的绝对坐标，计算要画的位置，然后在graphics上画对应的图形
        Vector2i p = getAbsPosition();
        //显示各个角色信息
        Font font2 = ((FontLib) AssetFactory.getAsset("fontLib")).placePriceFont;
        graphics.drawImage(assets.shanghai, p.x, p.y, null);
        graphics.setFont(font2);
        Vector2i drawPoint = p.add(20,1000);
        String text = "游戏天数：" + gamedays + "天";
        graphics.drawString(text, 1400,100);
        //画右侧的四个角色
        for (int i = 0; i < GameSystem.get().getPlayerNum(); i++) {
            Vector2i infoPoint = p.add(1300, 200 + i * 150);
            graphics.drawImage(playerPicture.img[GameSystem.get(). getActorChoose()[i]], infoPoint.x, infoPoint.y, 100, 100, null);
        }
    }
    public static int GetCurrentPlayerNum(){
        return currentPlayer;
    }
}

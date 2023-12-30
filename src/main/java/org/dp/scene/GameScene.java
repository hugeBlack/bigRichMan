package org.dp.scene;

import org.dp.assets.*;

import org.dp.components.*;

import org.dp.event.*;

import org.dp.event.LandedEvent.EventTileListener;
import org.dp.event.LandedEvent.HospitalTileListener;
import org.dp.event.LandedEvent.PlaceTileListener;
import org.dp.event.LandedEvent.PlayerLandedOnEventTile;
import org.dp.event.LandedEvent.PlayerLandedOnHospitalTile;
import org.dp.event.LandedEvent.PlayerLandedOnPlaceTile;
import org.dp.event.LandedEvent.PlayerLandedOnStartTile;
import org.dp.event.LandedEvent.PlayerLandedOnStoreTile;
import org.dp.event.LandedEvent.StartTileListener;
import org.dp.event.LandedEvent.StoreTileListener;

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
        // 清除骰子效果
        removeChildren((Component) diceStrategy);
        diceStrategy = new OneDiceStrategy(new Vector2i(1400, 230));
        diceStrategy.setStatus(currentPlayer);
        addComponent((Component) diceStrategy);
        // 切换为下一个人
        currentPlayer=(currentPlayer+1)%GameSystem.get().getPlayerNum();
        GameSystem.get().setCurrentPlayer(currentPlayer);

        // 修改游戏天数
        if(currentPlayer==0)
            gamedays++;

        PlayerInfo playerInfos = GameSystem.get().getPlayerInfo();
        if(playerInfos.getPlayerInfo(currentPlayer).inHospital==true){
            playerInfos.reduceForbidDay(currentPlayer);
            ConfirmBox c = new ConfirmBox("您被禁止活动！");
            c.show();
            c.setCallback((type) -> {});
            currentPlayer=(currentPlayer+1)%GameSystem.get().getPlayerNum();
        }
    }

    private PlayerInfoComponent playerInfoComponent;
    private PlayerCardComponent playerCardComponent = new PlayerCardComponent();

    DiceStrategy diceStrategy;

    public GameScene() {
        // 注册监听器
        GameEventBus.get().registerListener(PlayerLandedOnPlaceTile.class, new PlaceTileListener());
        GameEventBus.get().registerListener(PlayerLandedOnStartTile.class, new StartTileListener());
        GameEventBus.get().registerListener(PlayerLandedOnEventTile.class, new EventTileListener());
        GameEventBus.get().registerListener(PlayerLandedOnHospitalTile.class, new HospitalTileListener());
        GameEventBus.get().registerListener(PlayerLandedOnStoreTile.class, new StoreTileListener());

        // 第一个人好像必须要这样
        diceStrategy = new OneDiceStrategy(new Vector2i(1400, 230));
        addComponent((Component) diceStrategy);

        gamedays = 1;//从第一天开始

        playerInfoComponent = new PlayerInfoComponent();

        GameButton buttonOpenConfirmBox = new GameButton(new Vector2i(500, 500), new Vector2i(300, 50), "Player Info");
        GameButton storeButton = new GameButton(new Vector2i(1250, 700), new Vector2i(300, 50), "结束回合");
        GameButton backButton = new GameButton(new Vector2i(1250, 800), new Vector2i(300, 50), "退出游戏");
        buttonOpenConfirmBox.registerObserver(new ComponentObserver() {
            @Override
            public void onEvent(ComponentEvent e) {
                if (e instanceof ButtonClickEvent) {
                    playerInfoComponent.show();
                }
            }
        });
// 事件流程是:
// DiceChosen - 更新当前玩家骰子策略
// DiceRolled - 玩家移动、切换玩家、触发RoundStart
// RoundStart - 显示手牌
// 其中DiceChosen只负责更新当前玩家的策略组件,不涉及玩家切换。
// 而玩家切换是发生在DiceRolled事件中的alterCurrentPlayer方法中。
        // RoundStart事件监听器
        // 作用:显示玩家的手牌组件
        GameEventBus.get().registerListener(RoundStartEvent.class, event -> {
            playerCardComponent.show();
        });
        // DiceRolled事件监听器
        // 作用:执行玩家移动逻辑,切换到下一个玩家,触发RoundStart事件
        GameEventBus.get().registerListener(DiceRolledEvent.class, event -> {
            System.out.println("Player"+currentPlayer+"DiceRolled");
            // 执行玩家移动逻辑
            GameSystem.get().performPlayerMove();
//            // 切换到下一个玩家
//            alterCurrentPlayer();
//            // 触发RoundStart事件,为下个玩家初始化
//            GameEventBus.get().emitEvent(new RoundStartEvent());
        });
        // DiceChosen事件监听器
        // 作用:更新当前玩家的骰子策略组件
        GameEventBus.get().registerListener(DiceChosenEvent.class, event ->{
            // 清除上一个玩家的骰子策略组件
            removeChildren((Component) diceStrategy);
            // 获取当前玩家的骰子策略
            diceStrategy = PlayerInfo.playerInfos[GameSystem.get().getActorChoose()[currentPlayer]].strategy;
            // 如果为空则设置为默认策略1
            if (diceStrategy == null) {
                diceStrategy = new OneDiceStrategy(new Vector2i(1400, 230));
            }
            System.out.println(diceStrategy);
            // 设置骰子策略组件的状态为当前玩家
            diceStrategy.setStatus(currentPlayer);
            // 添加新的骰子策略组件
            addComponent((Component) diceStrategy);
        });
        addComponent(buttonOpenConfirmBox);

        // 商店按钮
        storeButton.registerObserver(new ComponentObserver() {
            @Override
            public void onEvent(ComponentEvent e) {

                // 切换到下一个玩家
                alterCurrentPlayer();
                // 触发RoundStart事件,为下个玩家初始化
                GameEventBus.get().emitEvent(new RoundStartEvent());
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
        // 显示各个角色信息
        Font font2 = ((FontLib) AssetFactory.getAsset("fontLib")).placePriceFont;
        graphics.drawImage(assets.shanghai, p.x, p.y, null);
        graphics.setFont(font2);
        String text = "游戏天数：" + gamedays + "天";
        graphics.drawString(text, 1400, 100);
        // 画右侧的四个角色
        for (int i = 0; i < GameSystem.get().getPlayerNum(); i++) {
            Vector2i infoPoint = p.add(1300, 200 + i * 150);
            graphics.drawImage(playerPicture.img[GameSystem.get().getActorChoose()[i]], infoPoint.x, infoPoint.y, 100,
                    100, null);
        }
    }

    public static int GetCurrentPlayerNum() {
        return currentPlayer;
    }

    
}

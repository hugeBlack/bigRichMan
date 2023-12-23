package org.dp.scene;

import org.dp.assets.*;
import org.dp.components.DiceStrategy;
import org.dp.components.ThreeDiceStrategy;
import org.dp.event.ButtonClickEvent;
import org.dp.logic.GameSystem;
import org.dp.utils.Vector2i;
import org.dp.view.Component;
import org.dp.view.ComponentObserver;
import org.dp.view.ConfirmBox;
import org.dp.view.GameButton;
import org.dp.view.Playground;
import org.dp.view.events.ComponentEvent;
import org.dp.view.events.ConfirmBoxEvent;

import java.awt.*;
import java.util.Arrays;

public class GameScene extends Scene {
    // 这是个例子，图上有一个玩家，点一下玩家图像就左右移动，然后发出一个PlayerClicked事件，被observer接收，然后clickCount+1
    //private int clickCount;
    private int gamedays;    //记录游戏天数
    private TitleSceneAssets assets = (TitleSceneAssets)AssetFactory.getAsset("titleSceneAssets");
    private Font font = ((FontLib)AssetFactory.getAsset("fontLib")).testFont;//编辑字体
    private int currentPlayer=0;
    PlayerPicture playerPicture = (PlayerPicture) AssetFactory.getAsset("player");
    PlayerInfo playerInfo = (PlayerInfo) AssetFactory.getAsset("playerInfo");
    private void alterCurrentPlayer(){
        currentPlayer=(currentPlayer+1)%GameSystem.get().getPlayerNum();
        diceStrategy.setStatus(currentPlayer);
        GameSystem.get().setCurrentPlayer(currentPlayer);
        //修改游戏天数
if(currentPlayer==0)
    gamedays++;
    }

    DiceStrategy diceStrategy;
    public GameScene(){
        // 一个TestComponent作为子组件
        //TestComponent testComponent = new TestComponent();
        //addComponent(testComponent);
        diceStrategy = new ThreeDiceStrategy(new Vector2i(1400, 230));
        addComponent((Component) diceStrategy);
        //testComponent.registerObserver(new ComponentObserver() {//通过component进行监视
       //     @Override
         //   public void onEvent(ComponentEvent e) {
          //      clickCount++;
          //  }
        //});

        gamedays = 1;//从第一天开始
        ConfirmBox c = new ConfirmBox("这是一个确认框哦" );
        // 添加监视者来获取用户的选择
        c.registerObserver(new ComponentObserver() {
            @Override
            public void onEvent(ComponentEvent e) {
                if(e instanceof ConfirmBoxEvent){
                    ConfirmBoxEvent e1 = (ConfirmBoxEvent) e;
                    if(e1.getResult()){
                        System.out.println("OK！");
                    }else{
                        System.out.println("Cancel！");
                    }
                }
            }
        });

        GameButton moveButton = new GameButton(new Vector2i(500,400), new Vector2i(300,50), "Move Player");
        GameButton buttonOpenConfirmBox = new GameButton(new Vector2i(500,500), new Vector2i(300,50), "Open ConfirmBox");
        GameButton backButton = new GameButton(new Vector2i(1250,800), new Vector2i(300,50), "Back");
        buttonOpenConfirmBox.registerObserver(new ComponentObserver() {
            @Override
            public void onEvent(ComponentEvent e) {
                if(e instanceof ButtonClickEvent){
                    c.show();
                }

            }
        });
        moveButton.registerObserver(new ComponentObserver() {//人物移动，以及当前角色更改
            @Override
            public void onEvent(ComponentEvent e) {
                if(e instanceof ButtonClickEvent){
                    GameSystem.get().performPlayerMove();
                    c.show();
                    alterCurrentPlayer();
                }
            }
        });

        addComponent(buttonOpenConfirmBox);
        addComponent(moveButton);

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
        for (int i = 0; i < GameSystem.get().getPlayerNum(); i++) {
            Vector2i infoPoint = p.add(1300, 200 + i * 150);
            graphics.drawImage(playerPicture.img[GameSystem.get(). getActorChoose()[i]], infoPoint.x, infoPoint.y, 100, 100, null);
        }
    }
}

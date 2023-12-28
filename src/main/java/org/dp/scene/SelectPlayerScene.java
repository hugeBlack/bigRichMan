package org.dp.scene;

import org.dp.assets.*;
import org.dp.event.*;
import org.dp.logic.GameSystem;
import org.dp.utils.Vector2i;
import org.dp.view.ComponentObserver;
import org.dp.view.GameButton;
import org.dp.view.Playground;
import org.dp.view.events.ComponentEvent;

import javax.swing.*;
import java.awt.*;

public class SelectPlayerScene extends Scene {
    private SelectPlayerElementFactory elementFactory = new DefaultSelectPlayerElementFactory();  //抽象工厂模式
    private TitleSceneAssets assets = (TitleSceneAssets)AssetFactory.getAsset("titleSceneAssets");
    PlayerPicture playerPicture = (PlayerPicture) AssetFactory.getAsset("player");
    PlayerInfo playerInfo = (PlayerInfo) AssetFactory.getAsset("playerInfo");
    private int actorSize;//可供选择的角色的数量
    //定义一个长度为4，每个值都是-1的int型数组
    //数组中的值表示玩家选择的角色，-1表示未选择
     private int[] currentChoose=new int[]{-1,-1,-1,-1};
    private GameButton[] leftButton=new GameButton[4];
    private GameButton[] rightButton=new GameButton[4];
    private Font font = ((FontLib) AssetFactory.getAsset("fontLib")).testFont;
    private GameButton nextButton;
    private GameButton prevButton;

    private PlayersNumScene playerNumScene;
    private static boolean contains(int[] arr, int num) {
        int count=0;
        for (int i : arr) {
            if (i == num) {
                count++;
            }
        }
        if(count>=2)
            return true;
        return false;
    }


    public void setPlayerNumScene(PlayersNumScene playerNumScene) {
        this.playerNumScene = playerNumScene;
    }

    public SelectPlayerScene() {

        prevButton=new GameButton(new Vector2i(1150, 800), new Vector2i(100,60), "返回");
        nextButton=new GameButton(new Vector2i(1350, 800), new Vector2i(100,60), "下一步");

        nextButton.registerObserver(new ComponentObserver() {
            @Override
            public void onEvent(ComponentEvent e) {
                if (e instanceof ButtonClickEvent) {
                    GameSystem.get().setActorChoose(currentChoose);
                    Playground.get().switchScene(GameSystem.get().getScene());
                    GameEventBus.get().emitEvent((IGameEvent) new RoundStartEvent());
                }
            }
        });
        prevButton.registerObserver(new ComponentObserver() {
            @Override
            public void onEvent(ComponentEvent e) {
                if (e instanceof ButtonClickEvent) {
                    Playground.get().switchScene(new PlayersNumScene());
                }
            }
        });
        addComponent(nextButton);
        addComponent(prevButton);
        actorSize = playerPicture.img.length;

        int playerNum = 0;
        for(int i = 0;i < GameSystem.get().getPlayerNum();i++)
            playerNum ++;
        int maxPlayerNum = 4;
        //初始化选择的角色
        for(int i=0;i<GameSystem.get().getPlayerNum();i++)
        {
            currentChoose[i]=i;
            //此处改用抽象工厂模式来创建左右按钮
            leftButton[i] = elementFactory.createLeftButton(new Vector2i(80 + i * 300 + (maxPlayerNum - playerNum) * 200, 650));
            rightButton[i] = elementFactory.createRightButton(new Vector2i(200 + i * 300 + (maxPlayerNum - playerNum) * 200, 650));


            addComponent(leftButton[i]);
            addComponent(rightButton[i]);
        }
        leftButton[0].registerObserver(new ComponentObserver() {//如果单击“向左”按钮
            @Override
            public void onEvent(ComponentEvent e) {
                if (e instanceof ButtonClickEvent) {
                    currentChoose[0] = (currentChoose[0] - 1 + actorSize) % actorSize;
                    while(contains(currentChoose,currentChoose[0]))
                    currentChoose[0] = (currentChoose[0] - 1 + actorSize) % actorSize;
                }
            }
        });
        rightButton[0].registerObserver(new ComponentObserver() {//如果单击“向右”按钮
            @Override
            public void onEvent(ComponentEvent e) {
                if (e instanceof ButtonClickEvent) {
                    currentChoose[0] = (currentChoose[0] + 1 + actorSize) % actorSize;
                    while(contains(currentChoose,currentChoose[0]))
                    currentChoose[0] = (currentChoose[0] + 1) % actorSize;
                }
            }
        });
        if(GameSystem.get().getPlayerNum()>1)
        {
            leftButton[1].registerObserver(new ComponentObserver() {//如果单击“向左”按钮
                @Override
                public void onEvent(ComponentEvent e) {
                    if (e instanceof ButtonClickEvent) {
                        currentChoose[1] = (currentChoose[1] - 1 + actorSize) % actorSize;
                        while(contains(currentChoose,currentChoose[1]))
                        currentChoose[1] = (currentChoose[1] - 1 + actorSize) % actorSize;
                    }
                }
            });
            rightButton[1].registerObserver(new ComponentObserver() {//如果单击“向右”按钮
                @Override
                public void onEvent(ComponentEvent e) {
                    if (e instanceof ButtonClickEvent) {
                        currentChoose[1] = (currentChoose[1] + 1 + actorSize) % actorSize;
                        while(contains(currentChoose,currentChoose[1]))
                        currentChoose[1] = (currentChoose[1] + 1) % actorSize;
                    }
                }
            });
        }
        if(GameSystem.get().getPlayerNum()>2) {
            leftButton[2].registerObserver(new ComponentObserver() {//如果单击“向左”按钮
                @Override
                public void onEvent(ComponentEvent e) {
                    if (e instanceof ButtonClickEvent) {
                        currentChoose[2] = (currentChoose[2] - 1 + actorSize) % actorSize;
                        while(contains(currentChoose,currentChoose[2]))
                        currentChoose[2] = (currentChoose[2] - 1 + actorSize) % actorSize;
                    }
                }
            });
            rightButton[2].registerObserver(new ComponentObserver() {//如果单击“向右”按钮
                @Override
                public void onEvent(ComponentEvent e) {
                    if (e instanceof ButtonClickEvent) {
                        currentChoose[2] = (currentChoose[2] + 1 + actorSize) % actorSize;
                        while(contains(currentChoose,currentChoose[2]))
                        currentChoose[2] = (currentChoose[2] + 1) % actorSize;
                    }
                }
            });
        }
        if(GameSystem.get().getPlayerNum()>3) {
            leftButton[3].registerObserver(new ComponentObserver() {//如果单击“向左”按钮
                @Override
                public void onEvent(ComponentEvent e) {
                    if (e instanceof ButtonClickEvent) {
                        currentChoose[3] = (currentChoose[3] - 1 + actorSize) % actorSize;
                        while(contains(currentChoose,currentChoose[3]))
                        currentChoose[3] = (currentChoose[3] - 1 + actorSize) % actorSize;
                    }
                }
            });
            rightButton[3].registerObserver(new ComponentObserver() {//如果单击“向右”按钮
                @Override
                public void onEvent(ComponentEvent e) {
                    if (e instanceof ButtonClickEvent) {
                        currentChoose[3] = (currentChoose[3] + 1 + actorSize) % actorSize;
                        while(contains(currentChoose,currentChoose[3]))
                        currentChoose[3] = (currentChoose[3] + 1) % actorSize;
                    }
                }
            });
        }

    }

    @Override
    public void drawMe(Graphics graphics) {
        // 如何画一个组件：
        // 先获取左上角的绝对坐标，计算要画的位置，然后在graphics上画对应的图形
        Vector2i p = getAbsPosition();
        Vector2i drawPoint = p.add(100, 200);
        graphics.setFont(font);
        graphics.drawImage(assets.background, p.x, p.y, null);
        graphics.drawString("选择角色", drawPoint.x, drawPoint.y);
        Font font2 = ((FontLib) AssetFactory.getAsset("fontLib")).placePriceFont;
        graphics.setFont(font2);
        int playerNum = 0;
        for(int i = 0;i < GameSystem.get().getPlayerNum();i++)
            playerNum ++;
        int maxPlayerNum = 4;
        for (int i = 0; i < GameSystem.get().getPlayerNum(); i++) {
            Vector2i infoPoint = p.add(100 + i * 300 + (maxPlayerNum - playerNum) * 200, 300);
            graphics.drawString("人物信息", infoPoint.x, infoPoint.y+75);
            graphics.drawString("玩家" + (i + 1), infoPoint.x, infoPoint.y +150);
            graphics.drawString("姓名：" + playerInfo.getPlayerInfo(currentChoose[i]).defaultName, infoPoint.x, infoPoint.y + 200);
            graphics.drawString("金币数：" + playerInfo.getPlayerInfo(currentChoose[i]).coinNum, infoPoint.x, infoPoint.y + 250);
            graphics.drawString("汽车卡牌数：" + playerInfo.getPlayerInfo(currentChoose[i]).cardCarNum, infoPoint.x, infoPoint.y + 300);
            graphics.drawString("幸运卡牌数：" + playerInfo.getPlayerInfo(currentChoose[i]).cardLuckNum, infoPoint.x, infoPoint.y + 350);

            graphics.drawImage(playerPicture.img[currentChoose[i]], infoPoint.x, infoPoint.y - 50, 100, 100, null);
        }
    }
}

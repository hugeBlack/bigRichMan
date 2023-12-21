package org.dp.scene;

import org.dp.assets.AssetFactory;
import org.dp.assets.FontLib;
import org.dp.assets.TitleSceneAssets;
import org.dp.event.ButtonClickEvent;
import org.dp.logic.GameSystem;
import org.dp.logic.IGameSystem;
import org.dp.utils.Vector2i;
import org.dp.view.ComponentObserver;
import org.dp.view.GameButton;
import org.dp.view.Playground;
import org.dp.view.events.ComponentEvent;

import java.awt.*;
import java.util.LinkedList;
import java.util.Vector;

public class PlayersNumScene extends Scene{
    private Font font = ((FontLib)AssetFactory.getAsset("fontLib")).testFont;//编辑字体
    private GameButton nextButton;
    private GameButton prevButton;
    private Vector<GameButton> numButtons=new Vector<>();
    private int playerNum;
    private SelectPlayerScene selectPlayerScene;

    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }

    public PlayersNumScene()
    {
nextButton=new GameButton(new Vector2i(1400, 700), new Vector2i(100,100), "下一步");
prevButton=new GameButton(new Vector2i(300, 700), new Vector2i(100,100), "返回");

        nextButton.registerObserver(new ComponentObserver() {
            @Override
            public void onEvent(ComponentEvent e) {
                if(e instanceof ButtonClickEvent){
                    IGameSystem gameSystem = GameSystem.get();
                    gameSystem.init();
                    gameSystem.setPlayerNum(playerNum);

                    if(selectPlayerScene==null) {
                        selectPlayerScene = new SelectPlayerScene();
                        selectPlayerScene.setPlayerNumScene(PlayersNumScene.this);
                    }
                    Playground.get().switchScene(selectPlayerScene);
                }
            }
        });
        prevButton.registerObserver(new ComponentObserver() {
            @Override
            public void onEvent(ComponentEvent e) {
                if(e instanceof ButtonClickEvent){
                    Playground.get().switchScene(new TitleScene());
                }
            }
        });
        for(int i=1;i<=4;i++)
        {
            GameButton button=new GameButton(new Vector2i(50+300*i, 300), new Vector2i(200,400), i+"");
            button.setBackgroundColor(Color.white);
            button.setTextColor(Color.BLACK);
            button.registerObserver(new ComponentObserver() {
                @Override
                public void onEvent(ComponentEvent e) {
                    if(e instanceof ButtonClickEvent){
                        for(GameButton numButton:numButtons)
                        {
                            numButton.setTextColor(Color.BLACK);
                        }
                        button.setTextColor(Color.BLUE);
                        playerNum=Integer.parseInt(button.getTitle()) ;
                    }
                }
            });
            numButtons.add(button);
            addComponent(button);
        }
        addComponent(nextButton);
        addComponent(prevButton);
        numButtons.get(0).setTextColor(Color.blue);
        playerNum=1;
    }
    @Override
    public void drawMe(Graphics graphics) {
        // 如何画一个组件：
        // 先获取左上角的绝对坐标，计算要画的位置，然后在graphics上画对应的图形
        Vector2i p = getAbsPosition();
        Vector2i drawPoint = p.add(100,200);
        graphics.setFont(font);
        graphics.drawString("选择玩家人数", drawPoint.x, drawPoint.y);
    }

}

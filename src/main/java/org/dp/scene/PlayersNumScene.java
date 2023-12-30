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
import java.util.Vector;

public class PlayersNumScene extends Scene{
    private PlayerNumButtonFactory playerNumButtonFactory = new DefaultPlayerNumButtonFactory();   //抽象工厂模式
    private TitleSceneAssets assets = (TitleSceneAssets)AssetFactory.getAsset("titleSceneAssets");
    private Font font = ((FontLib)AssetFactory.getAsset("fontLib")).testFont;//编辑字体
    private GameButton nextButton;
    private GameButton prevButton;
    private Vector<GameButton> numButtons=new Vector<>();
    private int playerNum;
    //private SelectPlayerScene selectPlayerScene;

    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }

    public PlayersNumScene()
    {
        prevButton=new GameButton(new Vector2i(1100, 700), new Vector2i(100,80), "返回");
        nextButton=new GameButton(new Vector2i(1300, 700), new Vector2i(100,80), "下一步");

        nextButton.registerObserver(new ComponentObserver() {   //“下一步”按钮的监听器
            @Override
            public void onEvent(ComponentEvent e) {
                if(e instanceof ButtonClickEvent){
                    IGameSystem gameSystem = GameSystem.get();
                    gameSystem.init();
                    gameSystem.setPlayerNum(playerNum);
                    Playground.get().switchScene(new SelectPlayerScene());
                }
            }
        });
        prevButton.registerObserver(new ComponentObserver() {    //“返回”按钮监听器
            @Override
            public void onEvent(ComponentEvent e) {
                if(e instanceof ButtonClickEvent){
                    Playground.get().switchScene(new TitleScene());     //返回主页面
                }
            }
        });
        for(int i=1;i<=4;i++)
        {
            GameButton button= playerNumButtonFactory.createPlayerButton(new Vector2i((i-1)*300+200,400),i+"");
            //此处改用抽象工厂模式来创建玩家人数按钮
            //button.setBackgroundColor(Color.white);
            //button.setTextColor(Color.BLACK);
            //Font playerNumberFont = ((FontLib)AssetFactory.getAsset("fontLib")).playerNumberButton;//编辑字体
            //button.setFont(playerNumberFont);     //主要是为了设置字体大小
            button.registerObserver(new ComponentObserver() {    //人数按钮监听器
                @Override
                public void onEvent(ComponentEvent e) {
                    if(e instanceof ButtonClickEvent){
                        for(GameButton numButton:numButtons)
                        {
                            numButton.setTextColor(Color.BLACK);
                            numButton.setBorderColor(Color.BLACK);
                        }
                        button.setTextColor(Color.BLUE);
                        button.setBorderColor(Color.BLUE);
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
        numButtons.get(0).setBorderColor(Color.blue);
        playerNum=1;
    }
    @Override
    public void drawMe(Graphics graphics) {
        // 如何画一个组件：
        // 先获取左上角的绝对坐标，计算要画的位置，然后在graphics上画对应的图形
        Vector2i p = getAbsPosition();
        Vector2i drawPoint = p.add(100,220);
        graphics.setFont(font);
        graphics.drawImage(assets.background, p.x, p.y, null);
        graphics.drawString("请选择玩家人数", drawPoint.x, drawPoint.y);
    }

}

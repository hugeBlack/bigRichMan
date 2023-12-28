package org.dp.scene;

import org.dp.assets.AssetFactory;
import org.dp.assets.FontLib;
import org.dp.assets.TitleSceneAssets;
import org.dp.logic.GameSystem;
import org.dp.logic.IGameSystem;
import org.dp.view.GameButton;
import org.dp.utils.Vector2i;
import org.dp.view.ComponentObserver;
import org.dp.view.Playground;
import org.dp.view.events.ComponentEvent;

import java.awt.*;

public class TitleScene extends Scene {

    private TitleSceneAssets assets = (TitleSceneAssets)AssetFactory.getAsset("titleSceneAssets");//根据名称获取资产
    private FontLib fontLib = ((FontLib) AssetFactory.getAsset("fontLib"));//获取字体
    //下面这些要等构造函数再进行初始化
    private boolean isInitialized = false;
    private Vector2i titleRelativePos = null;//标题的位置
    private int ascent = 0;//基线到最高字符顶部的距离

    private String title = "大富翁";


    public TitleScene() {
        //创建组件，添加组件，定义组件的监视器
        GameButton startButton = new GameButton(new Vector2i(700, 500), new Vector2i(200, 50), "开始游戏");
        addComponent(startButton);
        startButton.registerObserver(new ComponentObserver() {
            @Override
            public void onEvent(ComponentEvent e) {
               /* IGameSystem gameSystem = GameSystem.get();
                gameSystem.init();
                Playground.get().switchScene(gameSystem.getScene());*/
                Playground.get().switchScene(new PlayersNumScene());
            }
        });

        GameButton saveButton = new GameButton(new Vector2i(700, 600), new Vector2i(200, 50), "读取存档");
        addComponent(saveButton);
        saveButton.registerObserver(new ComponentObserver() {
            @Override
            public void onEvent(ComponentEvent e) {
                System.out.println("读取存档!");
            }
        });
    }

    @Override
    public void drawMe(Graphics graphics) {
        graphics.setFont(fontLib.testFont);
        Vector2i p = getAbsPosition();
        if(!isInitialized){
            int titleWidth = graphics.getFontMetrics().stringWidth(title);
            int titleHeight = graphics.getFontMetrics().getHeight();
            ascent = graphics.getFontMetrics().getAscent();
            titleRelativePos = new Vector2i(-titleWidth / 2, titleHeight);
            isInitialized = true;

        }

        graphics.drawImage(assets.background, p.x, p.y, null);
        Vector2i drawPoint = p.add(800 + titleRelativePos.x, 50 + titleRelativePos.y);
        graphics.drawString(title, drawPoint.x, drawPoint.y);
    }
}

package org.dp.scene;

import org.dp.assets.AssetFactory;
import org.dp.assets.FontLib;
import org.dp.event.ButtonClickEvent;
import org.dp.logic.GameSystem;
import org.dp.logic.IGameSystem;
import org.dp.utils.Vector2i;
import org.dp.view.ComponentObserver;
import org.dp.view.GameButton;
import org.dp.view.Playground;
import org.dp.view.events.ComponentEvent;

import java.awt.*;

public class SelectPlayerScene extends Scene {
    private Font font = ((FontLib) AssetFactory.getAsset("fontLib")).testFont;
    private GameButton nextButton;
    private GameButton prevButton;
    private PlayersNumScene playerNumScene;

    public void setPlayerNumScene(PlayersNumScene playerNumScene) {
        this.playerNumScene = playerNumScene;
    }

    public SelectPlayerScene() {
    nextButton=new GameButton(new Vector2i(1400, 700), new Vector2i(100,100), "下一步");
    prevButton=new GameButton(new Vector2i(300, 700), new Vector2i(100,100), "返回");

    nextButton.registerObserver(new ComponentObserver() {
        @Override
        public void onEvent(ComponentEvent e) {
            if(e instanceof ButtonClickEvent){
                Playground.get().switchScene(GameSystem.get().getScene());
            }
        }
    });
    prevButton.registerObserver(new ComponentObserver() {
        @Override
        public void onEvent(ComponentEvent e) {
            if(e instanceof ButtonClickEvent){
                Playground.get().switchScene(playerNumScene);
            }
        }
    });
        addComponent(nextButton);
        addComponent(prevButton);

}
    @Override
    public void drawMe(Graphics graphics) {
        // 如何画一个组件：
        // 先获取左上角的绝对坐标，计算要画的位置，然后在graphics上画对应的图形
        Vector2i p = getAbsPosition();
        Vector2i drawPoint = p.add(100,200);
        graphics.setFont(font);
        graphics.drawString("选择角色", drawPoint.x, drawPoint.y);
    }
}

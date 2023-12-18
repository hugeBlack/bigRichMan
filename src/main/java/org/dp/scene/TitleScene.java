package org.dp.scene;

import org.dp.assets.AssetFactory;
import org.dp.assets.FontLib;
import org.dp.components.GameButton;
import org.dp.utils.Vector2i;
import org.dp.view.ComponentObserver;
import org.dp.view.Playground;
import org.dp.view.events.ComponentEvent;

import java.awt.*;

public class TitleScene extends Scene {

    private FontLib fontLib = ((FontLib) AssetFactory.getAsset("fontLib"));
    private boolean isInitialized = false;
    private Vector2i titleRelativePos = null;
    private int ascent = 0;

    public TitleScene() {
        GameButton startButton = new GameButton(new Vector2i(700, 500), new Vector2i(200, 50), "开始游戏");
        addComponent(startButton);
        startButton.registerObserver(new ComponentObserver() {
            @Override
            public void onEvent(ComponentEvent e) {
                System.out.println("CLICKED!");
                Playground.get().switchScene(new TestScene());
            }
        });
    }

    @Override
    public void drawMe(Graphics graphics) {
        graphics.setFont(fontLib.testFont);
        Vector2i p = getAbsPosition();
        if(!isInitialized){
            int titleWidth = graphics.getFontMetrics().stringWidth("Title");
            int titleHeight = graphics.getFontMetrics().getHeight();
            ascent = graphics.getFontMetrics().getAscent();
            titleRelativePos = new Vector2i(-titleWidth / 2, titleHeight);
            isInitialized = true;

        }


        Vector2i drawPoint = p.add(800 + titleRelativePos.x, 50 + titleRelativePos.y);
        graphics.drawString("大富翁", drawPoint.x, drawPoint.y);
        graphics.drawString("Test",0,100);
    }
}

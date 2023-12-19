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

    private TitleSceneAssets assets = (TitleSceneAssets)AssetFactory.getAsset("titleSceneAssets");
    private FontLib fontLib = ((FontLib) AssetFactory.getAsset("fontLib"));
    private boolean isInitialized = false;
    private Vector2i titleRelativePos = null;
    private int ascent = 0;

    private String title = "大富翁";

    public TitleScene() {
        GameButton startButton = new GameButton(new Vector2i(700, 500), new Vector2i(200, 50), "开始游戏");
        addComponent(startButton);
        startButton.registerObserver(new ComponentObserver() {
            @Override
            public void onEvent(ComponentEvent e) {
                IGameSystem gameSystem = GameSystem.get();
                gameSystem.init();
                Playground.get().switchScene(gameSystem.getScene());
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

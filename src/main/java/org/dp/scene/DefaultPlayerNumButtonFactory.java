package org.dp.scene;

import org.dp.assets.AssetFactory;
import org.dp.assets.FontLib;
import org.dp.utils.Vector2i;
import org.dp.view.GameButton;

import java.awt.*;

//实现具体的工厂类（抽象玩家数量按钮工厂）
public class DefaultPlayerNumButtonFactory implements PlayerNumButtonFactory {
    public GameButton createPlayerButton(Vector2i position,String label){
        GameButton button = new GameButton(position,new Vector2i(200,200),label);
        button.setBackgroundColor(Color.white);
        button.setTextColor(Color.BLACK);
        Font playerNumberFont = ((FontLib)AssetFactory.getAsset("fontLib")).playerNumberButton;//编辑字体
        button.setFont(playerNumberFont);
        return button;
    }
}

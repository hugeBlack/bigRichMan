package org.dp.scene;

import org.dp.assets.AssetFactory;
import org.dp.assets.PlayerInfo;
import org.dp.utils.Vector2i;
import org.dp.view.GameButton;

//实现具体的工厂类（SelectPlayerElementFactory）
public class DefaultSelectPlayerElementFactory implements  SelectPlayerElementFactory{

    @Override
    public GameButton createLeftButton(Vector2i position) {
        return new GameButton(position,new Vector2i(50,50),"<");
    }

    @Override
    public GameButton createRightButton(Vector2i position) {
       return new GameButton(position,new Vector2i(50,50),">");
    }

    @Override
    public PlayerInfo createPlayerInfo() {
        return (PlayerInfo) AssetFactory.getAsset("playInfo");
    }
}

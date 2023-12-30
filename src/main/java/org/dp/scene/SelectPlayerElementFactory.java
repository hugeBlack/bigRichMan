package org.dp.scene;

import org.dp.assets.PlayerInfo;
import org.dp.utils.Vector2i;
import org.dp.view.GameButton;

//抽象工厂模式
//主要用来创建PlayerSelectScene中的一些元素
//DefaultSelectPlayerElementFactory实现具体的工厂类
public interface SelectPlayerElementFactory {
    GameButton createLeftButton(Vector2i position);
    GameButton createRightButton(Vector2i position);
    PlayerInfo createPlayerInfo();
}

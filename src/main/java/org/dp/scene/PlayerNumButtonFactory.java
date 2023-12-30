package org.dp.scene;

import org.dp.utils.Vector2i;
import org.dp.view.GameButton;

//抽象的玩家数量按钮工厂接口
//抽象工厂模式
//在PlayersNumScene中，使用这个工厂来创建玩家按钮
//DefaultPlayerNumButtonFactory类实现了具体的工厂类
public interface PlayerNumButtonFactory {
    GameButton createPlayerButton(Vector2i position,String label);
}

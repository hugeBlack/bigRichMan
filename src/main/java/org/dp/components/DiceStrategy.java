package org.dp.components;

import org.dp.view.events.MouseEvent;

// 【策略模式】的目的是定义一系列的算法,把它们一个个封装起来,并且使它们可相互替换。
//
//        在这个游戏的例子中,我们有不同的骰子掷骰算法:
//
//        单个骰子掷骰
//        两个骰子掷骰
//        三个骰子掷骰
public interface DiceStrategy {
    boolean onMouseEventMe(MouseEvent e);      //鼠标点击触发的事件
    int getDicePointSum();    //获得骰子的随即点数
    void setStatus(int currentPlayer);
}

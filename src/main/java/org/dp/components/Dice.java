package org.dp.components;

import org.dp.view.Component;
import org.dp.view.events.MouseEvent;
//用到了策略模式
//OneDice（投一个骰子）、TwoDice（投两个骰子）、ThreeDice（投三个骰子）三个类都实现这个接口
//根据情况来决定使用不同的骰子投掷策略
public interface Dice {
    public boolean onMouseEventMe(MouseEvent e);      //鼠标点击触发的事件
    public int getDicePointSum();    //获得骰子的随即点数
    void setStatus(int currentPlayer);
}

package org.dp.components;

import org.dp.view.events.MouseEvent;
//用到了策略模式
//OneDice（投一个骰子）、TwoDice（投两个骰子）、ThreeDice（投三个骰子）三个类都实现这个接口
//根据情况来决定使用不同的骰子投掷策略
//使用方法：Dice dice = new OneDice()/TwoDice()/ThreeDice();（根据具体使用情况选定）
public interface DiceStrategy {
    public boolean onMouseEventMe(MouseEvent e);      //鼠标点击触发的事件
    public int getDicePointSum();    //获得骰子的随即点数
    void setStatus(int currentPlayer);
}

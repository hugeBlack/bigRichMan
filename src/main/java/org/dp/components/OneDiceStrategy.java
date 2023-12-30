package org.dp.components;

import org.dp.assets.AssetFactory;
import org.dp.assets.DiceAssets;
import org.dp.event.DiceRolledEvent;
import org.dp.event.GameEventBus;
import org.dp.event.IGameEvent;
import org.dp.logic.GameSystem;
import org.dp.logic.IGameSystem;
import org.dp.scene.GameScene;
import org.dp.utils.AnimationTimeHelper;
import org.dp.utils.Vector2i;
import org.dp.view.Component;
import org.dp.view.ConfirmBox;
import org.dp.view.events.ClickEvent;
import org.dp.view.events.MouseEvent;

import javax.swing.*;
import java.awt.*;

//策略模式
//正常情况下是使用一个骰子
public class OneDiceStrategy extends Component implements DiceStrategy {
    private DiceAssets diceAssets = (DiceAssets) AssetFactory.getAsset("diceAssets");
    private boolean isRolling = false;  //是否翻滚，用同一个变量来做到对两个骰子状态的相同判断
    private int lastPoint = 1;  //记录骰子上一次的点数
    private IGameSystem gameSystem;
    private AnimationTimeHelper animationTimeHelper = null;    //记录动画进度
    private double lastProgress = 0;     //记录？？
    private int status = 0;
    private Vector2i startPos;   //起点

    //构造函数
    public OneDiceStrategy(Vector2i p) {
        super(p,new Vector2i(64,64));   //这里初始化位置及该Component组件框大小，p是传入的位置
        gameSystem = GameSystem.get();
        cursorType = Cursor.HAND_CURSOR;
        startPos = p ;
    }

    public void setStatus(int status){
        this.status =status;
        animationTimeHelper = new AnimationTimeHelper(1000);
        animationTimeHelper.start();
    }

    public int getRandomDicePoint(){    //随机生成骰子的点数
        lastPoint = (int)(Math.random() * 6) + 1;
        return lastPoint;
    }
    @Override
    public int getDicePointSum() {     //返回两个骰子的总点数
        return lastPoint;
    }

    @Override
    public void drawMe(Graphics graphics) {
        double moveProgress = 1;
        //获取当前的relativePosition
        Vector2i relativePosition = getRelativePosition();
        if(!isRolling){     //如果骰子不在翻滚状态，画骰子的图
            if(animationTimeHelper != null)
                moveProgress = animationTimeHelper.getBezierProgress();
            Vector2i dstPosition;
            switch (status){
                case 0:
                    dstPosition=startPos.add(0, 0);
                    //计算当前距离到目标距离的差值
                    break;
                case 1:
                    dstPosition=startPos.add(0, 200);
                    break;
                case 2:
                    dstPosition=startPos.add(0, 400);
                    break;
                case 3:
                default:
                    dstPosition=startPos.add(0, 600);
                    break;
            }
            Vector2i delta = dstPosition.sub(relativePosition);
            setRelativePosition(relativePosition.add((int)(delta.x*moveProgress), (int)(delta.y*moveProgress)));
            Vector2i p = getAbsPosition();
            graphics.drawImage(diceAssets.diceImage[lastPoint - 1], p.x,p.y, 64,64,null);
        }
        else {
            Vector2i p = getAbsPosition();
            double nowProgress = animationTimeHelper.getBezierProgress();
            if (nowProgress > lastProgress + 0.05) {
                lastPoint = getRandomDicePoint();
                lastProgress += 0.05;
            }
            graphics.drawImage(diceAssets.diceImage[lastPoint - 1], p.x, p.y, 64, 64, null);
            if (nowProgress == 1) {
                isRolling = false;
                ConfirmBox c = new ConfirmBox("你骰到了" + getDicePointSum() + "点!");
                GameSystem.get().setNextDicePoint(getDicePointSum());
                c.show();
                c.setCallback((type) -> {
                    GameEventBus.get().emitEvent((IGameEvent) new DiceRolledEvent());
                });
            }
        }
    }

    public boolean onMouseEventMe(MouseEvent e){
        if(!(e instanceof ClickEvent))
            return true;
        if(!gameSystem.canRollDice() || isRolling){
            ConfirmBox c = new ConfirmBox("现在还不能掷骰子哦~");
            c.show();
            return true;
        }else{
            lastProgress = 0.0;
            isRolling = true;
            animationTimeHelper = new AnimationTimeHelper(2000);
            animationTimeHelper.start();
        }
        return true;
    }
    //一个骰子的情况下，我们要实现，玩家点击骰子，就可以进行骰子的旋转
    //因此，首先，我们要写drawMe函数来画出骰子
    //然后，还要能做到鼠标点击骰子之后对应的事件
    //按理来说比较简单
    //直接抄就行

}
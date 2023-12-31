package org.dp.components;

import org.dp.assets.AssetFactory;
import org.dp.assets.DiceAssets;
import org.dp.event.DiceRolledEvent;
import org.dp.event.GameEventBus;
import org.dp.event.IGameEvent;
import org.dp.logic.GameSystem;
import org.dp.logic.IGameSystem;
import org.dp.utils.AnimationTimeHelper;
import org.dp.utils.Vector2i;
import org.dp.view.ConfirmBox;
import org.dp.view.events.ClickEvent;
import org.dp.view.events.MouseEvent;

import javax.swing.*;
import java.awt.*;

// 幸运骰子装饰器
class LuckyDiceDecorator extends DiceDecorator {
    private DiceAssets diceAssets = (DiceAssets) AssetFactory.getAsset("diceAssets");
    private boolean isRolling = false;  //是否翻滚，用同一个变量来做到对两个骰子状态的相同判断
    private int lastPoint = 1;  //记录骰子上一次的点数
    private IGameSystem gameSystem;
    private AnimationTimeHelper animationTimeHelper = null;    //记录动画进度
    private double lastProgress = 0;     //记录？？
    private int status = 0;
    private Vector2i startPos;   //起点

    //构造函数
    public LuckyDiceDecorator(Vector2i p, DiceStrategy decoratedStrategy) {
        super(p,decoratedStrategy);   //这里初始化位置及该Component组件框大小，p是传入的位置
        gameSystem = GameSystem.get();
        cursorType = Cursor.HAND_CURSOR;
        startPos = p ;
    }

    public void setStatus(int status){
        this.status =status;
        animationTimeHelper = new AnimationTimeHelper(1000);
        animationTimeHelper.start();
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
                lastPoint = getDicePointSum();
                lastProgress += 0.05;
            }
            graphics.drawImage(diceAssets.diceImage[lastPoint - 1], p.x, p.y, 64, 64, null);
            if (nowProgress == 1) {
                isRolling = false;
                if (GameSystem.get().getScene().isRoundEnd) {
                    ConfirmBox c = new ConfirmBox("您的回合已结束");
                    c.show();
                    c.setCallback((type) -> {});
                } else {
                    GameSystem.get().getScene().isRoundEnd = true;
                    ConfirmBox c = new ConfirmBox("使用幸运骰子前进" + getDicePointSum() + "点!");
                    GameSystem.get().setNextDicePoint(getDicePointSum());
                    // 恢复为1个骰子
                    GameSystem.get().getPlayerInfo().updatePlayerInfo(GameSystem.get().getPlayerNum(), "strategy", 1);
                    c.show();
                    c.setCallback((type) -> {
                        GameEventBus.get().emitEvent((IGameEvent) new DiceRolledEvent());
                    });
                }
            }
        }
    }

    public boolean onMouseEventMe(MouseEvent e){
        if(!(e instanceof ClickEvent))
            return true;
        if(!gameSystem.canRollDice() || isRolling){
            ConfirmBox c = new ConfirmBox("现在还不能掷骰子哦~");
            c.show();
            c.setCallback((type) -> {});
            return true;
        }else{
            lastProgress = 0.0;
            isRolling = true;
            animationTimeHelper = new AnimationTimeHelper(2000);
            animationTimeHelper.start();
        }
        return true;
    }
    private Integer luckyNumber;

    public void setLuckyNumber(Integer luckyNumber) {
        this.luckyNumber = luckyNumber;
    }

    @Override
    public int getDicePointSum() {
        // 如果设置了幸运数字，则返回它，否则回退到包裹的策略对象的行为
        return luckyNumber != null ? luckyNumber : super.getDicePointSum();
    }

}
package org.dp.components;

import org.dp.assets.AssetFactory;
import org.dp.assets.DiceAssets;
import org.dp.logic.GameSystem;
import org.dp.logic.IGameSystem;
import org.dp.utils.AnimationTimeHelper;
import org.dp.utils.Vector2i;
import org.dp.view.Component;
import org.dp.view.ConfirmBox;
import org.dp.view.events.ClickEvent;
import org.dp.view.events.MouseEvent;

import java.awt.*;

public class Dice extends Component {

    private DiceAssets diceAssets = (DiceAssets) AssetFactory.getAsset("diceAssets");
    private boolean isRolling = false;
    private int lastPoint = 1;
    private IGameSystem gameSystem;
    private AnimationTimeHelper animationTimeHelper = null;
    private double lastProgress = 0;
    private int status=0;
    private Vector2i startPos;//起点
    public Dice(Vector2i p) {
        super(p, new Vector2i(64,64));
        gameSystem = GameSystem.get();
        cursorType = Cursor.HAND_CURSOR;
        startPos= p;
    }
    public void setStatus(int status){
        this.status=status;
        animationTimeHelper = new AnimationTimeHelper(1000);
        animationTimeHelper.start();
    }

    private int getRandomDicePoint() {
        return (int)(Math.random() * 6) + 1;
    }

    @Override
    public void drawMe(Graphics graphics) {
        double moveProgress=1;
        //获取当前的relativePosition
         Vector2i relativePosition = getRelativePosition();
        if(!isRolling) {
            if(animationTimeHelper != null)
            moveProgress = animationTimeHelper.getBezierProgress();
            Vector2i dstPosition;
            switch (status){
                case 0:
                    dstPosition=startPos.add(0, 0);
                    //计算当前距离到目标距离的差值
                    break;
                case 1:
                    dstPosition=startPos.add(100, 0);
                    break;
                case 2:
                    dstPosition=startPos.add(200, 0);
                    break;
                case 3:
                default:
                     dstPosition=startPos.add(300, 0);
                    break;
        }
            Vector2i delta = dstPosition.sub(relativePosition);

            setRelativePosition(relativePosition.add((int)(delta.x*moveProgress), (int)(delta.y*moveProgress)));

            Vector2i p = getAbsPosition();
            graphics.drawImage(diceAssets.diceImage[lastPoint - 1], p.x,p.y, 64,64,null);
        }else{
            Vector2i p = getAbsPosition();
            double nowProgress = animationTimeHelper.getBezierProgress();
            if(nowProgress > lastProgress + 0.05){
                lastPoint = getRandomDicePoint();
                lastProgress += 0.05;
            }
            graphics.drawImage(diceAssets.diceImage[lastPoint - 1], p.x,p.y, 64,64,null);
            if(nowProgress == 1){
                isRolling = false;
                lastPoint = gameSystem.getNextDicePoint();
                ConfirmBox c = new ConfirmBox("你骰到了"+ lastPoint + "点!");
                c.show();
            }
        }
    }

    @Override
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
}

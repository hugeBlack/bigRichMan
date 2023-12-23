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

//策略模式
//若使用卡牌技能，可能要投掷两个骰子，则调用这个类
public class ThreeDiceStrategy extends Component implements DiceStrategy {

    //第一个骰子用diceAssets1来表示
    private DiceAssets diceAssets1 = (DiceAssets) AssetFactory.getAsset("diceAssets");
    //第二个骰子用diceAssets2来表示
    private DiceAssets diceAssets2 = (DiceAssets) AssetFactory.getAsset("diceAssets");
    //第三个骰子用diceAssets3来表示
    private DiceAssets diceAssets3 = (DiceAssets) AssetFactory.getAsset("diceAssets");
    private boolean isRolling = false;  //是否翻滚，用同一个变量来做到对三个骰子状态的相同判断
    private int lastPoint1 = 1;  //记录第一个骰子上一次的点数
    private int lastPoint2 = 1;   //记录第二个骰子上一次的点数
    private int lastPoint3 = 1;   //记录第三个骰子上一次的点数
    private IGameSystem gameSystem;
    private AnimationTimeHelper animationTimeHelper = null;    //记录动画进度，现在选取三个骰子动画进度一样的方式
    private double lastProgress = 0;     //记录？？
    private int status = 0;
    private Vector2i startPos;   //起点

    //构造函数
    public ThreeDiceStrategy(Vector2i p) {
        super(p,new Vector2i(192,64));   //这里初始化位置及该Component组件框大小，p是传入的位置
        gameSystem = GameSystem.get();
        cursorType = Cursor.HAND_CURSOR;
        startPos = p ;
    }
    public void setStatus(int status){
        this.status =status;
        animationTimeHelper = new AnimationTimeHelper(1000);
        animationTimeHelper.start();
    }

    public int getRandomDice1Point(){    //随机生成第一个骰子的点数
        lastPoint1 = (int)(Math.random() * 6) + 1;
        return lastPoint1;
    }
    public int getRandomDice2Point(){       //随机生成第二个骰子的点数
        lastPoint2 = (int)(Math.random() * 6) + 1;
        return lastPoint2;
    }
    public int getRandomDice3Point(){       //随机生成第三个骰子的点数
        lastPoint3 = (int)(Math.random() * 6) + 1;
        return lastPoint3;
    }
    @Override
    public int getDicePointSum() {     //返回两个骰子的总点数
        return lastPoint1+lastPoint2+lastPoint3;
    }

    @Override
    public void drawMe(Graphics graphics) {
        double moveProgress = 1;
        //获取当前的relativePosition
        Vector2i relativePosition = getRelativePosition();
        if(!isRolling){     //如果骰子不在翻滚状态，分别画三个骰子的图
            if(animationTimeHelper != null)
                moveProgress = animationTimeHelper.getBezierProgress();
            Vector2i dstPosition;
            switch (status){
                case 0:
                    dstPosition=startPos.add(0, 0);
                    //计算当前距离到目标距离的差值
                    break;
                case 1:
                dstPosition=startPos.add(0, 140);
                break;
                case 2:
                    dstPosition=startPos.add(0, 300);
                    break;
                case 3:
                default:
                    dstPosition=startPos.add(0, 450);
                    break;
            }
            Vector2i delta = dstPosition.sub(relativePosition);
            setRelativePosition(relativePosition.add((int)(delta.x*moveProgress), (int)(delta.y*moveProgress)));
            Vector2i p = getAbsPosition();
            graphics.drawImage(diceAssets1.diceImage[lastPoint1 - 1], p.x,p.y, 64,64,null);
            graphics.drawImage(diceAssets2.diceImage[lastPoint2 - 1], p.x+64,p.y, 64,64,null);
            graphics.drawImage(diceAssets2.diceImage[lastPoint3 - 1], p.x+128,p.y, 64,64,null);
        }
        else {
            Vector2i p = getAbsPosition();
            double nowProgress = animationTimeHelper.getBezierProgress();
            if (nowProgress > lastProgress + 0.05) {
                lastPoint1 = getRandomDice1Point();
                lastPoint2 = getRandomDice2Point();
                lastPoint3 = getRandomDice3Point();
                lastProgress += 0.05;
            }
            graphics.drawImage(diceAssets1.diceImage[lastPoint1 - 1], p.x, p.y, 64, 64, null);
            graphics.drawImage(diceAssets2.diceImage[lastPoint2 - 1], p.x + 64, p.y , 64, 64, null);
            graphics.drawImage(diceAssets2.diceImage[lastPoint3 - 1], p.x + 128, p.y , 64, 64, null);
            if (nowProgress == 1) {
                isRolling = false;
                lastPoint1 = gameSystem.getNextDicePoint();    //这里获取骰子的点数并展示出来
                lastPoint2 = gameSystem.getNextDicePoint();
                lastPoint3 = gameSystem.getNextDicePoint();
                ConfirmBox c = new ConfirmBox("你骰到了" + getDicePointSum() + "点!");
                c.show();
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
    //三个骰子的情况同两个骰子的情况类似
    //三个骰子的情况下
    //我们要做到，玩家点击其中一个骰子，就同时进行三个骰子的投掷
    //我们肯定是得创建三个骰子变量，并用同一个isRolling来表征他们的状态
    //这样是可以做到同时止停的
    //我们必须画出三个骰子的图像，用drawMe方法
    //还要能获取用户点击骰子后触发的事件
    //虽然判断的方式相同，都是通过isRolling来判断
    //但是要让三个骰子的状态都发生改变
    //因此可以考虑将这个方法放进接口中
}

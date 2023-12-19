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
    public Dice(Vector2i p) {
        super(p, new Vector2i(64,64));
        gameSystem = GameSystem.get();
        cursorType = Cursor.HAND_CURSOR;
    }

    private int getRandomDicePoint() {
        return (int)(Math.random() * 6) + 1;
    }

    @Override
    public void drawMe(Graphics graphics) {
        Vector2i p = getAbsPosition();
        if(!isRolling){
            graphics.drawImage(diceAssets.diceImage[lastPoint - 1], p.x,p.y, 64,64,null);
        }else{
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

package org.dp.components;

import org.dp.assets.AssetFactory;
import org.dp.assets.PlayerPicture;
import org.dp.assets.TestComponentAssets;
import org.dp.event.ButtonClickEvent;
import org.dp.utils.AnimationTimeHelper;
import org.dp.utils.Vector2i;
import org.dp.view.Component;
import org.dp.view.events.*;

import java.awt.*;

// 这是个自定义组件的例子，点一下图像就往左右移动，然后发出一个PlayerClicked事件
public class TestComponent extends Component {
    TestComponentAssets playerPicture;
    private boolean isOnRight = false;
    private boolean isMoveComplete = true;
    private Vector2i startPos;
    private AnimationTimeHelper moveAnimationHelper = null;

    public TestComponent() {
        super(new Vector2i(100,50), new Vector2i(0,0));
        playerPicture = (TestComponentAssets) AssetFactory.getAsset("testAssets");
        Vector2i size = new Vector2i(100, 100);
        setHitBoxSize(size);
        // 设置鼠标在其上时的形状
        cursorType = Cursor.HAND_CURSOR;
    }


    @Override
    public void drawMe(Graphics graphics) {
        if(!isMoveComplete){
            double moveProgress = moveAnimationHelper.getBezierProgress();
            if(!isOnRight){
                if(moveProgress >= 1){
                    setRelativePosition(startPos.add(800, 0));
                    isOnRight = true;
                    isMoveComplete = true;
                    moveAnimationHelper = null;
                }else{
                    setRelativePosition(startPos.add((int) (800 * moveProgress), 0));
                }

            }else{
                if(moveProgress >= 1){
                    setRelativePosition(startPos.add(-800, 0));
                    isOnRight = false;
                    isMoveComplete = true;
                }else{
                    setRelativePosition(startPos.add((int) (-800 * moveProgress), 0));
                }
            }
        }


        Vector2i p = getAbsPosition();
        graphics.drawImage(playerPicture.img, p.x, p.y, 100, 100, null);
    }

    @Override
    public boolean onMouseEventMe(MouseEvent e){
        if(e instanceof ClickEvent){
            if(isMoveComplete){
                emitEvent(new ButtonClickEvent());
                moveAnimationHelper = new AnimationTimeHelper(1000);
                moveAnimationHelper.start();
                isMoveComplete = false;
                startPos = getAbsPosition();
            }
        } else if (e instanceof HoverEvent){
            // 鼠标在内部移动时要做的事情
        } else if (e instanceof LeaveEvent){
            System.out.println("LEAVE!");
        }
        return true;
    }

}

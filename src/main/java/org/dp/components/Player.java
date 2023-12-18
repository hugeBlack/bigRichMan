package org.dp.components;

import org.dp.assets.AssetFactory;
import org.dp.assets.PlayerPicture;
import org.dp.utils.AnimationTimeHelper;
import org.dp.utils.Time;
import org.dp.utils.Vector2i;
import org.dp.view.Component;
import org.dp.view.Playground;
import org.dp.view.events.*;

import java.awt.*;

// 这是个例子，点一下玩家图像就往左右移动，然后发出一个PlayerClicked事件
public class Player extends Component {
    PlayerPicture playerPicture;
    private boolean isOnRight = false;
    private double timeMoveStarted = 0;
    private boolean isMoveComplete = true;
    private Vector2i startPos;
    private AnimationTimeHelper moveAnimationHelper = null;

    public Player() {
        super(new Vector2i(100,50), new Vector2i(0,0));
        playerPicture = (PlayerPicture) AssetFactory.getAsset("player");
        Vector2i size = new Vector2i(playerPicture.img.getWidth(null), playerPicture.img.getHeight(null));
        setHitBoxSize(size);
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
        graphics.drawImage(playerPicture.img, p.x, p.y, null);
    }

    @Override
    public boolean onMouseEventMe(MouseEvent e){
        if(e instanceof ClickEvent){
            if(isMoveComplete){
                emitEvent(new PlayerClickEvent());
                moveAnimationHelper = new AnimationTimeHelper(1000);
                moveAnimationHelper.start();
                isMoveComplete = false;
                startPos = getAbsPosition();
                timeMoveStarted = Time.getTimeInMs();
            }
        } else if (e instanceof HoverEvent){
            Playground.get().setCursor(Cursor.HAND_CURSOR);
        } else if (e instanceof LeaveEvent){
            System.out.println("LEAVE!");
        }
        return true;
    }

}

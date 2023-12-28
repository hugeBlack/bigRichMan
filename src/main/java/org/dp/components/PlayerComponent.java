package org.dp.components;

import org.dp.assets.AssetFactory;
import org.dp.assets.PlayerPicture;
import org.dp.components.tiles.TileComponent;
import org.dp.event.GameEventBus;
import org.dp.event.GameEventListener;
import org.dp.event.IGameEvent;
import org.dp.event.UIPlayerMoveEvent;
import org.dp.utils.AnimationTimeHelper;
import org.dp.utils.Vector2i;
import org.dp.view.Component;

import java.awt.*;
import java.util.LinkedList;

public class PlayerComponent extends Component {

    private PlayerPicture playerPicture = (PlayerPicture) AssetFactory.getAsset("player");
    private int type;

    // 移动动画相关
    private boolean isMoving = false;
    private LinkedList<TileComponent> movePath;
    private AnimationTimeHelper animationTimeHelper;
    private int totalMoveLength = 0;
    private double nextProgress = 0;
    private PlayerComponent me;
    private Vector2i prevPos; // 相对位置
    private Vector2i nextPos; // 相对位置
    private Vector2i destPos;

    //type = 0~3
    public PlayerComponent(Vector2i p, int typeId) {
        super(p, new Vector2i(60,60));
        type= typeId;
        me = this;
        GameEventBus.get().registerListener(UIPlayerMoveEvent.class, new GameEventListener() {
            @Override
            public void onEvent(IGameEvent e) {
                UIPlayerMoveEvent playerMoveEvent = (UIPlayerMoveEvent) e;
                if(playerMoveEvent.target != me)
                    return;
                isMoving = true;
                movePath = playerMoveEvent.tilesPassed;
                animationTimeHelper = new AnimationTimeHelper(movePath.size() * 500);
                totalMoveLength = movePath.size();

                nextProgress = 1.0 / totalMoveLength;
                prevPos = getRelativePosition();
                TileComponent now = movePath.getFirst();
                Vector2i parentPos = getParent().getAbsPosition();
                nextPos = new Vector2i(now.getAbsPosition().x - parentPos.x + 20, now.getAbsPosition().y - parentPos.y + 10);
                TileComponent dest = movePath.getLast();
                destPos = new Vector2i(dest.getAbsPosition().x - parentPos.x + 20, dest.getAbsPosition().y - parentPos.y + 10);
                animationTimeHelper.start();
            }
        });
    }

    @Override
    public void drawMe(Graphics graphics) {

        if(isMoving){
            double progress = animationTimeHelper.getBezierProgress();
            if(progress >= 0.99){
                setRelativePosition(destPos);
                isMoving = false;
            }else if(progress >= nextProgress){
                nextProgress += 1.0 / totalMoveLength;

                if(movePath.size() > 1){
                    Vector2i parentPos = getParent().getAbsPosition();
                    TileComponent last =  movePath.removeFirst();
                    TileComponent now = movePath.getFirst();
                    prevPos = new Vector2i(last.getAbsPosition().x - parentPos.x + 20, last.getAbsPosition().y - parentPos.y + 10);
                    nextPos = new Vector2i(now.getAbsPosition().x - parentPos.x + 20, now.getAbsPosition().y - parentPos.y + 10);
                }else{
                    isMoving = false;
                }

            }else{
                double progressInBlock = (progress - nextProgress) * totalMoveLength + 1.0;
                int dx = nextPos.x - prevPos.x;
                int dy = nextPos.y - prevPos.y;
                setRelativePosition(new Vector2i((int) (prevPos.x + progressInBlock * dx), (int) (prevPos.y + progressInBlock * dy)));
            }

        }
        Vector2i p = getAbsPosition();
        graphics.drawImage(playerPicture.img[type], p.x,p.y, 60,60,null);
    }
}

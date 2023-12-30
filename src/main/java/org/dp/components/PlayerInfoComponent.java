package org.dp.components;

import org.dp.assets.*;
import org.dp.event.ButtonClickEvent;
import org.dp.logic.GameSystem;
import org.dp.scene.DefaultSelectPlayerElementFactory;
import org.dp.scene.PlayersNumScene;
import org.dp.scene.Scene;
import org.dp.scene.SelectPlayerElementFactory;
import org.dp.utils.StringUtils;
import org.dp.utils.Vector2i;
import org.dp.view.ComponentObserver;
import org.dp.view.Component;
import org.dp.view.GameButton;
import org.dp.view.Playground;
import org.dp.view.events.ComponentEvent;
import org.dp.view.events.ConfirmBoxEvent;
import org.dp.view.events.MouseEvent;

import java.awt.*;
import java.util.LinkedList;

public class PlayerInfoComponent extends Component {
    private ImageAsset assets = (ImageAsset) AssetFactory.getAsset("imageAssets");
    PlayerPicture playerPicture = (PlayerPicture) AssetFactory.getAsset("player");
    private GameButton exitButton;
    private Vector2i windowPos;
    private PlayerInfoComponent me;// 自己
    PlayerInfo playerInfos = GameSystem.get().getPlayerInfo();

    public PlayerInfoComponent() {
        super(new Vector2i(200, 100), new Vector2i(800, 600));
        me = this;
        // 添加按钮
        exitButton = new GameButton(new Vector2i(500, 640), new Vector2i(128, 35), "返回");
        exitButton.registerObserver(new ComponentObserver() {
            @Override
            public void onEvent(ComponentEvent e) {
                if (e instanceof ButtonClickEvent) {
                    Playground.get().removeChildren(me);
                }
            }
        });
        addComponent(exitButton);
    }

    @Override
    public void drawMe(Graphics graphics) {
        // 如何画一个组件：
        // 先获取左上角的绝对坐标，计算要画的位置，然后在graphics上画对应的图形
        Vector2i windowPos = getAbsPosition();
        Font font = ((FontLib) AssetFactory.getAsset("fontLib")).titleButtonText;
        Image board = assets.getImage("board");
        graphics.setFont(font);
        graphics.setColor(Color.gray);
        setHitBoxSize(getParent().getHitBoxSize());
        graphics.fillRect(windowPos.x, windowPos.y, board.getWidth(null), board.getHeight(null));
        graphics.drawImage(board, windowPos.x, windowPos.y, null);
        graphics.setFont(font);
        graphics.setColor(Color.BLACK);

        //做一个for循环，i的范围是0到玩家数量-1，要输出这个玩家的所有信息
        for(int i=0;i<GameSystem.get().getPlayerNum();i++){
            //获取这个玩家
            //注意要根据选择的角色来获取玩家
            PlayerInfo player=playerInfos.getPlayerInfo(GameSystem.get().getActorChoose()[i]);
            int dist=900/GameSystem.get().getPlayerNum();
            int x=windowPos.x+200+dist*i;
            graphics.drawImage(playerPicture.img[GameSystem.get(). getActorChoose()[i]], x, windowPos.y+50, 100, 100, null);
            int y=windowPos.y+200;
            //输出标题：玩家1
            graphics.drawString("玩家"+(i+1),x,y);
            y-=40;
            //输出玩家的信息
            graphics.drawString(player.defaultName,x,y+150);
            graphics.drawString("金币数："+player.coinNum,x,y+230);
            graphics.drawString("地产数："+player.houseNum,x,y+310);
            graphics.drawString("点券数：" + player.couponNum, x, y + 280);

            //输出卡牌数
            graphics.drawString("汽车卡牌数："+player.cardCarNum,x,y+390);
            graphics.drawString("幸运卡牌数："+player.cardLuckNum,x,y+470);

        }

    }

    @Override
    public boolean onMouseEventMe(MouseEvent e) {
        return true;
    }

    public void show() {
        Playground.get().addComponent(this);
    }

}
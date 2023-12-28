package org.dp.components;
import org.dp.assets.*;
import org.dp.event.*;
import org.dp.logic.GameSystem;
import org.dp.scene.*;
import org.dp.utils.StringUtils;
import org.dp.utils.Vector2i;
import org.dp.view.*;
import org.dp.view.Component;
import org.dp.view.events.ComponentEvent;
import org.dp.view.events.ConfirmBoxEvent;
import org.dp.view.events.MouseEvent;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Objects;

public class PlayerCardComponent extends Component{

    private ImageAsset assets =( ImageAsset) AssetFactory.getAsset("imageAssets");
    PlayerPicture playerPicture = (PlayerPicture) AssetFactory.getAsset("player");
    private GameButton exitButton;
    private GameButton carButton;
    private GameButton luckButton;
    private Vector2i windowPos;
    private Component me;//自己
    PlayerInfo playerInfos =GameSystem.get().getPlayerInfo();
    public PlayerCardComponent() {
        super(new Vector2i(200,100), new Vector2i(800,600));
        me = this;

        exitButton = new GameButton(new Vector2i(500, 640), new Vector2i(128,35), "返回");
        carButton = new GameButton(new Vector2i(500, 460), new Vector2i(128,35), "使用汽车");
        luckButton = new GameButton(new Vector2i(500, 540), new Vector2i(128,35), "使用幸运");
        exitButton.registerObserver(new ComponentObserver() {
            @Override
            public void onEvent(ComponentEvent e) {
                if(e instanceof ButtonClickEvent){
                    Playground.get().removeChildren(me);
                    GameEventBus.get().emitEvent((IGameEvent) new DiceChosenEvent());
                }
            }
        });

        carButton.registerObserver((e) -> {

            if(e instanceof ButtonClickEvent){

                // 获取当前玩家编号
                int playerId = GameScene.GetCurrentPlayerNum();

                PlayerInfo playerInfos =GameSystem.get().getPlayerInfo();
                PlayerInfo player=playerInfos.getPlayerInfo(GameSystem.get().getActorChoose()[playerId]);

                int currentPlayerCard = player.cardCarNum;
                if(currentPlayerCard >=1) {
                    player.updatePlayerInfo(player.id, "cardcar", currentPlayerCard - 1);
                    ConfirmBox c = new ConfirmBox("使用汽车卡牌");
                    c.show();

                    String[] options = {"1个骰子","2个骰子","3个骰子"};

                    Object obj = JOptionPane.showInputDialog(null,
                            "请选择骰子数",
                            "策略选择",
                            JOptionPane.QUESTION_MESSAGE,
                            null, options, options[0]);

                    String strategy = "1个骰子";

                    if (obj == null) {// 点击取消的话
                        GameSystem.get().getPlayerInfo().updatePlayerInfo(playerId, "strategy", 1);
                    }
                    else {
                        strategy = obj.toString();
                    }
                    // 根据选择设置策略
                    if(strategy.equals("1个骰子")) {
                        GameSystem.get().getPlayerInfo().updatePlayerInfo(playerId, "strategy", 1);
                    } else if(strategy.equals("2个骰子")){
                        GameSystem.get().getPlayerInfo().updatePlayerInfo(playerId, "strategy", 2);
                    } else if(strategy.equals("3个骰子")) {
                        GameSystem.get().getPlayerInfo().updatePlayerInfo(playerId, "strategy", 3);
                    }

                }
                else{
                    ConfirmBox c = new ConfirmBox("卡牌数量不足");
                    c.show();
                }
            }

        });

        luckButton.registerObserver(new ComponentObserver() {
            @Override
            public void onEvent(ComponentEvent e) {
                if(e instanceof ButtonClickEvent){
                    Playground.get().removeChildren(me);
                }
            }
        });
        addComponent(exitButton);
        addComponent(carButton);
        addComponent(luckButton);
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
        graphics.fillRect(windowPos.x,windowPos.y, board.getWidth(null), board.getHeight(null));
        graphics.drawImage(board, windowPos.x, windowPos.y, null);
        graphics.setFont(font);
        graphics.setColor(Color.BLACK);

        //获取这个玩家
        //注意要根据选择的角色来获取玩家
        PlayerInfo player=playerInfos.getPlayerInfo(GameScene.GetCurrentPlayerNum());
        int dist=900/GameSystem.get().getPlayerNum();
        int x=windowPos.x+200;
        graphics.drawImage(playerPicture.img[GameScene.GetCurrentPlayerNum()], x, windowPos.y+50, 100, 100, null);
        int y=windowPos.y+100;
        //输出玩家的信息
        graphics.drawString(player.defaultName,x,y+150);
        graphics.drawString("金币数："+player.coinNum,x,y+230);
        graphics.drawString("地产数："+player.houseNum,x,y+310);
        //输出卡牌数
        graphics.drawString("汽车卡牌数："+player.cardCarNum,x,y+390);
        graphics.drawString("幸运卡牌数："+player.cardLuckNum,x,y+470);
    }
    @Override
    public boolean onMouseEventMe(MouseEvent e){
        return true;
    }

    public void show() {
        Playground.get().addComponent(this);
        System.out.println(Playground.get());
    }
    private DiceCallback callback;

    public void setDiceCallback(DiceCallback callback) {
        this.callback = callback;
    }

    public interface DiceCallback {
        void onDiceChosen();
    }
}
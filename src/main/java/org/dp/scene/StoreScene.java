package org.dp.scene;

import org.dp.assets.AssetFactory;
import org.dp.assets.FontLib;
import org.dp.assets.PlayerInfo;
import org.dp.assets.TitleSceneAssets;
import org.dp.logic.GameSystem;
import org.dp.logic.IGameSystem;
import org.dp.view.ConfirmBox;
import org.dp.view.GameButton;
import org.dp.utils.Vector2i;
import org.dp.view.ComponentObserver;
import org.dp.view.Playground;
import org.dp.view.events.ComponentEvent;

import javax.swing.*;
import java.awt.*;

public class StoreScene extends Scene {

    private TitleSceneAssets assets = (TitleSceneAssets)AssetFactory.getAsset("titleSceneAssets");//根据名称获取资产
    private FontLib fontLib = ((FontLib) AssetFactory.getAsset("fontLib"));//获取字体
    //下面这些要等构造函数再进行初始化
    private boolean isInitialized = false;//是否已经初始化
    private Vector2i titleRelativePos = null;//标题的位置
    private int ascent = 0;//基线到最高字符顶部的距离

    private String title = "商店";


    public StoreScene() {

        GameButton backButton = new GameButton(new Vector2i(1250,100), new Vector2i(200,40), "返回");
        backButton.registerObserver(new ComponentObserver() {
            @Override
            public void onEvent(ComponentEvent e) {
                Playground.get().switchScene(GameSystem.get().getScene());
            }
        });
        addComponent(backButton);

        //LuckyCard的购买按钮
        GameButton buyButton = new GameButton(new Vector2i(300,800), new Vector2i(200,30), "购买");
        buyButton.registerObserver(new ComponentObserver() {
            @Override
            public void onEvent(ComponentEvent e) {
                int currentPlayerNum= GameScene.GetCurrentPlayerNum();
                PlayerInfo playerInfos =GameSystem.get().getPlayerInfo();
                PlayerInfo player=playerInfos.getPlayerInfo(GameSystem.get().getActorChoose()[currentPlayerNum]);

                int currentPlayerCoin = player.coinNum;
                int currentPlayerCard = player.cardNum;
                if(currentPlayerCoin>=1000) {
                    player.updatePlayerInfo(player.id, "coin", currentPlayerCoin - 1000);
                    player.updatePlayerInfo(player.id, "card", currentPlayerCard + 1);
                    ConfirmBox c = new ConfirmBox(player.defaultName+"您已成功购买幸运骰子卡牌！您还有"+player.coinNum+"元！");
                    c.show();
                }
                else{
                    ConfirmBox c = new ConfirmBox(player.defaultName+"您的余额不足1000元！");
                    c.show();
                }
            }
        });
        addComponent(buyButton);

        //CarCard的购买按钮
        GameButton buyButton2 = new GameButton(new Vector2i(1100,800), new Vector2i(200,30), "购买");
        buyButton2.registerObserver(new ComponentObserver() {
            @Override
            public void onEvent(ComponentEvent e) {
                int currentPlayerNum= GameScene.GetCurrentPlayerNum();
                PlayerInfo playerInfos =GameSystem.get().getPlayerInfo();
                PlayerInfo player2=playerInfos.getPlayerInfo(GameSystem.get().getActorChoose()[currentPlayerNum]);

                int currentPlayerCoin = player2.coinNum;
                int currentPlayerCard = player2.cardNum;
                if(currentPlayerCoin>=2000) {
                    player2.updatePlayerInfo(player2.id, "coin", currentPlayerCoin - 2000);
                    player2.updatePlayerInfo(player2.id, "card", currentPlayerCard + 1);
                    ConfirmBox c = new ConfirmBox(player2.defaultName+"您已成功购买汽车卡牌！您还有"+player2.coinNum+"元！");
                    c.show();
                }
                else{
                    ConfirmBox c = new ConfirmBox(player2.defaultName+"您的余额不足2000元！");
                    c.show();
                }
            }
        });
        addComponent(buyButton2);

    }

    @Override
    public void drawMe(Graphics graphics) {//绘制商店
        graphics.setFont(fontLib.testFont);//设置字体
        Vector2i p = getAbsPosition();//获取绝对位置
        if(!isInitialized){//如果没有初始化
            int titleWidth = graphics.getFontMetrics().stringWidth(title);//获取标题的宽度
            int titleHeight = graphics.getFontMetrics().getHeight();//获取标题的高度
            ascent = graphics.getFontMetrics().getAscent();//获取基线到最高字符顶部的距离
            titleRelativePos = new Vector2i(-titleWidth / 2, titleHeight);//设置标题的相对位置
            isInitialized = true;
        }

        graphics.drawImage(assets.store, p.x, p.y, null);//绘制背景
       Vector2i drawPoint = p.add(800 + titleRelativePos.x,  20+titleRelativePos.y);//设置标题的绘制位置
        graphics.drawString(title, drawPoint.x, drawPoint.y);//绘制标题

        // 创建JLabel 用于显示图片
        JLabel label = new JLabel();
        // 加载图片
        ImageIcon icon = new ImageIcon("./assets/card/LuckyCard.jpg");
        label.setIcon(icon);

        //缩小图片
        icon.setImage(icon.getImage().getScaledInstance(400, 600, Image.SCALE_DEFAULT));//100,100分别为大小 可以自由设置

        //将图片添加到窗体上
        graphics.drawImage(icon.getImage(), 200, 170, null);

        // 创建JLabel 用于显示图片
        JLabel label2 = new JLabel();
        // 加载图片
        ImageIcon icon2 = new ImageIcon("./assets/card/CarCard.jpg");
        label2.setIcon(icon2);

        //缩小图片
        icon2.setImage(icon2.getImage().getScaledInstance(400, 600, Image.SCALE_DEFAULT));//100,100分别为大小 可以自由设置

        //将图片添加到窗体上
        graphics.drawImage(icon2.getImage(), 1000, 170, null);
    }
}

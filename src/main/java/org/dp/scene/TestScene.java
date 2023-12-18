package org.dp.scene;

import org.dp.assets.AssetFactory;
import org.dp.assets.FontLib;
import org.dp.components.MapComponent;
import org.dp.event.ButtonClickEvent;
import org.dp.utils.Vector2i;
import org.dp.view.ComponentObserver;
import org.dp.components.TestComponent;
import org.dp.view.ConfirmBox;
import org.dp.view.GameButton;
import org.dp.view.Playground;
import org.dp.view.events.ComponentEvent;
import org.dp.view.events.ConfirmBoxEvent;

import java.awt.*;

public class TestScene extends Scene {
    // 这是个例子，图上有一个玩家，点一下玩家图像就左右移动，然后发出一个PlayerClicked事件，被observer接收，然后clickCount+1
    private int clickCount;
    private Font font = ((FontLib)AssetFactory.getAsset("fontLib")).testFont;
    public TestScene(){
        // 一个TestComponent作为子组件
        TestComponent testComponent = new TestComponent();
        addComponent(testComponent);
        MapComponent mapComponent = new MapComponent(new Vector2i(300,300));
        addComponent(mapComponent);
        testComponent.registerObserver(new ComponentObserver() {
            @Override
            public void onEvent(ComponentEvent e) {
                clickCount++;
            }
        });

        ConfirmBox c = new ConfirmBox("先获取左上角的绝对坐标，计算要画的位置，然后在graphics上画对应的图形");
        GameButton buttonOpenConfirmBox = new GameButton(new Vector2i(300,800), new Vector2i(300,50), "Open ConfirmBox");
        GameButton backButton = new GameButton(new Vector2i(700,800), new Vector2i(300,50), "Back");
        buttonOpenConfirmBox.registerObserver(new ComponentObserver() {
            @Override
            public void onEvent(ComponentEvent e) {
                if(e instanceof ButtonClickEvent){
                    addComponent(c);
                }

            }
        });
        addComponent(buttonOpenConfirmBox);

        backButton.registerObserver(new ComponentObserver() {
            @Override
            public void onEvent(ComponentEvent e) {
                Playground.get().switchScene(new TitleScene());
            }
        });
        addComponent(backButton);

        c.registerObserver(new ComponentObserver() {
            @Override
            public void onEvent(ComponentEvent e) {
                if(e instanceof ConfirmBoxEvent){
                    ConfirmBoxEvent e1 = (ConfirmBoxEvent) e;
                    if(e1.getResult()){
                        System.out.println("OK！");
                    }else{
                        System.out.println("Cancel！");
                    }
                }
                removeChildren(c);

            }
        });


    }
    @Override
    public void drawMe(Graphics graphics) {
        // 如何画一个组件：
        // 先获取左上角的绝对坐标，计算要画的位置，然后在graphics上画对应的图形
        Vector2i p = getAbsPosition();
        Vector2i drawPoint = p.add(20,200);
        graphics.setFont(font);
        graphics.drawString("点击了" + clickCount + "次", drawPoint.x, drawPoint.y);
    }
}

package org.dp.view;

import org.dp.assets.AssetFactory;
import org.dp.assets.TestFont;
import org.dp.utils.Vector2i;
import org.dp.view.events.ComponentEvent;

import java.awt.*;

public class TestScene extends Component{
    // 这是个例子，图上有一个玩家，点一下玩家图像就左右移动，然后发出一个PlayerClicked事件，被observer接收，然后clickCount+1
    private int clickCount;
    private Font font = ((TestFont)AssetFactory.getAsset("testFont")).font;
    public TestScene(){
        super(new Vector2i(0,0), new Vector2i(1600,900));
        // 一个player作为子组件
        Player player = new Player();
        addComponent(player);
        player.registerObserver(new ComponentObserver() {
            @Override
            public void onEvent(ComponentEvent e) {
                clickCount++;
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

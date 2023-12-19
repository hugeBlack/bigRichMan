package org.dp.view;

import org.dp.assets.AssetFactory;
import org.dp.assets.ConfirmBoxAssets;
import org.dp.event.ButtonClickEvent;
import org.dp.utils.StringUtils;
import org.dp.utils.Vector2i;
import org.dp.view.events.ComponentEvent;
import org.dp.view.events.ConfirmBoxEvent;
import org.dp.view.events.MouseEvent;

import java.awt.*;

// 显示一个覆盖全局的ok/cancel框
public class ConfirmBox extends Component{

    private ConfirmBoxAssets assets = (ConfirmBoxAssets) AssetFactory.getAsset("confirmBoxAssets");
    private boolean initialized = false;
    private GameButton okButton;
    private GameButton noButton;
    private Vector2i windowPos;
    private java.util.List<String> splitInfo;
    private String info;
    private int ascent;
    private ConfirmBox me;
    public ConfirmBox(String info) {
        super(new Vector2i(0,0), new Vector2i(0,0));
        okButton = new GameButton(new Vector2i(7, 128), new Vector2i(128,25), "确定");
        noButton = new GameButton(new Vector2i(135, 128), new Vector2i(128,25), "取消");
        okButton.setBackgroundColor(null);
        noButton.setBackgroundColor(null);
        okButton.setTextColor(Color.BLACK);
        noButton.setTextColor(Color.BLACK);
        okButton.setFont(assets.buttonFont);
        noButton.setFont(assets.buttonFont);
        addComponent(okButton);
        addComponent(noButton);
        this.info = info;
        me = this;
        okButton.registerObserver(new ComponentObserver() {
            @Override
            public void onEvent(ComponentEvent e) {
                if(e instanceof ButtonClickEvent){
                    emitEvent(new ConfirmBoxEvent(true));
                    Playground.get().removeChildren(me);
                }

            }
        });

        noButton.registerObserver(new ComponentObserver() {
            @Override
            public void onEvent(ComponentEvent e) {
                if(e instanceof ButtonClickEvent){
                    emitEvent(new ConfirmBoxEvent(false));
                    Playground.get().removeChildren(me);
                }

            }
        });
    }

    @Override
    public void drawMe(Graphics graphics) {
        Vector2i p = getAbsPosition();
        if(!initialized){
            setHitBoxSize(getParent().getHitBoxSize());
            windowPos = new Vector2i(p.x + getHitBoxSize().x / 2 - assets.background.getWidth(null) / 2, p.y + getHitBoxSize().y / 2 - assets.background.getHeight(null) / 2);
            okButton.setRelativePosition(new Vector2i(windowPos.x + 7, windowPos.y+128));
            noButton.setRelativePosition(new Vector2i(windowPos.x + 135, windowPos.y+128));
            graphics.setFont(assets.infoFont);
            splitInfo = StringUtils.wrap(info, graphics.getFontMetrics(), 220);
            ascent = graphics.getFontMetrics().getHeight();
        }

        graphics.setColor(assets.maskColor);
        graphics.fillRect(p.x,p.y, getHitBoxSize().x, getHitBoxSize().y);
        graphics.drawImage(assets.background, windowPos.x, windowPos.y, null);

        graphics.setFont(assets.infoFont);
        graphics.setColor(Color.BLACK);
        int l = 1;
        for(String s : splitInfo){
            graphics.drawString(s, windowPos.x + 15, windowPos.y + 35 + ascent * l);
            ++l;
        }
    }

    @Override
    public boolean onMouseEventMe(MouseEvent e){
        return true;
    }

    public void show() {
        Playground.get().addComponent(this);
    }
}

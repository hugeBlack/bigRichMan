package org.dp.view;

import org.dp.assets.AssetFactory;
import org.dp.assets.FontLib;
import org.dp.event.ButtonClickEvent;
import org.dp.utils.Vector2i;
import org.dp.view.Component;
import org.dp.view.events.*;

import java.awt.*;

public class GameButton extends Component {

    private boolean isMouseIn = false;
    private boolean isInitialized = false;
    private Vector2i titleRelativePos = null;
    private int ascent = 0;

    private Color backgroundColor = Color.blue;
    private Color clickedBackgroundColor = new Color((int) (backgroundColor.getRed() *0.5 + 128), (int) (backgroundColor.getGreen()*0.5 + 128), (int) (backgroundColor.getBlue()*0.5 + 128));
    private Color textColor = Color.WHITE;
    private Color borderColor = Color.BLACK;    //用于存储按钮边框颜色
    private Font font = ((FontLib) AssetFactory.getAsset("fontLib")).titleButtonText;

    public Color getTextColor() {
        return textColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        if(backgroundColor == null)
            clickedBackgroundColor = new Color(0.0f,0.0f,0.0f, 0.3f);//透明
        else
            clickedBackgroundColor = new Color((int) (backgroundColor.getRed() *0.5 + 128), (int) (backgroundColor.getGreen()*0.5 + 128), (int) (backgroundColor.getBlue()*0.5 + 128));
        this.backgroundColor = backgroundColor;
    }
    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        isInitialized = false;
        this.font = font;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        this.isInitialized = false;
    }

    private String title;
    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public GameButton(Vector2i p, Vector2i hitBoxSize, String title) {
        super(p, hitBoxSize);
        this.title = title;
        this.cursorType = Cursor.HAND_CURSOR;
    }

    @Override
    public void drawMe(Graphics graphics) {
        Vector2i p = getAbsPosition();
        Vector2i hitBox = this.getHitBoxSize();
        if(isMouseIn){
            graphics.setColor(clickedBackgroundColor);
            graphics.fillRect(p.x, p.y, hitBox.x, hitBox.y);
        }else if(backgroundColor != null){
            graphics.setColor(backgroundColor);
            graphics.fillRect(p.x, p.y, hitBox.x, hitBox.y);
        }

        // 设置边框颜色
        graphics.setColor(borderColor);
        graphics.drawRect(p.x, p.y, hitBox.x, hitBox.y);

        graphics.setColor(textColor);
        graphics.setFont(font);
        if(!isInitialized){
            int titleWidth = graphics.getFontMetrics().stringWidth(title);
            int titleHeight = graphics.getFontMetrics().getHeight();
            titleRelativePos = new Vector2i(-titleWidth / 2, -titleHeight / 2);
            ascent = graphics.getFontMetrics().getAscent();
            isInitialized = true;
        }
        graphics.drawString(title, p.x + hitBox.x/2 + titleRelativePos.x, p.y + hitBox.y / 2 + titleRelativePos.y + ascent);

    }




    @Override
    public boolean onMouseEventMe(MouseEvent e){
        if(e instanceof ClickEvent){
            emitEvent(new ButtonClickEvent());
        } else if (e instanceof HoverEvent){
            isMouseIn = true;
        } else if (e instanceof LeaveEvent){
            isMouseIn = false;
        }
        return true;
    }
}

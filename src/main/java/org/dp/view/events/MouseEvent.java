package org.dp.view.events;

public class MouseEvent extends ComponentEvent{
    public int x;
    public int y;
    public MouseEvent(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

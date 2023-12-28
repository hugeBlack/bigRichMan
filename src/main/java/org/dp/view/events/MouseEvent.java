package org.dp.view.events;

public class MouseEvent{
    private boolean processed = false;
    public int x;
    public int y;
    public MouseEvent(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public boolean isProcessed() {
        return processed;
    }
}

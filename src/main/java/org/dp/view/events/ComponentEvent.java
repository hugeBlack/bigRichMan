package org.dp.view.events;

public abstract class ComponentEvent {
    private boolean isCanceled;
    public void setCancelled(boolean c){
        isCanceled = c;
    }

    public boolean getCancelled(){
        return isCanceled;
    }
}

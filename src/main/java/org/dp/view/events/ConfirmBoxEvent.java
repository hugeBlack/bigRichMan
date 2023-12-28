package org.dp.view.events;


public class ConfirmBoxEvent extends ComponentEvent {
    private boolean result;
    public ConfirmBoxEvent(boolean result){
        this.result = result;
    }

    public boolean getResult() {
        return result;
    }


}

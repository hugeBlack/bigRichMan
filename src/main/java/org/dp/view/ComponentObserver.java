package org.dp.view;

import org.dp.view.events.ComponentEvent;

public interface ComponentObserver {
    // 观察者模式！通过判断ComponentEvent的类型判断监听到了什么事件
    void onEvent(ComponentEvent e);
}

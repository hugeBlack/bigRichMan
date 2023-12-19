package org.dp.event;

import org.dp.event.IGameEvent;

public interface GameEventListener {
    void onEvent(IGameEvent e);
}

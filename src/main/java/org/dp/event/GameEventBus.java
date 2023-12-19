package org.dp.event;


import java.util.HashMap;
import java.util.HashSet;

// 实现中介者模式，向游戏的各个组件发送事件
public class GameEventBus {

    private static GameEventBus instance = null;
    private HashMap<Class, HashSet<GameEventListener>> listenerMap = new HashMap<>();
    private GameEventBus(){

    }

    // 单例模式
    public static GameEventBus get(){
        if(instance == null)
            instance = new GameEventBus();
        return instance;
    }

    public void registerListener(Class eventType, GameEventListener listener){
        if(!listenerMap.containsKey(eventType)){
            listenerMap.put(eventType, new HashSet<>());
        }
        listenerMap.get(eventType).add(listener);
    }

    public boolean unregisterListener(Class eventType, GameEventListener listener){
        if(!listenerMap.containsKey(eventType)){
            listenerMap.put(eventType, new HashSet<>());
        }
        if(listenerMap.get(eventType).contains(listener)){
            listenerMap.get(eventType).remove(listener);
            return true;
        }
        return false;
    }

    public void emitEvent(IGameEvent e){
        // 根据注册的监听事件类型分发事件
        if(!listenerMap.containsKey(e.getClass()))
            return;
        for(GameEventListener el : listenerMap.get(e.getClass())){
            el.onEvent(e);
        }
    }
}

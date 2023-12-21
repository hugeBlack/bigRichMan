package org.dp.view;

import org.dp.utils.Vector2i;
import org.dp.view.events.ComponentEvent;
import org.dp.view.events.HoverEvent;
import org.dp.view.events.LeaveEvent;
import org.dp.view.events.MouseEvent;

import java.awt.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;

// 继承了这个类的界面类都是模板模式！
public abstract class Component {
    private LinkedList<Component> children = new LinkedList<>();//子结点
    private static Vector2i lastMousePos = new Vector2i(-99999,-99999);//鼠标位置
    private static Component lastHovered = null;//上次鼠标停留的组件
    private boolean isHidden = false;
    private Component parent = null;
    private HashSet<ComponentObserver> observers = new HashSet<>();
    private Vector2i relativePosition = null;
    private Vector2i hitBoxSize = null;

    public int cursorType = 0;//鼠标样式
    private LinkedList<Component> componentsToBeRemoved = new LinkedList<>();
    private LinkedList<Component> componentsToBeAdded = new LinkedList<>();

    public Vector2i getHitBoxSize() {
        return new Vector2i(hitBoxSize);
    }

    public void setHitBoxSize(Vector2i hitBoxSize) {
        this.hitBoxSize = new Vector2i(hitBoxSize);
    }



    public static void drawRoot(Component root, Graphics graphics){
        root.draw(graphics);
    }
    public static void emitRootMouseEvent(Component root, MouseEvent e){
        Vector2i tmp = new Vector2i(e.x, e.y);
        root.onMouseEvent(e);
        if(e instanceof HoverEvent){
            lastMousePos = tmp;
        }
    }

    public Vector2i getRelativePosition() {
        return relativePosition;
    }

    public void setRelativePosition(Vector2i relativePosition) {
        this.relativePosition = relativePosition;
    }


    public boolean isHidden() {
        return isHidden;
    }

    public Component getParent() {
        return parent;
    }

    // 把这个组件从parent中脱离
    public void detach(Component parent) {
        this.parent = parent;
    }

    public void removeChildren(Component component){
        // 先加进来，下一帧再处理
        if(children.contains(component))
            componentsToBeRemoved.add(component);
        else
            throw new RuntimeException("Child not found");
    }

    private void draw(Graphics graphics){
        // 迭代器模式！迭代地绘制整个图形界面
        for(Component c : componentsToBeRemoved){
            children.remove(c);
        }
        for(Component c : componentsToBeAdded){
            children.add(c);
        }
        componentsToBeRemoved.clear();
        componentsToBeAdded.clear();
        if(!isHidden)
            drawMe(graphics);
        for(Component c : children){
            c.draw(graphics);
        }
    }

    private void onMouseEvent(MouseEvent e){
        // 责任链模式！
        // 向下传递点击事件，
        e.x -= relativePosition.x;
        e.y -= relativePosition.y;
        int orgX = e.x;
        int orgY = e.y;

        // 先沿着组件树传播，如果被处理了就停止传播
        for (Iterator<Component> it = children.descendingIterator(); it.hasNext(); ) {
            Component c = it.next();
            if(c.inHitBox(orgX, orgY)){
                c.onMouseEvent(e);
                if(e.isProcessed()) {
                    break;
                }
            }
        }
        if(e.isProcessed())
            return;
        // 没被处理，看看自己能不能处理
        boolean processed = onMouseEventMe(e);
        if(processed){
            e.setProcessed(true);
            Playground.get().setCursor(this.cursorType);
        }


        // 特别的，如果当前是一个移动事件，而且不是上次移动到的目标，则说明要向目标发出一个移出事件
        if(processed && e instanceof HoverEvent && lastHovered != this){
            if(lastHovered !=null)
                lastHovered.onMouseEventMe(new LeaveEvent(orgX, orgY));
            lastHovered = this;
        }
    }

    //  可以重载这个函数来决定是否继续传播，如果你想处理，就返回true
    public boolean onMouseEventMe(MouseEvent e){
        return false;
    }

    public abstract void drawMe(Graphics graphics);
    public void addComponent(Component child){
        if(children.contains(child))
            return;
        componentsToBeAdded.add(child);
        child.parent = this;
    }

    public void registerObserver(ComponentObserver eo){
        observers.add(eo);
    }

    public void removeObserver(ComponentObserver eo){
        if(observers.contains(eo))
            observers.remove(eo);
        else
            throw new RuntimeException("Child not found");
    }
    public void emitEvent(ComponentEvent e){
        // 观察者模式！观察组件发出的event
        for(ComponentObserver co : observers){
            co.onEvent(e);
        }
    }

    // 向上递归找到组件的绝对坐标
    public Vector2i getAbsPosition() {
        if(relativePosition == null)
            return new Vector2i(0,0);
        else if (parent == null) {
            return new Vector2i(this.relativePosition);
        }else{
            Vector2i p = parent.getAbsPosition();
            return new Vector2i(this.relativePosition.x + p.x, this.relativePosition.y + p.y);
        }
    }

    public Component(Vector2i p, Vector2i hitBoxSize){
        relativePosition = new Vector2i(p);
        this.hitBoxSize = new Vector2i(hitBoxSize);
    }

    private boolean inHitBox(int x, int y){
        return x >= relativePosition.x && x < relativePosition.x + hitBoxSize.x
                && y >= relativePosition.y && y < relativePosition.y + hitBoxSize.y;
    }

    public LinkedList<Component> getChildren() {
        return children;
    }
}

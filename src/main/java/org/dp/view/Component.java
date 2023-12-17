package org.dp.view;

import org.dp.utils.Vector2i;
import org.dp.view.events.ComponentEvent;
import org.dp.view.events.HoverEvent;
import org.dp.view.events.LeaveEvent;
import org.dp.view.events.MouseEvent;

import java.awt.*;
import java.util.HashSet;

// 继承了这个类的界面类都是模板模式！
public abstract class Component {
    private HashSet<Component> children = new HashSet<>();
    private HashSet<Component> childrenMouseIn = new HashSet<>();
    private boolean isHidden = false;
    private Component parent = null;
    private HashSet<ComponentObserver> observers = new HashSet<>();
    private Vector2i relativePosition = null;
    private Vector2i hitBoxSize = null;

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
        root.onMouseEvent(e);
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
        if(children.contains(component))
            children.remove(component);
        else
            throw new RuntimeException("Child not found");
    }

    private void draw(Graphics graphics){
        // 迭代器模式！迭代地绘制整个图形界面
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

        for(Component c : children){
            if(e.getCancelled()) {
                break;
            }
            if(c.inHitBox(orgX, orgY))
                c.onMouseEvent(e);
        }
        if(!e.getCancelled() && onMouseEventMe(e))
            e.setCancelled(true);

        if(!(e instanceof HoverEvent))
            return;

        for(Component c : children) {
            if (childrenMouseIn.contains(c) && !c.inHitBox(orgX, orgY)) {
                c.onMouseEvent(new LeaveEvent(orgX, orgY));
            }
        }

        childrenMouseIn.clear();
        for(Component c : children){
            if(c.inHitBox(orgX, orgY))
                childrenMouseIn.add(c);
        }
    }

    //  可以重载这个函数来决定是否继续传播，如果你想处理，就返回true
    public boolean onMouseEventMe(MouseEvent e){
        return false;
    }

    public abstract void drawMe(Graphics graphics);
    public void addComponent(Component child){
        children.add(child);
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

    Component(Vector2i p, Vector2i hitBoxSize){
        relativePosition = new Vector2i(p);
        this.hitBoxSize = new Vector2i(hitBoxSize);
    }

    private boolean inHitBox(int x, int y){
        return x >= relativePosition.x && x < relativePosition.x + hitBoxSize.x
                && y >= relativePosition.y && y < relativePosition.y + hitBoxSize.y;
    }
}

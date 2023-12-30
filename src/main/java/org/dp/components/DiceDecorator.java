package org.dp.components;

import org.dp.utils.Vector2i;
import org.dp.view.Component;
import org.dp.view.events.MouseEvent;

// DiceStrategy 装饰器抽象类
abstract class DiceDecorator extends Component implements DiceStrategy {
    protected DiceStrategy decoratedStrategy;

    public DiceDecorator(Vector2i p, DiceStrategy decoratedStrategy) {
        super(p, new Vector2i(64,64));
        this.decoratedStrategy = decoratedStrategy;
    }

    public boolean onMouseEventMe(MouseEvent e) {
        return decoratedStrategy.onMouseEventMe(e);
    }

    public int getDicePointSum() {
        return decoratedStrategy.getDicePointSum();
    }

    public void setStatus(int currentPlayer) {
        decoratedStrategy.setStatus(currentPlayer);
    }
}
package org.dp.components.tiles;

import org.dp.utils.Vector2i;
import org.dp.view.Component;

import java.awt.*;

public abstract class TileComponent extends Component {

    public TileComponent(Vector2i p, Vector2i hitBoxSize) {
        super(p, hitBoxSize);
    }
}

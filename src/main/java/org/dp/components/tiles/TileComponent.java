package org.dp.components.tiles;

import org.dp.utils.Vector2i;
import org.dp.view.Component;

import java.awt.*;

public abstract class TileComponent extends Component {

    public TileComponent(Vector2i p) {
        super(p, new Vector2i(100, 100));
    }
}

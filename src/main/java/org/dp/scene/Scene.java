package org.dp.scene;

import org.dp.utils.Vector2i;
import org.dp.view.Component;

public abstract class Scene extends Component {
    public Scene() {
        super(new Vector2i(0,0), new Vector2i(1600,900));
    }

    @Override
    public Vector2i getAbsPosition(){
        return new Vector2i(0,0);
    }
}

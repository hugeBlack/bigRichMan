package org.dp.components;

import org.dp.components.tiles.PlaceTileComponent;
import org.dp.components.tiles.StartTileComponent;
import org.dp.components.tiles.TileFactory;
import org.dp.utils.Vector2i;
import org.dp.view.Component;
import org.dp.view.events.ClickEvent;
import org.dp.view.events.HoverEvent;
import org.dp.view.events.LeaveEvent;
import org.dp.view.events.MouseEvent;

import java.awt.*;

public class MapComponent extends Component {
    public MapComponent(Vector2i p) {
        // 稍后设置大小
        super(p, new Vector2i(0,0));



    }

    @Override
    public void drawMe(Graphics graphics) {

    }

    @Override
    public boolean onMouseEventMe(MouseEvent e){
        if(e instanceof ClickEvent){
            System.out.println("Click");
        } else if (e instanceof HoverEvent){

        } else if (e instanceof LeaveEvent){
            System.out.println("LEAVE map!");
        }
        return true;
    }
}

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
        TileFactory tileFactory = new TileFactory();
        // 把地图读入然后设置位置，应该从gameSystem中获得
        StartTileComponent startTileComponent = (StartTileComponent) tileFactory.getTile(TileFactory.TileType.START, new Vector2i(200,200));
        PlaceTileComponent placeTileComponent1 = (PlaceTileComponent) tileFactory.getTile(TileFactory.TileType.PLACE, new Vector2i(0,0));
        PlaceTileComponent placeTileComponent2 = (PlaceTileComponent) tileFactory.getTile(TileFactory.TileType.PLACE, new Vector2i(100,0));
        PlaceTileComponent placeTileComponent3 = (PlaceTileComponent) tileFactory.getTile(TileFactory.TileType.PLACE, new Vector2i(200,0));
        PlaceTileComponent placeTileComponent4 = (PlaceTileComponent) tileFactory.getTile(TileFactory.TileType.PLACE, new Vector2i(0,100));
        PlaceTileComponent placeTileComponent5 = (PlaceTileComponent) tileFactory.getTile(TileFactory.TileType.PLACE, new Vector2i(200,100));
        PlaceTileComponent placeTileComponent6 = (PlaceTileComponent) tileFactory.getTile(TileFactory.TileType.PLACE, new Vector2i(0,200));
        PlaceTileComponent placeTileComponent7 = (PlaceTileComponent) tileFactory.getTile(TileFactory.TileType.PLACE, new Vector2i(100,200));
        addComponent(startTileComponent);
        addComponent(placeTileComponent1);
        addComponent(placeTileComponent2);
        addComponent(placeTileComponent3);
        addComponent(placeTileComponent4);
        addComponent(placeTileComponent5);
        addComponent(placeTileComponent6);
        addComponent(placeTileComponent7);
        setHitBoxSize(new Vector2i(300,300));


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

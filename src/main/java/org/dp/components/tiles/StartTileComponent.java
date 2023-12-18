package org.dp.components.tiles;

import org.dp.assets.AssetFactory;
import org.dp.assets.MapAssets;
import org.dp.utils.Vector2i;

import java.awt.*;

public class StartTileComponent extends TileComponent {

    private Image img = ((MapAssets) AssetFactory.getAsset("mapAssets")).startTile;

    public StartTileComponent(Vector2i p) {
        super(p, new Vector2i(100, 100));
    }

    @Override
    public void drawMe(Graphics graphics) {
        Vector2i p = getAbsPosition();
        graphics.drawImage(img, p.x,p.y, 100,100,null);
    }
}

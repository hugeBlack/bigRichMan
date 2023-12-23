package org.dp.components.tiles;

import org.dp.assets.AssetFactory;
import org.dp.assets.MapAssets;
import org.dp.utils.Vector2i;

import java.awt.*;

public class EventTileComponent  extends TileComponent {

    private Image img = ((MapAssets) AssetFactory.getAsset("mapAssets")).eventTile;
    private String title="命运";
    public EventTileComponent(Vector2i p) {
        super(p);
    }

    @Override
    public void drawMe(Graphics graphics) {
        Vector2i p = getAbsPosition();
        Vector2i hitBox = getHitBoxSize();
        int ascent = graphics.getFontMetrics().getAscent();
        graphics.drawImage(img, p.x,p.y, 100,100,null);
        graphics.drawString(title, p.x + hitBox.x/3, p.y + hitBox.y/3  + ascent);
    }
}

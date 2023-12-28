package org.dp.components.tiles;

import org.dp.assets.AssetFactory;
import org.dp.assets.MapAssets;
import org.dp.event.GameEventBus;
import org.dp.event.LandedEvent.EventTileListener;
import org.dp.event.LandedEvent.PlayerLandedOnEventTile;
import org.dp.utils.Vector2i;

import java.awt.*;

public class EventTileComponent  extends TileComponent {

    private Image img = ((MapAssets) AssetFactory.getAsset("mapAssets")).eventTile;
    private String title="命运";
    private int bonus;
    public EventTileComponent(Vector2i p,int bonus) {
        super(p);
        // 注册监听器
        GameEventBus.get().registerListener(PlayerLandedOnEventTile.class, new EventTileListener());
        this.bonus=bonus;
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

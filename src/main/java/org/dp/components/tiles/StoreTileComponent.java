package org.dp.components.tiles;

import org.dp.assets.AssetFactory;
import org.dp.assets.FontLib;
import org.dp.assets.MapAssets;
import org.dp.event.GameEventBus;
import org.dp.event.LandedEvent.PlayerLandedOnStoreTile;
import org.dp.event.LandedEvent.StoreTileListener;
import org.dp.utils.Vector2i;

import java.awt.*;

public class StoreTileComponent extends TileComponent {
    private Image img = ((MapAssets) AssetFactory.getAsset("mapAssets")).storeTile;
    private String title = "商店";

    public StoreTileComponent(Vector2i p) {
        super(p);
        // 注册监听器
        //GameEventBus.get().registerListener(PlayerLandedOnStoreTile.class, new StoreTileListener());
    }

    @Override
    public void drawMe(Graphics graphics) {
        Vector2i p = getAbsPosition();
        Vector2i hitBox = getHitBoxSize();
        int ascent = graphics.getFontMetrics().getAscent();
        graphics.drawImage(img, p.x, p.y, 100, 100, null);
        Font font = ((FontLib) AssetFactory.getAsset("fontLib")).placePriceFont;
        graphics.setFont(font);
        graphics.setColor(Color.BLACK);
        graphics.drawString(title, p.x + hitBox.x / 4, p.y + hitBox.y/3 + ascent);
    }
}

package org.dp.components.tiles;

import org.dp.assets.AssetFactory;
import org.dp.assets.FontLib;
import org.dp.assets.MapAssets;
import org.dp.event.GameEventBus;
import org.dp.event.LandedEvent.PlayerLandedOnStartTile;
import org.dp.event.LandedEvent.StartTileListener;
import org.dp.utils.Vector2i;

import java.awt.*;

public class StartTileComponent extends TileComponent {

    private Image img = ((MapAssets) AssetFactory.getAsset("mapAssets")).startTile;
    private String title = "起点";
    private int playerID;
    private String Price = "+ 1000";

    public StartTileComponent(Vector2i p, int playerID) {
        super(p);
        this.playerID = playerID;
        // 注册监听器
        // GameEventBus.get().registerListener(PlayerLandedOnStartTile.class, new StartTileListener());
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
        graphics.drawString(Price, p.x + hitBox.x / 4, p.y + 70 + ascent);
        graphics.drawString(title, p.x + hitBox.x / 4, p.y + 10 + ascent);
    }
}

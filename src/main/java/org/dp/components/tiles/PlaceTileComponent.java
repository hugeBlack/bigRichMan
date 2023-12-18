package org.dp.components.tiles;

import org.dp.assets.AssetFactory;
import org.dp.assets.FontLib;
import org.dp.assets.MapAssets;
import org.dp.utils.Vector2i;

import java.awt.*;

public class PlaceTileComponent extends TileComponent{
    private int price;
    private Color textColor = Color.WHITE;
    private boolean isInitialized = false;
    private Vector2i titleRelativePos = null;
    private String title;
    private Font font = ((FontLib)AssetFactory.getAsset("fontLib")).placePriceFont;
    private Image img = ((MapAssets) AssetFactory.getAsset("mapAssets")).placeTile;

    private int ascent;
    public PlaceTileComponent(Vector2i p, int price) {
        super(p, new Vector2i(100,100));
        title = "$ " + price;
    }

    @Override
    public void drawMe(Graphics graphics) {
        Vector2i p = getAbsPosition();
        Vector2i hitBox = getHitBoxSize();
        graphics.drawImage(img, p.x,p.y, 100,100,null);
        graphics.setColor(textColor);
        graphics.setFont(font);
        if(!isInitialized){
            int titleWidth = graphics.getFontMetrics().stringWidth(title);
            int titleHeight = graphics.getFontMetrics().getHeight();
            titleRelativePos = new Vector2i(-titleWidth / 2, -titleHeight / 2);
            ascent = graphics.getFontMetrics().getAscent();
            isInitialized = true;
        }
        graphics.drawString(title, p.x + hitBox.x/2 + titleRelativePos.x, p.y + 80 + titleRelativePos.y + ascent);
    }
}

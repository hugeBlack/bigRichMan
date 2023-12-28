package org.dp.components.tiles;

import org.dp.assets.AssetFactory;
import org.dp.assets.FontLib;
import org.dp.assets.MapAssets;
import org.dp.event.GameEventBus;
import org.dp.event.LandedEvent.HospitalTileListener;
import org.dp.event.LandedEvent.PlayerLandedOnHospitalTile;
import org.dp.utils.Vector2i;

import java.awt.*;

public class HospitalTileComponent extends TileComponent{
    private boolean isInitialized = false;
    private Vector2i titleRelativePos = null;
    private String title="医院";
    private String Price;
    private Font font = ((FontLib) AssetFactory.getAsset("fontLib")).placePriceFont;
    private Image img = ((MapAssets) AssetFactory.getAsset("mapAssets")).hospitalTile;

    private int ascent;
    public HospitalTileComponent(Vector2i p, int price) {
        super(p);
        Price ="停"+ price+"天";
        // 注册监听器
        //GameEventBus.get().registerListener(PlayerLandedOnHospitalTile.class, new HospitalTileListener());
    }

    @Override
    public void drawMe(Graphics graphics) {
        Vector2i p = getAbsPosition();
        Vector2i hitBox = getHitBoxSize();
        graphics.drawImage(img, p.x,p.y, 100,100,null);
        graphics.setColor(Color.black);
        graphics.setFont(font);
        if(!isInitialized){
            int titleWidth = graphics.getFontMetrics().stringWidth(title);
            int titleHeight = graphics.getFontMetrics().getHeight();
            titleRelativePos = new Vector2i(-titleWidth / 2, -titleHeight / 2);
            ascent = graphics.getFontMetrics().getAscent();
            isInitialized = true;
        }
        graphics.drawString(Price, p.x + hitBox.x/3 + titleRelativePos.x, p.y + 70 + titleRelativePos.y + ascent);
        graphics.drawString(title, p.x + hitBox.x/3 + titleRelativePos.x, p.y + 20 + titleRelativePos.y + ascent);
    }
}


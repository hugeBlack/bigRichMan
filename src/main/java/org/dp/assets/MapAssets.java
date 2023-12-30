package org.dp.assets;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MapAssets implements IAsset {
    public Image startTile;
    public Image placeTile;
    public Image eventTile;
    public Image hospitalTile;
    public Image storeTile;

    public MapAssets() {
        try {
            startTile = ImageIO.read(new File("./assets/tile/startTile.png"));
            placeTile = ImageIO.read(new File("./assets/tile/placeTile.png"));
            eventTile = ImageIO.read(new File("./assets/tile/eventTile.png"));
            hospitalTile = ImageIO.read(new File("./assets/tile/hospitalTile.png"));
            storeTile = ImageIO.read(new File("./assets/tile/storeTile.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

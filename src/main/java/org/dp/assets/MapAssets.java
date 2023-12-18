package org.dp.assets;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MapAssets implements IAsset{
    public Image startTile;
    public Image placeTile;

    public MapAssets(){
        try {
            startTile = ImageIO.read(new File("./assets/startTile.png"));
            placeTile = ImageIO.read(new File("./assets/placeTile.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

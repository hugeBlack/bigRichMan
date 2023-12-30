package org.dp.assets;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class BuildingAssets implements IAsset{
    public Image building[] = new Image[6];

    public BuildingAssets(){
        try {
            building[0] = ImageIO.read(new File("./assets/building/house01.png"));
            building[1] = ImageIO.read(new File("./assets/building/house02.png"));
            building[2] = ImageIO.read(new File("./assets/building/house03.png"));
            building[3] = ImageIO.read(new File("./assets/building/house04.png"));
            building[4] = ImageIO.read(new File("./assets/building/house05.png"));
            building[5] = ImageIO.read(new File("./assets/building/house06.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

package org.dp.assets;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class TitleSceneAssets implements IAsset{
    public Image background;
    public Image shanghai;
    public Image store;

    public TitleSceneAssets(){
        try {
            background = ImageIO.read(new File("./assets/background.jpg"));
            shanghai = ImageIO.read(new File("./assets/shanghai.jpg"));
            store = ImageIO.read(new File("./assets/store.jpg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

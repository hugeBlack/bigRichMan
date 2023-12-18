package org.dp.assets;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class TitleSceneAssets implements IAsset{
    public Image background;

    public TitleSceneAssets(){
        try {
            background = ImageIO.read(new File("./assets/background.jpg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

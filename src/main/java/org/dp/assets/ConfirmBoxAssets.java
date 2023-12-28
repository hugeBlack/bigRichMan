package org.dp.assets;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ConfirmBoxAssets implements IAsset{
    public Image background;
    public Color maskColor;
    public Font buttonFont;
    public Font infoFont;

    public ConfirmBoxAssets(){
        try {
            background = ImageIO.read(new File("./assets/message.png"));
            maskColor = new Color(0.0f,0.0f,0.0f,0.3f);
            buttonFont = new Font("黑体", 0, 20);
            infoFont = new Font("黑体", 0, 17);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

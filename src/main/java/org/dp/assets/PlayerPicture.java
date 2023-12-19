package org.dp.assets;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

// 具体享元
public class PlayerPicture implements IAsset {

    public Image[] img;

    // 这里是不是还能做成组合、装饰、外观？看各位发挥
    public PlayerPicture(){
        img = new Image[4];
        try {
            img[0] = ImageIO.read(new File("./assets/player/1.png"));
            img[1] = ImageIO.read(new File("./assets/player/2.png"));
            img[2] = ImageIO.read(new File("./assets/player/3.png"));
            img[3] = ImageIO.read(new File("./assets/player/4.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

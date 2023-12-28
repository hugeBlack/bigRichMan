package org.dp.assets;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

// 具体享元
public class TestComponentAssets implements IAsset {

    public Image img;

    // 这里是不是还能做成组合、装饰、外观？看各位发挥
    TestComponentAssets(){
        try {
            img = ImageIO.read(new File("./assets/player/2.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

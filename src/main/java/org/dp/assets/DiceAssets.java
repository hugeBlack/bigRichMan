package org.dp.assets;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class DiceAssets implements IAsset{
    public Image[] diceImage;

    public DiceAssets(){
        diceImage = new Image[6];
        try {
            diceImage[0] = ImageIO.read(new File("./assets/dice/1.png"));
            diceImage[1] = ImageIO.read(new File("./assets/dice/2.png"));
            diceImage[2] = ImageIO.read(new File("./assets/dice/3.png"));
            diceImage[3] = ImageIO.read(new File("./assets/dice/4.png"));
            diceImage[4] = ImageIO.read(new File("./assets/dice/5.png"));
            diceImage[5] = ImageIO.read(new File("./assets/dice/6.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

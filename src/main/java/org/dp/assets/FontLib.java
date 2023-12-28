package org.dp.assets;


import java.awt.*;

public class FontLib implements IAsset {
    public Font testFont;
    public Font titleButtonText;
    public Font placePriceFont;
    public Font playerNumberButton;


    FontLib(){
        testFont = new Font(null, 0, 100);
        titleButtonText = new Font("楷体", 0, 30);
        placePriceFont = new Font("楷体", 0, 20);
        playerNumberButton = new Font("楷体",0,50);
    }
}
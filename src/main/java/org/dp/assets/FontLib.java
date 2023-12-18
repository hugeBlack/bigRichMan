package org.dp.assets;


import java.awt.*;

public class FontLib implements IAsset {
    public Font testFont;
    public Font titleButtonText;
    public Font placePriceFont;


    FontLib(){
        testFont = new Font(null, 0, 100);
        titleButtonText = new Font("楷体", 0, 30);
        placePriceFont = new Font("楷体", 0, 20);
    }
}

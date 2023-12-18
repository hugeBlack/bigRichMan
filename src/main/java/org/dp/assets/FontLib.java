package org.dp.assets;


import java.awt.*;

public class FontLib implements IAsset {
    public Font testFont;
    public Font titleButtonText;


    FontLib(){
        testFont = new Font(null, 0, 100);
        titleButtonText = new Font("楷体", 0, 30);
    }
}

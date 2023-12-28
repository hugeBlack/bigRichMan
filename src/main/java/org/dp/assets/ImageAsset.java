package org.dp.assets;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.util.LinkedList;
import java.util.Map;

public class ImageAsset implements IAsset {
    //创建一个字典，用来存储图片和名字的映射
    private Map<String,Image> imageMap;
    public Image getImage(String name){
        return imageMap.get(name);
    }
    public ImageAsset(){
        imageMap = new java.util.HashMap<>();
        try {
            imageMap.put("board", ImageIO.read(new File("./assets/image/board.png")));
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}

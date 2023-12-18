package org.dp.assets;

import java.util.HashMap;

// 享元模式，加载图片
public class AssetFactory {
    public static HashMap<String, IAsset> assetMap=new HashMap<>();

    // 工厂应该不需要实例化？
    private AssetFactory(){}

    public static IAsset getAsset(String assetName){

        if(assetMap.containsKey(assetName))
            return assetMap.get(assetName);

        IAsset product;

        if(assetName.equals("player")) {
            product = new PlayerPicture();
        }else if(assetName.equals("fontLib")){
            product = new FontLib();
        }else{
            throw new RuntimeException("No such product!");
        }
        assetMap.put(assetName, product);
        return product;
    }
}

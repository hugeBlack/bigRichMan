package org.dp.components.tiles;

import org.dp.utils.Vector2i;

public class TileFactory {



    // 格子的类型，有需要自己加
    public enum TileType{
        START, // 起点
        PLACE, // 地点
        //OPPORTUNITY, // 机会
        //FATE, // 命运
        CORNER1, // 第1种角块
        //CORNER2, // 第2种角块
        //CORNER3, // 第3种角块
    }

    public TileFactory(){

    }

    public TileComponent getTile(TileType tileType, Vector2i position, int price){
        switch (tileType){
            case START -> {
                return new StartTileComponent(position);
            }
            case PLACE -> {
                return new PlaceTileComponent(position, price);
            }
            default -> throw new RuntimeException("Unknown tile type");
        }
    }
}

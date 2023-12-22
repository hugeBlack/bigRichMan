package org.dp.components.tiles;

import org.dp.utils.Vector2i;

public class TileFactory {



    // 格子的类型，有需要自己加
    public enum TileType{
        START, // 起点
        PLACE, // 地点
        EVENT, // 事件
        //OPPORTUNITY, // 机会
        //FATE, // 命运
        CORNER1, // 第1种角块
        //CORNER2, // 第2种角块
        //CORNER3, // 第3种角块
        HOSPITAL
    }

    public TileFactory(){

    }

    public TileComponent getTile(TileType tileType, Vector2i position, Integer param,String title){
        position=position.add(300,1000);
        switch (tileType){
            case START -> {
                return new StartTileComponent(position,param);
            }
            case PLACE -> {
                return new PlaceTileComponent(position, param,title);
            }
            case EVENT -> {
                return new EventTileComponent(position);
            }
            case HOSPITAL -> {
                return new HospitalTileComponent(position,param);
            }
            default -> throw new RuntimeException("Unknown tile type");
        }
    }
}

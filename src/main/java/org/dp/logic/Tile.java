package org.dp.logic;

import org.dp.components.tiles.TileComponent;

// 逻辑上的tile，这应该是个基类，具体自己扩展
public class Tile {

    public TileComponent component;
    public Tile(){

    }

    // 逆时针双向链表连接？
    public Tile next;
    public Tile prev;


}

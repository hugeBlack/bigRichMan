package org.dp.utils;

public class Vector2i {
    public int x;
    public int y;
    public Vector2i(int x, int y){
        this.x = x;
        this.y = y;
    }
    public Vector2i(Vector2i p){
        this.x = p.x;
        this.y = p.y;
    }

    public Vector2i add(int dx, int dy){
        return new Vector2i(x + dx, y + dy);
    }

}

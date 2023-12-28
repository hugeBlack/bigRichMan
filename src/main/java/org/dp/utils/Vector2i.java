package org.dp.utils;

public class Vector2i {
    public int x;

    @Override
    public String toString() {
        return "Vector2i{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

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

    public Vector2i sub(Vector2i relativePosition) {
        return new Vector2i(x - relativePosition.x, y - relativePosition.y);
    }
}

package org.dp.logic;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.dp.components.MapComponent;
import org.dp.components.tiles.*;
import org.dp.utils.Vector2i;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

// 使用生成器模式
public class MapBuilder {
    private Tile[] tiles;
    GameMap ans;

    public MapBuilder(String mapConfigJson, Vector2i pos) {
        TileFactory tileFactory = new TileFactory();
        ans = new GameMap();
        ans.mapComponent = new MapComponent(pos);
        ans.firstTile=new Tile[4];
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(mapConfigJson), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            StringBuffer resposeBuffer = new StringBuffer("");
            String lineTxt = null;
            String s = "";

            //将文件内容全部拼接到 字符串s
            while ((lineTxt = br.readLine()) != null) {
                s += lineTxt;
            }
            //将文件内容转换为JSON对象
            JSONObject object = JSON.parseObject(s);

            //找到JSON数组的声明
            JSONArray objs = object.getJSONArray("Tile");
            int tileNum = objs.size();
            tiles = new Tile[tileNum];
            //遍历obj对象数组，逐个输出。
            for (int i = 0; i < objs.size(); i++) {
                JSONObject object1 = objs.getJSONObject(i);
                String objType = object1.getString("type");
                int objX = Integer.parseInt(object1.getString("x"));
                int objY = Integer.parseInt(object1.getString("y"));
                if (objType.equals("start")) {
                    Tile tile1 = new Tile();
                    int playerID = Integer.parseInt(object1.getString("player"));
                    StartTileComponent startTileComponent = (StartTileComponent) tileFactory.getTile(TileFactory.TileType.START,
                            new Vector2i(objX, objY), playerID, "");
                    tile1.component = startTileComponent;
                    ans.firstTile [playerID]= tile1;
                    tiles[i] = tile1;
                    ans.mapComponent.addComponent(startTileComponent);
                }
                else if (objType.equals("place")) {
                    int price = Integer.parseInt(object1.getString("price"));
                    String objName = object1.getString("title");
                    PlaceTileComponent placeTileComponent1 = (PlaceTileComponent) tileFactory.getTile(TileFactory.TileType.PLACE,
                            new Vector2i(objX, objY), price, objName);
                    Tile tile2 = new Tile();
                    tile2.component = placeTileComponent1;
                    tiles[i] = tile2;
                    ans.mapComponent.addComponent(placeTileComponent1);
                }
                else if (objType.equals("event")) {
                    EventTileComponent eventTileComponent = (EventTileComponent) tileFactory.getTile(TileFactory.TileType.EVENT,
                            new Vector2i(objX, objY), 0, "");
                    Tile tile2 = new Tile();
                    tile2.component = eventTileComponent;
                    tiles[i] = tile2;
                    ans.mapComponent.addComponent( eventTileComponent);
                }
                else if (objType.equals("hospital")) {
                    Tile tile1 = new Tile();
                    int price = Integer.parseInt(object1.getString("price"));
                    HospitalTileComponent hospitalTileComponent = (HospitalTileComponent) tileFactory.getTile(TileFactory.TileType.HOSPITAL,
                            new Vector2i(objX, objY), price, "");
                    tile1.component = hospitalTileComponent;
                    tiles[i] = tile1;
                    ans.mapComponent.addComponent( hospitalTileComponent);
                }
                else if (objType.equals("store")) {
                    Tile tile1 = new Tile();
                    StoreTileComponent storeTileComponent = (StoreTileComponent) tileFactory.getTile(TileFactory.TileType.STORE,
                            new Vector2i(objX, objY), 0, "");
                    tile1.component = storeTileComponent;
                    tiles[i] = tile1;
                    ans.mapComponent.addComponent( storeTileComponent);
                }

              //   System.out.println( " type:" + objType + " name:" + objName + " x:" + objX + " y:" + objY);

            }
            ans.mapComponent.setHitBoxSize(new Vector2i(300, 300));
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public GameMap build(Vector2i pos) {
        for (int i = 0; i < tiles.length; i++) {
            if (i == 0) {
                tiles[i].prev = tiles[tiles.length - 1];
                tiles[i].next = tiles[i + 1];
            } else if (i == tiles.length - 1) {
                tiles[i].prev = tiles[i - 1];
                tiles[i].next = tiles[0];
            } else {
                tiles[i].prev = tiles[i - 1];
                tiles[i].next = tiles[i + 1];
            }
        }
        return ans;
    }
}

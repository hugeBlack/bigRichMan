package org.dp.assets;

import org.dp.components.DiceStrategy;
import org.dp.components.OneDiceStrategy;
import org.dp.components.ThreeDiceStrategy;
import org.dp.components.TwoDiceStrategy;
import org.dp.utils.Vector2i;

public class PlayerInfo  implements IAsset{
    public int id;
    public int coinNum;
    public int cardCarNum;
    public int cardLuckNum;
    public int houseNum;//房屋数量
    public String defaultName;
    public DiceStrategy strategy;
    public static PlayerInfo[] playerInfos = new PlayerInfo[6];//玩家信息数组
    public PlayerInfo(int id,int coinNum,int cardCarNum,int cardLuckNum,String defaultName){
        this.id = id;

        this.coinNum = coinNum;
        this.cardCarNum = cardCarNum;
        this.cardLuckNum =cardLuckNum;
        this.defaultName= defaultName;
    }
    public PlayerInfo(){
        setPlayerInfos();
    }
    public void setPlayerInfos(){
        playerInfos[0] = new PlayerInfo(0,7000,1,1,"菲菲公主");
        playerInfos[1] = new PlayerInfo(1,1000,2,1,"无敌忍者");
        playerInfos[2] = new PlayerInfo(2,3000,4,1,"惠惠");
        playerInfos[3] = new PlayerInfo(3,5000,6,1,"石油大亨");
        playerInfos[4] = new PlayerInfo(4,10000,8,1,"很厉害的小螃蟹");
        playerInfos[5] = new PlayerInfo(5,40000,6,1,"修狗");
    }
    public void updatePlayerInfo(int i,String type,int num){//i为玩家编号，type为更新的类型，num为更新的数值
        if(type.toLowerCase(). equals("coin")){
            playerInfos[i].coinNum = num;
        }
        else if(type.toLowerCase().equals("cardcar")){
            playerInfos[i].cardCarNum = num;
        }
        else if(type.toLowerCase().equals("cardluck")){
            playerInfos[i].cardLuckNum = num;
        }
        else if(type.toLowerCase().equals("house")){
            playerInfos[i].houseNum = num;
        }
        else if(type.toLowerCase().equals("strategy")){
            switch (num){
                case 1:
                    playerInfos[i].strategy = new OneDiceStrategy(new Vector2i(1400, 230));
                    break;
                case 2:
                    playerInfos[i].strategy = new TwoDiceStrategy(new Vector2i(1400, 230));
                    break;
                case 3:
                    playerInfos[i].strategy = new ThreeDiceStrategy(new Vector2i(1400, 230));
                    break;
                default:
                    break;
            }
        }
    }
    public PlayerInfo getPlayerInfo(int index){
        return playerInfos[index];
    }

}

package org.dp.assets;

import org.dp.components.DiceStrategy;
import org.dp.components.OneDiceStrategy;
import org.dp.components.ThreeDiceStrategy;
import org.dp.components.TwoDiceStrategy;
import org.dp.utils.Vector2i;

public class PlayerInfo  implements IAsset{
    public int id;        //玩家编号
    public int coinNum;   //金币数量
    public int couponNum; //点券数量
    public int cardCarNum;
    public int cardLuckNum;
    public int houseNum;  //房屋数量
    public String defaultName;  //默认名称
    public DiceStrategy strategy;
    public boolean inHospital = false;  //是否在医院
    public int forbidDay=0;
    public static PlayerInfo[] playerInfos = new PlayerInfo[6];//玩家信息数组

    public PlayerInfo(int id,int coinNum,int couponNum,int cardCarNum,int cardLuckNum,String defaultName){

        this.id = id;
        this.coinNum = coinNum;
        this.couponNum = couponNum;
        this.cardCarNum = cardCarNum;
        this.cardLuckNum =cardLuckNum;
        this.defaultName= defaultName;
        this.inHospital = false;
        this.forbidDay= 0;
        this.houseNum = 0;
    }

    public PlayerInfo(){
        setPlayerInfos();
    }

    public void setPlayerInfos(){
        playerInfos[0] = new PlayerInfo(0,7000,1000,3,1,"菲菲公主");
        playerInfos[1] = new PlayerInfo(1,1000,1000,2,2,"无敌忍者");
        playerInfos[2] = new PlayerInfo(2,3000,1000,1,3,"惠惠");
        playerInfos[3] = new PlayerInfo(3,5000,1000,1,1,"石油大亨");
        playerInfos[4] = new PlayerInfo(4,10000,1000,1,1,"很厉害的小螃蟹");
        playerInfos[5] = new PlayerInfo(5,40000,1000,1,1,"修狗");

    }

    // 更新玩家信息
    public static void updatePlayerInfo(int i, String type, int num){//i为玩家编号，type为更新的类型，num为更新的数值
        if(type.toLowerCase(). equals("coin")){
            playerInfos[i].coinNum = num;
        }
        else if(type.toLowerCase().equals("coupon")){
            playerInfos[i].couponNum = num;
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

    // 更新玩家是否在医院的信息，传的也是静态的写死的编号
    public void setInHospital(int i,boolean inHospital,int forbidDay){
        playerInfos[i].inHospital = inHospital;
        playerInfos[i].forbidDay = forbidDay;
    }
    public void reduceForbidDay(int i){
        if(playerInfos[i].forbidDay==1){
            playerInfos[i].inHospital = false;
            playerInfos[i].forbidDay=0;
        }
        else{
            playerInfos[i].forbidDay-=1;
        }
    }

    // 获取玩家信息
    public PlayerInfo getPlayerInfo(int index){
        return playerInfos[index];
    }

}

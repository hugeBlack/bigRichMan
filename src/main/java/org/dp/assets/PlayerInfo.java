package org.dp.assets;

public class PlayerInfo  implements IAsset{
    public int id;        //玩家编号
    public int coinNum;   //金币数量
    public int couponNum; //点券数量
    public int cardNum;   //卡片数量
    public int houseNum;  //房屋数量
    public String defaultName;  //默认名称
    public boolean inHospital = false;  //是否在医院
    public static PlayerInfo[] playerInfos = new PlayerInfo[6];//玩家信息数组
    public PlayerInfo(int id, int coinNum, int couponNum, int cardNum, String defaultName){
        this.id = id;
        this.coinNum = coinNum;
        this.couponNum = couponNum;
        this.cardNum = cardNum;
        this.defaultName= defaultName;
        this.inHospital = false;
    }

    public PlayerInfo(){
        setPlayerInfos();
    }

    public void setPlayerInfos(){
        playerInfos[0] = new PlayerInfo(0,7000,1000,1,"菲菲公主");
        playerInfos[1] = new PlayerInfo(1,1000,1000,2,"无敌忍者");
        playerInfos[2] = new PlayerInfo(2,3000,1000,4,"惠惠");
        playerInfos[3] = new PlayerInfo(3,5000,1000,6,"石油大亨");
        playerInfos[4] = new PlayerInfo(4,10000,1000,8,"很厉害的小螃蟹");
        playerInfos[5] = new PlayerInfo(5,40000,1000,6,"修狗");
    }

    // 更新玩家信息
    public void updatePlayerInfo(int i,String type,int num){//i为玩家编号，type为更新的类型，num为更新的数值
        if(type.toLowerCase(). equals("coin")){
            playerInfos[i].coinNum = num;
        }
        else if(type.toLowerCase().equals("coupon")){
            playerInfos[i].couponNum = num;
        }
        else if(type.toLowerCase().equals("card")){
            playerInfos[i].cardNum = num;
        }
        else if(type.toLowerCase().equals("house")){
            playerInfos[i].houseNum = num;
        }

    }

    // 更新玩家是否在医院的信息
    public void setInHospital(int i,boolean inHospital){
        playerInfos[i].inHospital = inHospital;
    }

    // 获取玩家信息
    public PlayerInfo getPlayerInfo(int index){
        return playerInfos[index];
    }

}

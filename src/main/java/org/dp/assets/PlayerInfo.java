package org.dp.assets;

public class PlayerInfo  implements IAsset{
    public int coinNum;
    public int cardNum;
    public String defaultName;
    public static PlayerInfo[] playerInfos = new PlayerInfo[6];
    public PlayerInfo(int coinNum,int cardNum,String defaultName){
        this.coinNum = coinNum;
        this.cardNum = cardNum;
        this.defaultName= defaultName;
    }
    public PlayerInfo(){
        setPlayerInfos();
    }
    public void setPlayerInfos(){
        playerInfos[0] = new PlayerInfo(7000,1,"菲菲公主");
        playerInfos[1] = new PlayerInfo(1000,2,"无敌忍者");
        playerInfos[2] = new PlayerInfo(3000,4,"惠惠");
        playerInfos[3] = new PlayerInfo(5000,6,"石油大亨");
        playerInfos[4] = new PlayerInfo(10000,8,"很厉害的小螃蟹");
        playerInfos[5] = new PlayerInfo(40000,6,"修狗");
    }
    public PlayerInfo getPlayerInfo(int index){
        return playerInfos[index];
    }
}

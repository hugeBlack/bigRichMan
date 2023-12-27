package org.dp.components.tiles;

import org.dp.assets.AssetFactory;
import org.dp.assets.FontLib;
import org.dp.assets.MapAssets;
import org.dp.event.GameEventBus;
import org.dp.event.LandedEvent.PlaceTileListener;
import org.dp.event.LandedEvent.PlayerLandedOnPlaceTile;
import org.dp.assets.BuildingAssets;
import org.dp.logic.GameSystem;
import org.dp.logic.Player;
import org.dp.utils.Vector2i;

import java.awt.*;

// PlaceTileComponent类：继承自TileComponent类；
// 功能：绘制地图上的地产
public class PlaceTileComponent extends TileComponent {
    private boolean isInitialized = false; // 是否已经初始化
    private Vector2i titleRelativePos = null; // 标题的相对位置
    private String title = "房屋"; // 标题
    private String Price; // 房屋初始价格
    private boolean purchasablity = true; // 是否可购买
    private int owner = -1; // 所有者
    private int toll = 0; // 初始过路费/初购费
    private int tollIncrease = 0; // 过路费增长值
    private int level = 0; // 等级
    private static int Max_Level = 5; // 房屋最大等级
    private Font font = ((FontLib) AssetFactory.getAsset("fontLib")).placePriceFont; // 价格字体
    private Image img = ((MapAssets) AssetFactory.getAsset("mapAssets")).placeTile; // 地产图片
    private Image building[] = ((BuildingAssets) AssetFactory.getAsset("buildingAssets")).building; // 房屋图片

    private int ascent; // 上升
    // 构造函数

    public PlaceTileComponent(Vector2i p, int price, String title) {
        super(p);
        Price = "$ " + price;
        this.toll = price;
        this.tollIncrease = price/2;  // 过路费增长值
        this.title = title;
        // 注册监听器
        // GameEventBus.get().registerListener(PlayerLandedOnPlaceTile.class, new PlaceTileListener());
    }

    // ...房屋所属相关方法...
    // isPurchasable方法：返回一个布尔值，表示该地块是否可购买
    public boolean isPurchasable() {
        return this.purchasablity;
    }

    // setPurchasable方法：设置该地块是否可购买
    public void setPurchasable(boolean purchasablity) {
        this.purchasablity = purchasablity;
    }

    // getOwner方法：获取该地块的所有者
    public int getOwner() {
        return this.owner;
    }

    // setOwner方法：设置该地块的所有者
    public void setOwner(int owner) {
        this.owner = owner;
    }

    // ...房屋升级相关的方法...
    // getLevel方法：获取该地块的等级
    public int getLevel() {
        return this.level;
    }

    // setLevel方法：设置该地块的等级
    public void setLevel(int level) {
        this.level = level;
    }

    // canUpLevel方法：返回一个布尔值，表示该地块是否可升级
    public boolean canUpLevel() {
        return this.level < Max_Level;
    }

    // ...房屋过路费相关的方法...
    // getToll方法：获取该地块的过路费
    public int getToll(int passerID) {
        if (this.owner == -1) {
            return this.toll;
        } else if (this.owner == passerID) {
            return this.toll * this.level;
        }
        return this.tollIncrease * this.level;
    }

    // ...房屋名称相关的方法...
    public String getTitle() {
        return this.title;
    }

    @Override
    // 重写drawMe方法
    public void drawMe(Graphics graphics) {
        Vector2i p = getAbsPosition(); // 获取绝对位置
        Vector2i hitBox = getHitBoxSize(); // 获取碰撞盒大小
        graphics.drawImage(img, p.x, p.y, 100, 100, null); // 绘制地产图片
        // 绘制地产图片，当level大于0时
        if (this.level > 0) {
            // 计算要绘制的图片部分
            int sx1 = (this.level - 1) * 60;
            int sx2 = this.level * 60;

            // 调用drawImage绘制图片的指定部分到目标位置
            graphics.drawImage(building[this.owner], p.x, p.y, p.x + 60, p.y + 100, sx1, 0, sx2, 100, null);
        }
        graphics.setColor(Color.black); // 设置颜色
        graphics.setFont(font); // 设置字体
        if (!isInitialized) {
            int titleWidth = graphics.getFontMetrics().stringWidth(title); // 获取标题宽度
            int titleHeight = graphics.getFontMetrics().getHeight(); // 获取标题高度
            titleRelativePos = new Vector2i(-titleWidth / 2, -titleHeight / 2); // 设置标题相对位置
            ascent = graphics.getFontMetrics().getAscent(); // 获取上升
            isInitialized = true; // 设置初始化完成
        }
        // 绘制价格和标题
        graphics.drawString(Price, p.x + hitBox.x / 3 + 10 + titleRelativePos.x,
                p.y + 70 + titleRelativePos.y + ascent);
        graphics.drawString(title, p.x + hitBox.x / 3 + 10 + titleRelativePos.x,
                p.y + 20 + titleRelativePos.y + ascent);

    }
}

package org.dp;

import org.dp.view.Playground;
import org.dp.view.TestScene;

import java.awt.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("sun.java2d.uiScale", "1");
        Playground playground = Playground.get();
        // 在show之前要布置好初始场景
        playground.addComponent(new TestScene());

        playground.showWindow();

    }
}
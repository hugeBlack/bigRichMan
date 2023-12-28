package org.dp.utils;

public class Time {
    public static long timeStarted = System.nanoTime();

    public static double getTimeInMs(){
        return (System.nanoTime() - timeStarted) / 1e6;
    }
}

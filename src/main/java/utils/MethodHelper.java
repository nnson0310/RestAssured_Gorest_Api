package utils;

import java.util.Random;

public class MethodHelper {

    public static int getZeroOrOne() {
        return new Random().nextInt(10) % 2;
    }

    public static String getSystemEnvironmentVariable(String variableName) {
        return System.getenv(variableName);
    }

    public static void sleepInSeconds(long seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

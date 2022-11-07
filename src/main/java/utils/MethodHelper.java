package utils;

import java.util.Random;

public class MethodHelper {

    public static int getZeroOrOne() {
        return new Random().nextInt(10) % 2;
    }

    public static String getSystemEnvironmentVariable(String variableName) {
        return System.getenv(variableName);
    }
}

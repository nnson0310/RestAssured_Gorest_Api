package commons;

import utils.ConfigHelper;
import utils.MethodHelper;

import java.nio.file.Paths;

public final class GlobalConstants {
    public static final String GORES_MAIN_API_URL = "https://gorest.co.in";

    public static final String configFileName = "config.properties";

    public static final String currentDir = System.getProperty("user.dir");

    public static final String pathToMainResource = Paths.get("src", "main", "resources").toFile().getAbsolutePath();

    public static final String pathToTestResource = Paths.get("src", "test", "resources").toFile().getAbsolutePath();

//    public static void main(String[] args) {
//        System.out.println(bearerToken);
//    }
}
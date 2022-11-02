package utils;

import commons.GlobalConstants;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigHelper {
    private final Properties properties = new Properties();
    private static ConfigHelper configHelper;

    public static ConfigHelper getInstance() {
        if (configHelper == null) {
            configHelper = new ConfigHelper();
            configHelper.readConfigFile();
        }
        return configHelper;
    }

    public String getProperty(String propertyName) {
        return properties.getProperty(propertyName);
    }

    private void readConfigFile() {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(GlobalConstants.pathToMainResource + File.separator + GlobalConstants.configFileName);
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

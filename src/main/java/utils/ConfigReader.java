package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    
    private static Properties properties;
    private static final String CONFIG_FILE_PATH = "src/main/resources/config.properties";
    
    static {
        try {
            FileInputStream fis = new FileInputStream(CONFIG_FILE_PATH);
            properties = new Properties();
            properties.load(fis);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load config.properties file");
        }
    }
    
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    public static String getUrl() {
        return properties.getProperty("base.url");
    }
    
    public static String getBrowser() {
        return properties.getProperty("browser", "chrome");
    }
    
    public static int getPageLoadTimeout() {
        return Integer.parseInt(properties.getProperty("page.load.timeout", "120"));
    }
    
    public static int getImplicitWait() {
        return Integer.parseInt(properties.getProperty("implicit.wait", "10"));
    }
    
    public static boolean shouldTakeScreenshot() {
        return Boolean.parseBoolean(properties.getProperty("screenshot.on.failure", "true"));
    }
}

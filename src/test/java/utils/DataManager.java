package utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class DataManager {

    public static String DOWNLOAD_PATH = System.getProperty("user.dir") + "/target/test-data/download";
    public static final String BROWSER = System.getProperty("Browser");
    public static final String BROWSER_MODE = System.getProperty("BrowserMode");
    public static final String BASE_URL = PropertiesManager.getProperty("BaseURL");//BaseURL fetched from config.properties file

    private static final Map<ThreadLocal<String>, String> properties = new HashMap<ThreadLocal<String>, String>();

    public static String getProperty(ThreadLocal<String> key) {
        return properties.get(key);
    }

    public static void setProperty(ThreadLocal<String> key, String value) {
        properties.put(key, value);
    }

    public int generateRandomInteger(int numberOfDigits) {
        Random random = new Random();

        int minValue = (int) Math.pow(10, numberOfDigits - 1);
        int maxValue = (int) Math.pow(10, numberOfDigits) - 1;

        return random.nextInt(maxValue - minValue + 1) + minValue;
    }

    public String generateRandomString(int length) {
        Random random = new Random();
        StringBuilder randomString = new StringBuilder();

        for (int i = 0; i < length; i++) {
            char randomChar = (char) ('a' + random.nextInt(26));
            randomString.append(randomChar);
        }

        return randomString.toString();
    }

}

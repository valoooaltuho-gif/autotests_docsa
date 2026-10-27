package common;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class PropertiesReader {

    protected final static Properties prop = new Properties();

    public static String getProperty(String key) {
        return prop.getProperty(key);
    }

    public static void readPropertiesFile() {
        try (InputStream inputStream = PropertiesReader.class.getClassLoader().getResourceAsStream("dynamic.properties")) {
            Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            prop.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package de.kgalli.bookstore.utils;

import org.yaml.snakeyaml.Yaml;

import java.util.Map;

public class YamlUtils {

    public static Map<String, Object> load(String filename) {
        var yaml = new Yaml();
        var inputStream = YamlUtils.class
                .getClassLoader()
                .getResourceAsStream(filename);

        return yaml.load(inputStream);
    }
}

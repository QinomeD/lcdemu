package qinomed.lcdemu;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class FontLoader {
    public static Map<Character, boolean[]> font = new HashMap<>();

    public static void loadFont(String name) {
        try {
            InputStream is = FontLoader.class.getResourceAsStream("/font/" + name);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            List<String> lines = reader.lines().filter(line -> !line.isEmpty()).toList();
            if (lines.size() % 2 != 0) {
                System.err.println("Error: Incorrect structure!");
            }

            for (int i = 0; i < lines.size(); i+=2) {
                String[] strArray = lines.get(i + 1).split(",");
                boolean[] arr = new boolean[strArray.length];
                for (int j = 0; j < strArray.length; j++) {
                    arr[j] = strArray[j].equals("1");
                }

                font.put(lines.get(i).charAt(0), arr);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

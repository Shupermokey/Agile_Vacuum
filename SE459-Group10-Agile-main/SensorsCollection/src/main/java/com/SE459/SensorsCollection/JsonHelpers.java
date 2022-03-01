package com.SE459.SensorsCollection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;

public class JsonHelpers {
    public static void writeJson(MapArchitecture m) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        // Convert the Java object to a JSON String:
        // Write the JSON object to a file:
        try (FileWriter writer = new FileWriter("pre_defined_map.json")) {
            gson.toJson(m, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readJson() {
        String ans = "";
        try {
            // read JASON Strings, so that they can be converted in to a Java object then.
            File jsonFile = new File("pre_defined_map.json");
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile));
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            ans = sb.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return ans;
    }

}


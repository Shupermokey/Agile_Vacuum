package com.SE459.Portal.Controller;

import com.google.gson.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.UUID;
import java.util.logging.Logger;

@Controller()
public class SensorSimulatorController {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @RequestMapping("/SensorSimulator")
    public ModelAndView simulate(HttpServletRequest req) {
        // Redirect To Login if we don't have a logged in User.
        if (req.getSession().getAttribute("email") == null){
            return new ModelAndView("redirect:/Login");
        }

        return new ModelAndView("SensorSimulator");

    }


    @PostMapping("/FloorPlan")
    @ResponseBody
    public String uploadFloorPlan(@RequestParam String floorPlan) {
        try {

            JsonElement floorJson = JsonParser.parseString(floorPlan);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            UUID uuid = UUID.randomUUID();

            String fileName = "floorplan-" + uuid + ".json";
            String fileLocation = new File("Portal\\src\\main\\resources\\static\\js").getAbsolutePath() + "\\" + fileName;
            FileWriter fileWriter = new FileWriter(fileLocation);

            // inject name of json floorplan to the name property in the json itself.
            floorJson.getAsJsonObject().addProperty("name", fileName);
            gson.toJson(floorJson, fileWriter);
            fileWriter.close();

            return fileName;
        }
        catch(Exception e) {
            logger.throwing(this.getClass().getName(), "uploadFloorPlan", e);
            return "";
        }
    }
}

package com.SE459.SensorsCollection;

import java.util.List;

public class MapManipulator {
    private MapArchitecture a;

    public MapManipulator(MapArchitecture a) {
        this.a = a;
    }


    //format: v/h-y-x-1/2
    //example:25-1-open;
    public void openOrCloseDoor(List<String> command) {
        for (String s : command) {
            String[] c = s.split("-");
            int x = Integer.parseInt(c[1]);
            int y = Integer.parseInt(c[0]);
            String cmd = c[2];
            if (y > 9) {
                y += 10;

            } else {
                x += 10;
            }
            a.walls[y][x] = cmd.equals("open") ? 2 : 1;
        }

    }

    //format: y-x
    //example: 2-3;
    public void addObstacles(List<String> command) {
        for (String s : command) {
            String[] c = s.split("-");
            int x = Integer.parseInt(c[1]);
            int y = Integer.parseInt(c[0]);
            a.graph[y][x] = 999;
        }
    }

}


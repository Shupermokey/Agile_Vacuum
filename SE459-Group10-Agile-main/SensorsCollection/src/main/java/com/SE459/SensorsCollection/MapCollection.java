package com.SE459.SensorsCollection;

public class MapCollection {
    public static MapArchitecture pickMap(int i) {
        if (i == 1) return map1();
        else return map1();

    }

    public static MapArchitecture map1() {
        MapArchitecture a = new MapArchitecture();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                a.graph[i][j] = 1;
            }
        }

        for (int i = 5; i < 10; i++) {
            for (int j = 0; j < 4; j++) {
                a.graph[i][j] = 1;
            }
        }

        for (int i = 4; i < 9; i++) {
            for (int j = 7; j < 9; j++) {
                a.graph[i][j] = 2;
            }
        }
        a.walls[3][25] = 1;
        for (int i = 0; i < 10; i++) {
            a.walls[i][45] = 1;

        }
        for (int i = 1; i < 10; i++) {
            a.walls[i][65] = 1;
        }
        for (int i = 0; i < 4; i++) {
            a.walls[35][i] = 1;

        }
        for (int i = 0; i < 4; i++) {
            a.walls[45][i] = 1;

        }
        for (int i = 0; i < 4; i++) {
            a.walls[55][i] = 1;

        }
        for (int i = 4; i < 6; i++) {
            a.walls[25][i] = 1;

        }
        for (int i = 4; i < 8; i++) {
            a.walls[15][i] = 1;
        }
        for (int i = 8; i < 10; i++) {
            a.walls[35][i] = 1;

        }
        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 10; ++j) {
                a.dirt[i][j] = 1;
            }
        }

        a.walls[0][85] = 1;
        a.walls[1][85] = 1;
        a.walls[2][85] = 1;
        a.graph[9][4] = 999;
        //doors
        a.walls[35][1] = 2;
        a.walls[25][5] = 2;
        a.walls[15][7] = 2;
        a.walls[55][2] = 2;
        a.walls[2][45] = 2;
        a.walls[3][45] = 2;
        a.walls[6][45] = 2;
        a.walls[2][65] = 2;
        a.walls[1][85] = 2;
        a.graph[9][0] = 777;
        a.walls[95][4] = 1;
        a.walls[9][55] = 1;
        return a;

    }
}

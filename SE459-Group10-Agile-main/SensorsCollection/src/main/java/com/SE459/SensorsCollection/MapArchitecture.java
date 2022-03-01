package com.SE459.SensorsCollection;

public class MapArchitecture {
    public int[][] graph = new int[10][10];
    public int[][] dirt = new int[10][10];
    public int[][] walls = new int[100][100];

    public int[][] getGraph() {
        return graph;
    }

    public int[][] getDirty() {
        return dirt;
    }

    public void setGraph(int[][] g) {
        graph = g;
    }

    public void setDirt(int[][] d) {
        dirt = d;
    }

    public int[][] getWalls() {
        return walls;
    }

}

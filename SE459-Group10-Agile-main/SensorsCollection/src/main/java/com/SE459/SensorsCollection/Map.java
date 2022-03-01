package com.SE459.SensorsCollection;

import com.google.gson.Gson;

class Map {
    public int[][] graph;
    public int[][] dirty;
    public int[][] walls;
    private MapArchitecture cur;

    public Map() {
        String jsonStr = JsonHelpers.readJson();
        cur = new Gson().fromJson(jsonStr, MapArchitecture.class);
        graph = cur.getGraph();
        dirty = cur.getDirty();
        walls = cur.getWalls();
    }

//    public void updateJsonFile() {
//        cur.setGraph(graph);
//        cur.setDirt(dirty);
//        json.writeJson(cur);
//    }
}


package com.SE459.SensorsCollection;

import java.util.ArrayList;
import java.util.List;

public class SensorsCollection {
    private Map map;
    private MapArchitecture a;


    public void generateBasicMap(int index) {
        a = MapCollection.pickMap(index);
        JsonHelpers.writeJson(a);
        map = new Map();
    }

    public void runFinalMap() {
        JsonHelpers.writeJson(a);
        map = new Map();
    }

    public MapArchitecture getMapArch() {
        return a;
    }


    //navigation
    //0 invalid
    //1 true
    //2 false
    //3 facing a obstacle;
    //4 facing a closed door
    private int goForward(int y, int x) {
        int targetY = y - 1;
        if (targetY < 0 || targetY > 9) return 2;
        int num = (targetY * 10 + y * 10) / 2 + 10;
        //no wall here

        if (map.walls[num][x] == 0) {
            return 1;
        } else if (map.walls[num][x] == 1) {
            return 2;
        }
        //door opening
        else if (map.walls[num][x] == 2) {
            return 1;
        } else if (map.walls[num][x] == 4) {
            return 4;
        }
        //door closing
        else return 3;
    }

    private int goRight(int y, int x) {
        int targetX = x + 1;
        if (targetX < 0 || targetX > 9) return 2;


        int num = (targetX * 10 + x * 10) / 2 + 10;

        //no wall here

        if (map.walls[y][num] == 0) return 1;
            //facing a wall
        else if (map.walls[y][num] == 1) {
            return 2;
        }
        //door opening
        else if (map.walls[y][num] == 2) return 1;
            //door closing
        else if (map.walls[y][num] == 4) {
            return 4;
        } else return 3;
    }

    private int goLeft(int y, int x) {
        int targetX = x - 1;
        if (targetX < 0 || targetX > 9) return 2;
        int num = (targetX * 10 + x * 10) / 2 + 10;
        //no wall here

        if (map.walls[y][num] == 0) return 1;
            //facing a wall
        else if (map.walls[y][num] == 1) return 2;
            //door opening
        else if (map.walls[y][num] == 2) return 1;
            //door closing
        else if (map.walls[y][num] == 4) {
            return 4;
        } else return 3;
    }

    private int goBackward(int y, int x) {
        int targetY = y + 1;
        if (targetY < 0 || targetY > 9) return 2;
        int num = (targetY * 10 + y * 10) / 2 + 10;
        //no wall here

        if (map.walls[num][x] == 0) {
            return 1;
        }
        //facing a wall
        else if (map.walls[num][x] == 1) {
            return 2;
        }
        //door opening
        else if (map.walls[num][x] == 2) {
            return 1;
        }
        //door closing
        else if (map.walls[num][x] == 4) {
            return 4;
        } else return 3;
    }


    //dirt Sensor
    private boolean isDirty(int y, int x) {
        return map.dirty[y][x] > 0;
    }

    private void clean(int y, int x) {
        map.dirty[y][x]--;
    }

    //Surface Sensor
    private int surface(int y, int x) {
        return map.graph[y][x];
    }

    //Charging Station
    private Integer[] nearAChargingStation(int y, int x) {
        int[][] dir = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}, {2, 0}, {0, 2}, {-2, 0}, {0, -2}};
        for (int[] d : dir) {
            int tempX = x + d[1];
            int tempY = y + d[0];
            if (tempX < 0 || tempX > 9 || tempY < 0 || tempY > 9) continue;
            if (map.graph[y + d[0]][x + d[1]] == 777) {
                Integer[] ans = new Integer[2];
                ans[0] = y + d[0];
                ans[1] = x + d[1];
                return ans;
            }
        }
        return null;
    }

    private static String area(int y, int x) {
        if (x <= 3 && y <= 2) return "Guest Bedroom B";
        if (x > 3 && x <= 7 && y == 0) return "Bathroom 1";
        if (x >= 8 && x <= 9 && y <= 3) return "Closet";
        if (x >= 4 && x <= 5 && y == 1) return "Bathroom 2";
        if (x <= 1 && y == 3) return "Closet 1";
        if (x >= 2 && x <= 3 && y == 3) return "Closet 2";
        if (x <= 3 && y == 4) return "Closet 3";
        if (x <= 3 && y <= 9) return "Guest Bedroom A";
        if (x >= 4 && x <= 5 && y >= 2 && y <= 9) return "Hallway";
        return "Master Bedroom";
    }

    //dirt tank - 50
    //power - 250
    //full go back
    //empty go back
    double battery;
    int tank;

    boolean[][] v;
    int length = Integer.MAX_VALUE;
    List<String> back;

    public ArrayList<String> clean() {
        battery = 50;
        tank = 50;
        v = new boolean[10][10];
        ArrayList<String> p = new ArrayList<>();
        v[9][0] = true;
        p.add("Starts from (9, 0)" + " battery :" + (int) battery + "/250" + " tank : " + tank + "/50");
        dfs(p, 9, 0);
        for (String s : p) {
            System.out.println(s);
        }
        return p;
    }

    private void dfs(List<String> path, int y, int x) {
        for (int i = 0; i < 4; ++i) {
            int tempY;
            int tempX;
            String dir = "";
            if (i == 0) {
                dir = "goingUp";
                if (goForward(y, x) == 2) continue;
                else if (goForward(y, x) == 3) {
//                    path.add(" obstacle detected ! ");
                    continue;
                }
                tempX = x;
                tempY = y - 1;
            } else if (i == 1) {
                dir = "goingDown";
                if (goBackward(y, x) == 2) continue;
                else if (goBackward(y, x) == 3) {
//                    path.add(" obstacle detected ! ");
                    continue;
                }
                tempX = x;
                tempY = y + 1;
            } else if (i == 2) {
                dir = "goingRight";
                if (goRight(y, x) == 2) continue;
                else if (goRight(y, x) == 3) {
//                    path.add(" obstacle detected ! ");
                    continue;
                }
                tempX = x + 1;
                tempY = y;
            } else {
                dir = "goingLeft";
                if (goLeft(y, x) == 2) continue;
                else if (goLeft(y, x) == 3) {
//                    path.add(" obstacle detected ! ");
                    continue;
                }
                tempX = x - 1;
                tempY = y;
            }


            if (v[tempY][tempX]) continue;
            v[tempY][tempX] = true;

            int surCur = surface(y, x) == 777 ? 1 : surface(y, x);
            int surNext = surface(tempY, tempX) == 777 ? 1 : surface(tempY, tempX);
            double consume = ((double) surCur + 1 + surNext + 1) / 2;

            //cleaning process
            while (isDirty(y, x)) {
                //check if the tank is enough to do one vacuum operation.
                if (tank <= 1) {
                    double rest = enoughPower(y, x, battery);
                    path.add("");
                    path.add("The dirt tank has been full");
                    path.addAll(back);
                    path.add("Arriving at the charging station");
                    path.add("emptying ///////////////");
                    path.add("going back to the break point");
                    path.add("");
                    tank = 50;
                }
                //check if the battery is enough to do one vacuum operation.
                if (battery <= surCur + 1) {
                    double rest = enoughPower(y, x, battery);
                    path.add("");
                    path.add("The rest of the battery is not enough, going back to charging station now");
//                    System.out.println("The rest of the battery is not enough, going back to charging station now");
                    path.addAll(back);
                    path.add("Arriving at the charging station");
                    path.add("charging ///////////////");
                    path.add("going back to the break point");
                    path.add("");
                    battery = 250 - (battery - rest);
                }
                clean(y, x);
                tank -= 1;
            }
            //check if the battery is enough to move one position and go back to the charging station.
            if (battery < 50) {
                double rest = enoughPower(y, x, battery);
                if (rest <= consume) {
                    enoughPower(y, x, battery);
                    path.add("");
                    path.add("The rest of the battery is not enough, going back to charging station now");
//                    System.out.println("The rest of the battery is not enough, going back to charging station now");
                    path.addAll(back);
                    path.add("Arriving at the charging station");
                    path.add("charging ///////////////");
                    path.add("going back to the break point");
                    path.add("");
                    battery = 250 - (battery - rest);

                }
            }


            battery -= consume;
            path.add(dir + " to (" + tempY + ", " + tempX + ")" + " battery: " + (int) battery + "/250" + " tank : " + tank + "/50");
//            System.out.println(dir + " to (" + tempY + ", " + tempX + ")" + (int) battery + "/250" + " tank : " + tank + "/50");

//          call primary dfs search
            dfs(path, tempY, tempX);

            if (battery < 50) {
                double rest2 = enoughPower(tempY, tempX, battery);
                if (rest2 <= consume) {
                    path.add("");
                    path.add("The rest of the battery is not enough, going back to charging station now");
                    path.addAll(back);
                    path.add("Arriving at the charging station");
                    path.add("charging ///////////////");
                    path.add("going back to the break point");
                    path.add("");
//                    System.out.println("The rest of the battery is not enough, going back to charging station now");
                    battery = 250 - (battery - rest2);
                }
            }

            battery -= consume;
            path.add("back to: (" + y + ", " + x + ")" + " battery: " + (int) battery + "/250" + " tank : " + tank + "/50");
//            System.out.println("back to: (" + y + ", " + x + ")" + (int) battery + "/250" + " tank : " + tank + "/50");

        }
    }


    private double enoughPower(int y, int x, double rest) {
        if (y == 9 && x == 0) return rest;
        length = Integer.MAX_VALUE;
        back = new ArrayList<>();
        boolean[][] b = new boolean[10][10];
        b[y][x] = true;
        back(y, x, 9, 0, 0, new ArrayList<>(), b, rest);
        if (back.size() == 0) {
            return -1;
        }
        String part = back.get(back.size() - 1);
        int ind = 0;
        for (int i = part.length() - 1; i >= 0; --i) {
            if (part.charAt(i) == '/') ind = i;
            if (part.charAt(i) == ':') {
                rest = Double.parseDouble(part.substring(i + 2, ind));
                break;
            }
        }
        return rest;
    }

    private void back(int y, int x, int cy, int cx, int len, List<String> log, boolean[][] vi, double rest) {
        if (rest < 0) return;
        if (len >= length) return;
        if (x == cx && y == cy) {
            length = len;
            back.clear();
            back.addAll(log);
            return;
        }
        for (int i = 0; i < 4; ++i) {
            int tempY;
            int tempX;
            String dir = "";
            if (i == 0) {
                dir = "goingUp";
                if (goForward(y, x) == 2) continue;
                else if (goForward(y, x) == 3) {
                    continue;
                }
                tempX = x;
                tempY = y - 1;
            } else if (i == 1) {
                dir = "goingDown";
                if (goBackward(y, x) == 2) continue;
                else if (goBackward(y, x) == 3) {
                    continue;
                }
                tempX = x;
                tempY = y + 1;
            } else if (i == 2) {
                dir = "goingRight";
                if (goRight(y, x) == 2) continue;
                else if (goRight(y, x) == 3) {
                    continue;
                }
                tempX = x + 1;
                tempY = y;
            } else {
                dir = "goingLeft";
                if (goLeft(y, x) == 2) continue;
                else if (goLeft(y, x) == 3) {
                    continue;
                }
                tempX = x - 1;
                tempY = y;
            }
            if (vi[tempY][tempX]) continue;
            int surCur = surface(y, x);
            int surNext = surface(tempY, tempX);
            if (surNext == 777) surNext = 1;
            double consume = ((double) surCur + 1 + surNext + 1) / 2;
            rest -= consume;
            log.add("Back to Charging station: " + dir + " to (" + tempY + ", " + tempX + ")" + " battery : " + rest + "/250");
            vi[tempY][tempX] = true;
            back(tempY, tempX, 9, 0, len + 1, log, vi, rest);
            log.remove(log.size() - 1);
            rest += consume;
            vi[tempY][tempX] = false;
        }
    }
}

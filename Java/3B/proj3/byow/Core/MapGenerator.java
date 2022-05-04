package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
//import edu.princeton.cs.introcs.StdDraw;

//import java.awt.*;
import java.io.Serializable;

import java.util.ArrayList;


import java.util.List;
import java.util.Random;

/**
 * @author Henry Kang
 * @author Taemin Kim
 * To make the map.
 * */
public class MapGenerator<Calender> implements Serializable {
    /** Map width.*/
    private static final int WIDTH = 55;
    /** Map height.*/
    private static final int HEIGHT = 55;
    /** RandomUtils setting.*/
    private Random random;
    /** World Grid.*/
    private TETile[][] world;
    /** TERenderer.*/
    private TERenderer ter;

    /** ArrayList type of the Rooms.*/
    private static final ArrayList<Rooms> INITIALLIST = new ArrayList<>();

    /** number of rooms.*/
    private int numRooms;

    /** number of vertical hallway type of rooms. */
    private int numVerHall;

    /** number of horizontal hallway type of rooms.*/
    private int numHorHall;

    /** number of crossroad type of rooms.*/
    private int numCrossRoad;

    TETile player = Tileset.AVATAR;
    int initXPos;
    int initYPos;
    int curXPos;
    int curYPos;



    /**
     * initiate for the world generator.
     * @param seed Seed value.
     * @return return generated world.*/
    public void init(long seed) {
        Closest clo = new Closest();
        random = new Random(seed);


        //ter = new TERenderer();
        //ter.initialize(WIDTH, HEIGHT);

        world = new TETile[WIDTH][HEIGHT];

        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }


        numRooms();
        makeCrossRoad();
        makeRoom();
        drawVerticalHallway();
        drawHorizontalHallway();

        List<Object> getFirstInfo = clo.getSmallestInfo(INITIALLIST);
        List<Object> getInfo = clo.getSmallestInfo(
                (ArrayList) getFirstInfo.get(0), (Rooms) getFirstInfo.get(1));
        connect((Rooms) getFirstInfo.get(1), (Rooms) getInfo.get(1));
        Rooms tempRooms = (Rooms) getInfo.get(1);
        ArrayList<Rooms> tempList = (ArrayList) getInfo.get(0);

        for (int i = 0; i < INITIALLIST.size() - 2; i++) {
            getInfo = clo.getSmallestInfo(tempList, tempRooms);
            connect(tempRooms, (Rooms) getInfo.get(1));
            tempList = (ArrayList) getInfo.get(0);
            tempRooms = (Rooms) getInfo.get(1);
        }
        fillEntireGrid();

        initAvatar(seed);





        //ter.renderFrame(world);


    }

    /** create the CrossRoad.*/
    public void makeCrossRoad() {
        for (int i = 0; i < numCrossRoad; i++) {
            int width = RandomUtils.uniform(random, 2, 5);
            int height = RandomUtils.uniform(random, 3, 5);
            int xPos = RandomUtils.uniform(random, 10, 39);
            int yPos = RandomUtils.uniform(random, 10, 39);
            fillOutCrossRoad(world, xPos, yPos, width, height);
            INITIALLIST.add(new Rooms(xPos, yPos, width, height));
        }
    }


    /** setting for the number of rooms.*/
    public void numRooms() {
        numRooms = RandomUtils.uniform(random, 50, 60);
        numVerHall = RandomUtils.uniform(random, 13, 16);
        numHorHall = RandomUtils.uniform(random, 12, 17);
        numCrossRoad = RandomUtils.uniform(random, 2, 4);
    }

    /** create the rooms.*/
    public void makeRoom() {
        for (int i = 0; i < numRooms; i++) {
            int width = RandomUtils.uniform(random, 4, 9);
            int height = RandomUtils.uniform(random, 4, 9);
            int xPos = RandomUtils.uniform(random, 2, 39);
            int yPos = RandomUtils.uniform(random, 2, 39);

            if (checkingOverlap(world, xPos, yPos, width, height)) {
                fillOutFloor(world, xPos, yPos, width, height);
                INITIALLIST.add(new Rooms(xPos, yPos, width, height));
            }
        }
    }

    /** creates the vertical hallway.*/
    public void drawVerticalHallway() {
        for (int i = 0; i < numVerHall; i++) {
            int width = 1;
            int height = RandomUtils.uniform(random, 5, 9);
            int xPos = RandomUtils.uniform(random, 2, 43);
            int yPos = RandomUtils.uniform(random, 2, 38);
            if (checkingOverlap(world, xPos, yPos, width, height)) {
                fillOutFloor(world, xPos, yPos, width, height);
                INITIALLIST.add(new Rooms(xPos, yPos, width, height));
            }
        }
    }

    /** creates the horizontal hallway.*/
    public void drawHorizontalHallway() {
        for (int i = 0; i < numHorHall; i++) {
            int width = RandomUtils.uniform(random, 4, 9);
            int height = 1;
            int xPos = RandomUtils.uniform(random, 2, 38);
            int yPos = RandomUtils.uniform(random, 2, 39);
            if (checkingOverlap(world, xPos, yPos, width, height)) {
                fillOutFloor(world, xPos, yPos, width, height);
                INITIALLIST.add(new Rooms(xPos, yPos, width, height));
            }
        }
    }


    /** checking the overlap rooms.
     * @return whether true or false for the overlap rooms.
     * @param x xPos.
     * @param y yPos.
     * @param width width.
     * @param height height.
     * @param world world grid.*/
    public static boolean checkingOverlap(TETile[][] world,
                                          int x, int y, int width, int height) {
        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                if (world[i][j].equals(Tileset.FLOOR)) {
                    return false;
                }
            }
        }
        return true;
    }

    /** fill out the floors.
     * @param x xPos.
     * @param y yPos.
     * @param width width.
     * @param height height.
     * @param world world grid.*/
    public static void fillOutFloor(TETile[][] world,
                                    int x, int y, int width, int height) {
        for (int n = x; n < x + width; n++) {
            for (int m = y; m < y + height; m++) {
                world[n][m] = Tileset.FLOOR;
            }
        }
    }

    /** fill out the crossroad in different way. Since it's crossRoad.
     * @param x xPos.
     * @param y yPos.
     * @param width width.
     * @param height height.
     * @param world world grid.*/
    public static void fillOutCrossRoad(TETile[][] world,
                                        int x, int y, int width, int height) {
        for (int n = x - width; n < x + width; n++) {
            for (int m = y - height; m < y + height; m++) {
                world[n][m] = Tileset.FLOOR;
            }
        }
    }

    /** Connect the Room to Room.
     * @param start start Rooms.
     * @param target target Rooms.*/
    public void connect(Rooms start, Rooms target) {
        int startX = start.getXPos();
        int startY = start.getYPos();
        int targetX = target.getXPos();
        int targetY = target.getYPos();
        int gapX = targetX - startX;
        int gapY = targetY - startY;

        if (gapX >= 0) {
            for (int x = 1; x <= gapX; x++) {
                world[startX + x][startY] = Tileset.FLOOR;
            }
            if (gapY >= 0) {
                for (int y = 1; y <= gapY; y++) {
                    world[startX + gapX][startY + y] = Tileset.FLOOR;
                }
            }
            if (gapY < 0) {
                for (int y = 1; y <= -gapY; y++) {
                    world[startX + gapX][targetY + y] = Tileset.FLOOR;
                }
            }
        }


        if (gapX < 0) {
            for (int x = 1; x <= -gapX; x++) {
                world[targetX + x][targetY] = Tileset.FLOOR;
            }
            if (gapY >= 0) {
                for (int y = 1; y <= gapY; y++) {
                    world[targetX - gapX][startY + y] = Tileset.FLOOR;
                }
            }
            if (gapY < 0) {
                for (int y = 1; y <= -gapY; y++) {
                    world[targetX - gapX][targetY + y] = Tileset.FLOOR;
                }
            }
        }
    }

    /** fill to the wall.*/
    public void fillEntireGrid() {
        for (int i = 1; i < WIDTH - 1; i++) {
            for (int j = 1; j < HEIGHT - 1; j++) {
                if (!world[i][j].equals(Tileset.FLOOR)) {
                    if (world[i - 1][j - 1].equals(Tileset.FLOOR)) {
                        world[i][j] = Tileset.WALL;
                    }
                    if (world[i][j - 1].equals(Tileset.FLOOR)) {
                        world[i][j] = Tileset.WALL;
                    }
                    if (world[i + 1][j - 1].equals(Tileset.FLOOR)) {
                        world[i][j] = Tileset.WALL;
                    }

                    if (world[i - 1][j].equals(Tileset.FLOOR)) {
                        world[i][j] = Tileset.WALL;
                    }
                    if (world[i + 1][j].equals(Tileset.FLOOR)) {
                        world[i][j] = Tileset.WALL;
                    }
                    if (world[i - 1][j + 1].equals(Tileset.FLOOR)) {
                        world[i][j] = Tileset.WALL;
                    }
                    if (world[i][j + 1].equals(Tileset.FLOOR)) {
                        world[i][j] = Tileset.WALL;
                    }
                    if (world[i + 1][j + 1].equals(Tileset.FLOOR)) {
                        world[i][j] = Tileset.WALL;
                    }
                }
            }
        }
    }

    public void initAvatar(long seed) {


        //TERenderer ter = new TERenderer();


        initXPos = RandomUtils.uniform(random, 1, 49);
        initYPos = RandomUtils.uniform(random, 1, 48);
        long tempSeed = seed;
        while (!world[initXPos][initYPos].equals(Tileset.FLOOR)) {
            tempSeed += 1;
            Random tempRandom = new Random(tempSeed);
            initXPos = RandomUtils.uniform(tempRandom, 1, 49);
            initYPos = RandomUtils.uniform(tempRandom, 1, 48);
        }
        curXPos = initXPos;
        curYPos = initYPos;

        world[curXPos][curYPos] = player;


        //ter.renderFrame(world);
    }

    public void moveUp() {
        if (world[curXPos][curYPos + 1].equals(Tileset.FLOOR)) {
            world[curXPos][curYPos + 1] = Tileset.AVATAR;
            world[curXPos][curYPos] = Tileset.FLOOR;
            curYPos = curYPos + 1;
        }

        //ter.renderFrame(world);
    }

    public void moveDown() {
        if (world[curXPos][curYPos - 1].equals(Tileset.FLOOR)) {
            world[curXPos][curYPos - 1] = Tileset.AVATAR;
            world[curXPos][curYPos] = Tileset.FLOOR;
            curYPos = curYPos - 1;
        }
        //ter.renderFrame(world);
    }

    public void moveLeft() {
        if (world[curXPos - 1][curYPos].equals(Tileset.FLOOR)) {
            world[curXPos - 1][curYPos] = Tileset.AVATAR;
            world[curXPos][curYPos] = Tileset.FLOOR;
            curXPos = curXPos - 1;
        }
        //ter.renderFrame(world);
    }

    public void moveRight() {
        if (world[curXPos + 1][curYPos].equals(Tileset.FLOOR)) {
            world[curXPos + 1][curYPos] = Tileset.AVATAR;
            world[curXPos][curYPos] = Tileset.FLOOR;
            curXPos = curXPos + 1;
        }
        //ter.renderFrame(world);
    }

    public void moveUpWithoutShow() {
        if (world[curXPos][curYPos + 1].equals(Tileset.FLOOR)) {
            world[curXPos][curYPos + 1] = Tileset.AVATAR;
            world[curXPos][curYPos] = Tileset.FLOOR;
            curYPos = curYPos + 1;
        }
    }

    public void moveDownWithoutShow() {
        if (world[curXPos][curYPos - 1].equals(Tileset.FLOOR)) {
            world[curXPos][curYPos - 1] = Tileset.AVATAR;
            world[curXPos][curYPos] = Tileset.FLOOR;
            curYPos = curYPos - 1;
        }
    }

    public void moveLeftWithoutShow() {
        if (world[curXPos - 1][curYPos].equals(Tileset.FLOOR)) {
            world[curXPos - 1][curYPos] = Tileset.AVATAR;
            world[curXPos][curYPos] = Tileset.FLOOR;
            curXPos = curXPos - 1;
        }
    }

    public void moveRightWithoutShow() {
        if (world[curXPos + 1][curYPos].equals(Tileset.FLOOR)) {
            world[curXPos + 1][curYPos] = Tileset.AVATAR;
            world[curXPos][curYPos] = Tileset.FLOOR;
            curXPos = curXPos + 1;
        }
    }

    public void moveUpReplay() {
        if (world[curXPos][curYPos + 1].equals(Tileset.FLOOR)) {
            world[curXPos][curYPos + 1] = Tileset.AVATAR;
            world[curXPos][curYPos] = Tileset.FLOOR;
            curYPos = curYPos + 1;
        }
        //ter.renderFrame(world);
        //StdDraw.pause(500);
    }

    public void moveDownReplay() {
        if (world[curXPos][curYPos - 1].equals(Tileset.FLOOR)) {
            world[curXPos][curYPos - 1] = Tileset.AVATAR;
            world[curXPos][curYPos] = Tileset.FLOOR;
            curYPos = curYPos - 1;
        }
        //ter.renderFrame(world);
        //StdDraw.pause(500);
    }

    public void moveLeftReplay() {
        if (world[curXPos - 1][curYPos].equals(Tileset.FLOOR)) {
            world[curXPos - 1][curYPos] = Tileset.AVATAR;
            world[curXPos][curYPos] = Tileset.FLOOR;
            curXPos = curXPos - 1;
        }
        //ter.renderFrame(world);
        //StdDraw.pause(500);
    }

    public void moveRightReplay() {
        if (world[curXPos + 1][curYPos].equals(Tileset.FLOOR)) {
            world[curXPos + 1][curYPos] = Tileset.AVATAR;
            world[curXPos][curYPos] = Tileset.FLOOR;
            curXPos = curXPos + 1;
        }
        //ter.renderFrame(world);
        //StdDraw.pause(500);
    }

    public TETile[][] getWorld() {
        return this.world;
    }

    /*public void hud(String language) {
        HUD hud = new HUD(getWorld(), language);

        String type = hud.mouseOnTile();
        //StdDraw.setPenColor(Color.WHITE);
        //StdDraw.textRight(WIDTH - 2, HEIGHT - 2, type);
        //StdDraw.line(0, this.HEIGHT - 3, this.WIDTH, this.HEIGHT - 3);
        //StdDraw.show();


    }*/


}


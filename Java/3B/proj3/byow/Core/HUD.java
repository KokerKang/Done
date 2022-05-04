package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

public class HUD {
    private String language = "English";
    private TETile[][] world;
    public HUD(TETile[][] world, String language) {
        this.world = world;
        this.language = language;
    }

    public String mouseOnTile() {
        int x = (int) StdDraw.mouseX();
        int y = (int) StdDraw.mouseY();
        String tile = "";
        if (language.equals("English")) {
            tile = languageEnglish(x, y);
        } else if (language.equals("Spanish")) {
            tile = languageSpanish(x, y);
        } else if (language.equals("Korean")) {
            tile = languageKorean(x, y);
        }
        return tile;
    }

    public String languageEnglish(int x, int y) {
        if (world[x][y].equals(Tileset.AVATAR)) {
            return "Avatar";
        } else if (world[x][y].equals(Tileset.WALL)) {
            return "Wall";
        } else if (world[x][y].equals(Tileset.FLOOR)) {
            return "Floor";
        } else if (world[x][y].equals(Tileset.FLOWER)) {
            return "Flower";
        } else if (world[x][y].equals(Tileset.GRASS)) {
            return "Grass";
        } else if (world[x][y].equals(Tileset.LOCKED_DOOR)) {
            return "Locked Door";
        } else if (world[x][y].equals(Tileset.SAND)) {
            return "Sand";
        } else if (world[x][y].equals(Tileset.WATER)) {
            return "Water";
        } else if (world[x][y].equals(Tileset.UNLOCKED_DOOR)) {
            return "Unlocked Door";
        } else if (world[x][y].equals(Tileset.TREE)) {
            return "Tree";
        } else if (world[x][y].equals(Tileset.MOUNTAIN)) {
            return "Mountain";
        } else {
            return "Nothing";
        }
    }

    public String languageSpanish(int x, int y) {
        if (world[x][y].equals(Tileset.AVATAR)) {
            return "Avatar";
        } else if (world[x][y].equals(Tileset.WALL)) {
            return "Fabricar Un Muro";
        } else if (world[x][y].equals(Tileset.FLOOR)) {
            return "Las áreas";
        } else if (world[x][y].equals(Tileset.FLOWER)) {
            return "Flores";
        } else if (world[x][y].equals(Tileset.GRASS)) {
            return "Hierba";
        } else if (world[x][y].equals(Tileset.LOCKED_DOOR)) {
            return "Puerta Cerrada";
        } else if (world[x][y].equals(Tileset.SAND)) {
            return "Arena";
        } else if (world[x][y].equals(Tileset.WATER)) {
            return "Agua";
        } else if (world[x][y].equals(Tileset.UNLOCKED_DOOR)) {
            return "Puerta Cerrada";
        } else if (world[x][y].equals(Tileset.TREE)) {
            return "Madera";
        } else if (world[x][y].equals(Tileset.MOUNTAIN)) {
            return "montículo";
        } else {
            return "No tengo nada que declarar";
        }

    }

    public String languageKorean(int x, int y) {
        if (world[x][y].equals(Tileset.AVATAR)) {
            return "아바타";
        } else if (world[x][y].equals(Tileset.WALL)) {
            return "벽";
        } else if (world[x][y].equals(Tileset.FLOOR)) {
            return "바닥";
        } else if (world[x][y].equals(Tileset.FLOWER)) {
            return "꽃";
        } else if (world[x][y].equals(Tileset.GRASS)) {
            return "풀";
        } else if (world[x][y].equals(Tileset.LOCKED_DOOR)) {
            return "잠긴 문";
        } else if (world[x][y].equals(Tileset.SAND)) {
            return "모래";
        } else if (world[x][y].equals(Tileset.WATER)) {
            return "W물";
        } else if (world[x][y].equals(Tileset.UNLOCKED_DOOR)) {
            return "열린 문";
        } else if (world[x][y].equals(Tileset.TREE)) {
            return "나무";
        } else if (world[x][y].equals(Tileset.MOUNTAIN)) {
            return "산";
        } else {
            return "없음";
        }
    }
}
